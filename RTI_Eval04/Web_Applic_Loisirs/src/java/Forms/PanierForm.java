/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Beans.Article;
import Beans.Panier;
import Beans.User;
import database_acces.ConnexionDB;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author isen0
 */
public class PanierForm
{
    private String resultat;
    private final String configPath;
    private Article myArticle;
    private HttpServletRequest request;

    public PanierForm(HttpServletRequest req) {
        this.configPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"Web_Applic_Loisirs"+System.getProperty("file.separator")+ "configWeb.properties";
        request = req;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    public void ajoutArticle()
    {
        System.out.println("- entrée dans recupArticle -");
        myArticle = new Article();
        
            try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    String query;
                    ResultSet rs;
                    if(request.getParameter("idProduit").contains("A"))//SI article
                    {
                        query = "SELECT * FROM articles WHERE idArticle = '"+request.getParameter("idProduit")+"'";
                        System.out.println("Query AjoutPanierForm 1 : "+query);
                        rs = db.select(query);
                    }
                    else
                    {
                        query = "SELECT * FROM visites WHERE idVisite = '"+request.getParameter("idProduit")+"'";
                        System.out.println("Query AjoutPanierForm 1 : "+query);
                        rs = db.select(query);
                    }

                    if(rs != null)
                    {
                        //System.out.println("TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEESTTT323");
                        if(rs.next())
                        {
                            
                            ResultSetMetaData metaData = rs.getMetaData();

                            Vector<String> data = new Vector<>();
                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                            {
                                //System.out.println("rs : " + rs.getString(cpt));
                                data.add(rs.getString(cpt));
                            }
                            myArticle.setIdArticle(data.elementAt(0));
                            myArticle.setNomArticle(data.elementAt(1));
                            myArticle.setPrixArticle(Float.parseFloat(data.elementAt(2)));
                            myArticle.setStock(Integer.parseInt(data.elementAt(3)));
                            myArticle.setQuantite(Integer.parseInt(request.getParameter("quantiteProduit")));

                            if(myArticle.getQuantite()>0)
                            {
                                if(myArticle.getQuantite() < myArticle.getStock())
                                {
                                            if(myArticle.getIdArticle().contains("A"))//SI article
                                            {
                                                String query2 = "UPDATE articles "
                                                        + "SET stock = stock - '"+myArticle.getQuantite()+"'"
                                                        + "WHERE idArticle = '"+myArticle.getIdArticle()+"'";
                                                System.out.println("Query PanierForm 2 : "+query2);
                                                db.update(query2);
                                            }
                                            else//SI visite
                                            {
                                                String query3 = "UPDATE visites "
                                                        + "SET nbrPlaces = nbrPlaces - '"+myArticle.getQuantite()+"'"
                                                        + "WHERE idVisite = '"+myArticle.getIdArticle()+"'";
                                                System.out.println("Query PanierForm 3 : "+query3);
                                                db.update(query3);
                                            }
                                    HttpSession session = request.getSession();
                                    User myUser = (User) session.getAttribute("currentUser");
                                            
                                    String query4 = "INSERT INTO promesses (idArticle, quantite, datePromesse, userName)"
                                                    + "VALUES ('"+myArticle.getIdArticle()+"', '"+myArticle.getQuantite()+"', NOW(), '"+myUser.getNom()+"')";
                                    System.out.println("Query PanierForm 4 : "+query4);
                                    db.insert(query4);
                                    
                                    session = request.getSession();
            
                                    Panier myPanier;
                                    if (session.getAttribute("currentPanier") == null)
                                    {
                                        myPanier = new Panier();     
                                        System.out.println("Nouveau panier créé");           
                                    }
                                    else
                                    {
                                        myPanier = (Panier)session.getAttribute("currentPanier");
                                    }            

                                    myPanier.addArticle(myArticle);               
                                    session.setAttribute("currentPanier", myPanier);
                                    
                                    setResultat(myArticle.getNomArticle()+" ajouté au panier !");
                                }
                                else
                                {
                                    setResultat("Stock Insuffisant ! Adressez vous à votre magasin pour réserver.");
                                }
                            }
                            else
                            {
                                setResultat("La quantité souhaitée doit être supérieure à 0 ");
                            }
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs.next()");
                            setResultat("Identifiant article "+request.getParameter("idProduit")+" incorrect !");
                        }
                    }
                    else
                    {
                        setResultat("Identifiant article "+request.getParameter("idProduit")+" incorrect !");
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
    
    public void supprimerArticle()
    {
        System.out.println("- entrée dans supprimerArticle -");
        
        int idPromesse;
        String idArticle;
        int quantitePromesse;
        Date datePromesse;

        try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    HttpSession session = request.getSession();
                    User myUser = (User) session.getAttribute("currentUser");
                    
                    String query = "SELECT * FROM promesses WHERE idArticle = '"+request.getParameter("idProduit")+"' AND userName = '"+myUser.getNom()+"' ORDER BY datePromesse";
                    System.out.println("Query supprimerArticle 1 : "+query);
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
                            System.out.println("Id promesse (supprimerArticle): " + idPromesse);
                            if(idPromesse>0 && quantitePromesse>0 && datePromesse!=null)
                            {
                                
                                            if(idArticle.contains("A"))//SI article
                                            {
                                                String query2 = "UPDATE articles "
                                                        + "SET stock = stock + '"+quantitePromesse+"'"
                                                        + "WHERE idArticle = '"+idArticle+"'";
                                                System.out.println("Query supprimerArticle 2 : "+query2);
                                                db.update(query2);
                                            }
                                            else//SI visite
                                            {
                                                String query3 = "UPDATE visites "
                                                        + "SET nbrPlaces = nbrPlaces + '"+quantitePromesse+"'"
                                                        + "WHERE idVisite = '"+idArticle+"'";
                                                System.out.println("Query supprimerArticle 3 : "+query3);
                                                db.update(query3);
                                            }
                                            
                                            String query4 = "DELETE FROM promesses WHERE idPromesse = '"+idPromesse+"'";
                                                System.out.println("Query supprimerArticle 3 : "+query4);
                                                db.delete(query4);
                                                
                                            session = request.getSession();
                                            Panier myPanier = (Panier)session.getAttribute("currentPanier");
                                            myPanier.findRemoveArticle(idArticle);

                                            session.setAttribute("currentPanier", myPanier);
                                            System.out.println("article "+idArticle+" supprimé du panier");
                                        
                                            setResultat("Article retiré du panier !");
                            }
                            else
                            {
                                System.err.println("Erreur dans le parsing des données de la DB (supprimerArticle)");
                            }
                            
                        }

                    }
                    else
                    {
                        System.out.println("Aucun tuple dans la table promesses (supprimerArticle)");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee (supprimerArticle)");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB (supprimerArticle) : " + e.getMessage());
            }
    }
    
    public void payerPanier()
    {
        System.out.println("- entrée payerPanier supprimerArticle -");
        
        int idPromesse;
        String idArticle;
        int quantitePromesse;
        Date datePromesse;

        try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    HttpSession session = request.getSession();
                    User myUser = (User) session.getAttribute("currentUser");
                    
                    String query = "SELECT * FROM promesses WHERE userName = '"+myUser.getNom()+"' ORDER BY datePromesse";
                    System.out.println("Query supprimerArticle 1 : "+query);
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
                            System.out.println("Id promesse (payerPanier): " + idPromesse);
                            if(idPromesse>0 && quantitePromesse>0 && datePromesse!=null)
                            {
                                            session = request.getSession();
                                            float montant = (float) session.getAttribute("montantTotal");
                                
                                            String query3 = "INSERT INTO achats (nomUser, idArticle, quantite, montant, dateVente)"
                                                    + "VALUES ('"+myUser.getNom()+"', '"+idArticle+"', '"+quantitePromesse+"', '"+montant+"', CURDATE())";
                                            System.out.println("Query payerPanier 3 : "+query3);
                                            db.insert(query3);
                                            
                                            String query4 = "DELETE FROM promesses WHERE idPromesse = '"+idPromesse+"'";
                                            System.out.println("Query payerPanier 4 : "+query4);
                                            db.delete(query4);
                                                
                                            session = request.getSession();
                                            Panier myPanier = (Panier)session.getAttribute("currentPanier");
                                            myPanier.findRemoveArticle(idArticle);

                                            session.setAttribute("currentPanier", myPanier);
                                            System.out.println("article "+idArticle+" supprimé du panier");
                                        
                                            setResultat("Paiement OK");
                            }
                            else
                            {
                                System.err.println("Erreur dans le parsing des données de la DB (payerPanier)");
                                setResultat("Erreur lors du paiement");
                            }
                            
                        }
                         setResultat("Paiement OK");
                    }
                    else
                    {
                        System.out.println("Aucun tuple dans la table promesses (payerPanier)");
                        setResultat("Erreur lors du paiement");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee (payerPanier)");
                    setResultat("Erreur lors du paiement");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB (payerPanier) : " + e.getMessage());
                setResultat("Erreur lors du paiement");
            }
    }
    
    public void removeAllUserArticles()
    {
        System.out.println("- entrée dans removeAllUserArticles -");
        
        int idPromesse;
        String idArticle;
        int quantitePromesse;
        Date datePromesse;

        try
            {
                ConnexionDB db = new ConnexionDB(configPath,"bd_shopping");
                db.openConnection();

                if(db!=null)
                {
                    HttpSession session = request.getSession();
                    User myUser = (User) session.getAttribute("currentUser");
                    
                    String query = "SELECT * FROM promesses WHERE userName = '"+myUser.getNom()+"'";
                    System.out.println("Query removeAllUserArticles 1 : "+query);
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
                            System.out.println("Id promesse (removeAllUserArticles): " + idPromesse);
                            if(idPromesse>0 && quantitePromesse>0 && datePromesse!=null)
                            {
                                
                                            if(idArticle.contains("A"))//SI article
                                            {
                                                String query2 = "UPDATE articles "
                                                        + "SET stock = stock + '"+quantitePromesse+"'"
                                                        + "WHERE idArticle = '"+idArticle+"'";
                                                System.out.println("Query removeAllUserArticles 2 : "+query2);
                                                db.update(query2);
                                            }
                                            else//SI visite
                                            {
                                                String query3 = "UPDATE visites "
                                                        + "SET nbrPlaces = nbrPlaces + '"+quantitePromesse+"'"
                                                        + "WHERE idVisite = '"+idArticle+"'";
                                                System.out.println("Query removeAllUserArticles 3 : "+query3);
                                                db.update(query3);
                                            }
                                            
                                            String query4 = "DELETE FROM promesses WHERE idPromesse = '"+idPromesse+"'";
                                                System.out.println("Query removeAllUserArticles 3 : "+query4);
                                                db.delete(query4);
                                                
                                            session = request.getSession();
                                            Panier myPanier = (Panier)session.getAttribute("currentPanier");
                                            myPanier.findRemoveArticle(idArticle);

                                            session.setAttribute("currentPanier", myPanier);
                                            System.out.println("article "+idArticle+" supprimé du panier");
                                        
                                            setResultat("Tous les articles retirés du panier !");
                            }
                            else
                            {
                                System.err.println("Erreur dans le parsing des données de la DB (removeAllUserArticles)");
                            }
                            
                        }

                    }
                    else
                    {
                        System.out.println("Aucun tuple dans la table promesses (supprimerArticle)");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee (supprimerArticle)");
                }

                    db.closeConnection();
                    db = null;

            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB (supprimerArticle) : " + e.getMessage());
            }
    }
}
