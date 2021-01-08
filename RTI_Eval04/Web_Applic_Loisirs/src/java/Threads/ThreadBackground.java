/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Beans.Panier;
import database_acces.ConnexionDB;
//import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.servlet.ServletContext;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author isen0
 */
public class ThreadBackground extends Thread
{
    private final String configPath;
    private final long ONE_MINUTE_IN_MILLIS = 60000;
    
    private int idPromesse;
    private String idArticle;
    private int quantitePromesse;
    private Date datePromesse;
    
    private HttpServletRequest request;
    
    public ThreadBackground(HttpServletRequest req)
    {
        this.configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Loisirs"+System.getProperty("file.separator")+ "configWeb.properties";
        request = req;
    }
    
    public void run()
    {
        while(!isInterrupted())
        {
            System.out.println("--- Passage dans ThreadBackground ---");
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    String query = "SELECT * FROM promesses ";
                    System.out.println("Query ThreadBackground 1 : "+query);
                    ResultSet rs = db.select(query);

                    if(rs != null)
                    {
                        while(rs.next())
                        {
                            ResultSetMetaData metaData = rs.getMetaData();

                            Vector<String> data = new Vector<>();
                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                            {
                                //System.out.println("rs : " + rs.getString(cpt));
                                data.add(rs.getString(cpt));
                            }
                            try
                            {
                                idPromesse = Integer.parseInt(data.elementAt(0));
                            }
                            catch(NumberFormatException e)
                            {
                                idPromesse = -1;
                            }
                            
                            idArticle = data.elementAt(1);
                            
                            try
                            {
                                quantitePromesse = Integer.parseInt(data.elementAt(2));
                            }
                            catch(NumberFormatException e)
                            {
                                quantitePromesse = -1;
                            }
                            try
                            {
                                datePromesse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.elementAt(3));
                            }
                            catch(ParseException e)
                            {
                                datePromesse = null;
                            }
                            System.out.println("Id promesse : " + idPromesse);
                            System.out.println("Date promesse : "+datePromesse+" (ThreadBackground)");
                            if(idPromesse>0 && quantitePromesse>0 && datePromesse!=null)
                            {
                                Date currentDate = new Date();
                                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String strDate = formatDate.format(currentDate);
                                try
                                {
                                   currentDate = formatDate.parse(strDate); 
                                }
                                catch(ParseException e)
                                {
                                    currentDate = null;
                                }
                                System.out.println("currentDate : "+currentDate+" (ThreadBackground)");
                                if(currentDate!=null)
                                {
                                    long proTimeInMs = datePromesse.getTime();
                                    Date proAfterAddingMins = new Date(proTimeInMs + (30*ONE_MINUTE_IN_MILLIS));
                                    String strDate2 = formatDate.format(proAfterAddingMins);
                                    try
                                    {
                                       proAfterAddingMins = formatDate.parse(strDate2); 
                                    }
                                    catch(ParseException e)
                                    {
                                        proAfterAddingMins = null;
                                    }
                                    System.out.println("promesse AfterAddingMins : "+proAfterAddingMins+" (ThreadBackground)");
                                    
                                    System.out.println("TEST currentDate.after(proAfterAddingMins) : "+currentDate.after(proAfterAddingMins)+" (ThreadBackground)");
                                    if(proAfterAddingMins!=null)
                                    {
                                        if(currentDate.after(proAfterAddingMins))
                                        {
                                            if(idArticle.contains("A"))//SI article
                                            {
                                                String query2 = "UPDATE articles "
                                                        + "SET stock = stock + '"+quantitePromesse+"'"
                                                        + "WHERE idArticle = '"+idArticle+"'";
                                                System.out.println("Query ThreadBackground 2 : "+query2);
                                                db.update(query2);
                                            }
                                            else//SI visite
                                            {
                                                String query3 = "UPDATE visites "
                                                        + "SET nbrPlaces = nbrPlaces + '"+quantitePromesse+"'"
                                                        + "WHERE idVisite = '"+idArticle+"'";
                                                System.out.println("Query ThreadBackground 3 : "+query3);
                                                db.update(query3);
                                            }
                                            
                                            String query4 = "DELETE FROM promesses WHERE idPromesse = '"+idPromesse+"'";
                                                System.out.println("Query ThreadBackground 3 : "+query4);
                                                db.delete(query4);
                                                
                                            HttpSession session = request.getSession();
                                            Panier myPanier = (Panier)session.getAttribute("currentPanier");
                                            myPanier.findRemoveArticle(idArticle);

                                            session.setAttribute("currentPanier", myPanier);
                                            System.out.println("article "+idArticle+" supprimé du panier");
                                        }
                                        else
                                        {
                                            
                                        }
                                    }
                                    else
                                    {
                                        System.err.println("Erreur dans le parsing afterAddingMins (ThreadBackground)");
                                    }
                                }
                                else
                                {
                                    System.err.println("Erreur dans le parsing currentDate (ThreadBackground)");
                                }
                            }
                            else
                            {
                                System.err.println("Erreur dans le parsing des données de la DB (ThreadBackground)");
                            }
                            
                        }

                    }
                    else
                    {
                        System.out.println("Aucun tuple dans la table promesses (ThreadBackground)");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee (ThreadBackground)");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB (ThreadBackground) : " + e.getMessage());
            }
            try
            {
                sleep(ONE_MINUTE_IN_MILLIS);
            }
            catch(InterruptedException e)
            {
                System.out.println("Erreur (ThreadBackground) : "+e);
            }
        }
    }
}
