/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import database_acces.ConnexionDB;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author isen0
 */
public class PasswordForm
{
    private String resultat;
    private final String configPath;

    public PasswordForm() {
        this.configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Loisirs"+System.getProperty("file.separator")+ "configWeb.properties";
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    public void verifierMdp(HttpServletRequest request)
    {
        String userLogin = request.getParameter("name");
        String userPassFirst = request.getParameter("passFirst");
        String userPasVerif = request.getParameter("passVerif");
        
        if(userPassFirst.equals(userPasVerif))
        {
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    String query2="";
                
                    String query = "SELECT * FROM users WHERE login = '"+userLogin+"'";
                    System.out.println("Query password 1 : "+query);
                    ResultSet rs = db.select(query);
                    
                    if(rs != null)
                    {
                        if(rs.next())
                        {
                            query2 = "UPDATE users SET password = '"+userPassFirst+"' WHERE login = '"+userLogin+"'";
                            System.out.println("Query password 2 : "+query2);
                            db.update(query2);
                            setResultat("pwdOK");
                        }
                        else
                        {
                            setResultat("Cet identifiant n'existe pas ! Veuillez vous inscrire.");
                        }
                    }
                    else
                    {
                        setResultat("Cet identifiant n'existe pas ! Veuillez vous inscrire.");
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
        else
        {
            setResultat("Les mots de passe ne correspondent pas !");
        }
        
    }
}
