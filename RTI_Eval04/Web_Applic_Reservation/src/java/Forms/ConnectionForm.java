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
public class ConnectionForm {
    
    private String resultat;
    private final String configPath;

    public ConnectionForm() {
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
    
    public void verifierIdentifiants(HttpServletRequest request)
    {
        
        String userLogin = request.getParameter("name");
        String userPassword = request.getParameter("pass");
        String userChoice = request.getParameter("choice");
        
        /*if(userPassword.equals(userLogin+"123"))
        {
            setResultat("connectionOK");
        }
        else
        {
            setResultat("Identifiants incorrects !");
        }*/
        
        if(userChoice.equals("connection"))
        {
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_mouvements");
                db.openConnection();

                if(db!=null)
                {
                    String query = "SELECT password FROM users WHERE login = '"+userLogin+"'";
                    ResultSet rs = db.select(query);

                    if(rs != null)
                    {
                        if(rs.next())
                        {
                            ResultSetMetaData metaData = rs.getMetaData();

                            Vector<String> data = new Vector<>();
                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                            {
                                //System.out.println("rs : " + rs.getString(cpt));
                                data.add(rs.getString(cpt));
                            }
                            String mdp = data.elementAt(0);

                            if(userPassword.equals(mdp))
                            {
                                setResultat("connectionOK");
                            }
                            else
                            {
                                setResultat("Identifiants incorrects !");
                            }
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs.next()");
                            setResultat("Identifiants incorrects !");
                        }
                    }
                    else
                    {
                        setResultat("Identifiants incorrects !");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    setResultat("Non connecté à la BD !");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
                setResultat("Erreur connection à la BD !");
            }
            
        }
        else if(userChoice.equals("inscription"))
        {
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_mouvements");
                db.openConnection();

                if(db!=null)
                {
                    try
                    {
                        String query = "INSERT INTO users (login, password) VALUES ('"+userLogin+"', '"+userPassword+"')";
                        db.insert(query);
                        setResultat("connectionOK");
                    }
                    catch (SQLException e)
                    {
                        setResultat("L'utilisateur "+userLogin+" existe déjà");
                    }
                    
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    setResultat("Non connecté à la BD !");
                }


                    db.closeConnection();
                    db = null;
                
            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
            }
        }
        
    }
    
}
