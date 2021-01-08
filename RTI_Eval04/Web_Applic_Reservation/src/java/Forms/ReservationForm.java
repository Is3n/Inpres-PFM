/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import database_acces.ConnexionDB;
import database_acces.FichierConfig;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author isen0
 */
public class ReservationForm {
    
    private String resultat;
    private final String configPath;

    public ReservationForm() {
        this.configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Reservation"+System.getProperty("file.separator")+ "configWeb.properties";
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    public void makeReservation(HttpServletRequest request)
    {
        
        String contId = request.getParameter("idContainer");
        String contSociete = request.getParameter("societe");
        String contNature = request.getParameter("natureContenu");
        int contCapacite = -1;
        try
        {
            contCapacite = Integer.parseInt(request.getParameter("capacite"));
        }
        catch(NumberFormatException e)
        {
            setResultat("Erreur : Capacité doit être un entier positif !# ");
            System.err.println("Erreur parseInt Capacite container");
        }        
        String contDangers = request.getParameter("dangers");
        String resDestination = request.getParameter("destination");  
        
        int nbrRes = 0;
        
        if(contCapacite > 0)
        {
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_mouvements");
                db.openConnection();

                if(db!=null)
                {
                    ResultSet rs=null;
                    
                    String query = "SELECT COUNT(*) FROM reservations ";
                    rs = db.select(query);
                    if(rs != null)
                    {
                        if(rs.next())
                        {
                            ResultSetMetaData metaData = rs.getMetaData();

                            Vector<String> data = new Vector<>();
                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                            {
                                System.out.println("rs : " + rs.getString(cpt));
                                data.add(rs.getString(cpt));
                            }
                            nbrRes = Integer.parseInt(data.elementAt(0));
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs.next()");
                        }
                    }
                    else
                    {
                        System.err.println("Erreur dans le rs != null");
                    }

                    String query2 = "INSERT INTO containers (id, societe_id, nature_contenu, capacite, dangers) "
                                    + "VALUES ('"+contId+"', '"+contSociete+"', '"+contNature+"', '"+contCapacite+"', '"+contDangers+"')";
                    //db.insert(query2);
                    
                    String query3 = "INSERT INTO reservations (num_reserv, container_id, destination, etat) "
                                    + "VALUES ('ResWeb"+nbrRes+"', '"+contId+"', '"+resDestination+"', 0)";
                    //db.insert(query3);
                    
                    String query4 = "SELECT * FROM parc WHERE container_id IS NULL LIMIT 1";
                    //String query4 = "SELECT * FROM parc WHERE container_id = 54 LIMIT 1";
                    
                    try
                    {
                        rs = db.select(query4);
                    }
                    catch (SQLException e)
                    {
                        setResultat("Erreur : pas d'emplacement disponible !# # ");
                    }

                    String query5 = "";
                    String emplacement = "0,0";
                    if(rs != null)
                    {
                        if(rs.next())
                        {
                            ResultSetMetaData metaData = rs.getMetaData();

                            Vector<String> data = new Vector<>();
                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                            {
                                System.out.println("rs : " + rs.getString(cpt));
                                data.add(rs.getString(cpt));
                            }
                            int idParc = Integer.parseInt(data.elementAt(0));
                             emplacement = data.elementAt(1);

                             query5 = "UPDATE parc "
                                            + "SET container_id = '"+contId+"', etat_emplacement = 1, date_reserv = CURDATE(), destination = '"+resDestination+"' "
                                            + "WHERE id = "+idParc;
                            
                            db.insert(query2);
                            db.insert(query3);
                            
                            db.update(query5);
                            setResultat("ReservationOK#"+emplacement+"#ResWeb"+nbrRes);
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs.next()");
                            setResultat("Erreur : pas d'emplacement disponible !# # ");
                        }
                    }
                    else
                    {
                        System.err.println("Erreur dans le rs != null");
                        setResultat("Erreur : pas d'emplacement disponible !# # ");
                    }
                    
                    System.out.println("Query 2 : "+query2);
                    System.out.println("Query 3 : "+query3);
                    System.out.println("Query 4 : "+query4);
                    System.out.println("Query 5 : "+query5);

                    

                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    setResultat("Non connecté à la BD !# # ");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
                setResultat("Erreur connection à la BD !# # ");
            }
        }
        else
        {
            setResultat("Erreur : Capacité doit être un entier positif !# # ");
            System.err.println("Erreur Capacite container <= 0");
        }
        
    }
    
}
