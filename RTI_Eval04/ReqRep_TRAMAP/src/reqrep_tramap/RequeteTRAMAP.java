/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_tramap;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Hashtable;
import java.util.StringTokenizer;
import reqrep_poolthreads.ConsoleServeur;
import reqrep_poolthreads.Requete;
import database_acces.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import reqrep_chamap.*;


/**
 *
 * @author isen0
 */
public class RequeteTRAMAP implements Requete, Serializable
{
    private ConsoleServeur guiApplication;
    
    public static int LOGIN = 1;
    public static int INSCRIPTION = 2;
    public static int OPERATIONS = 3;
    public static int INPUT_LORRY_WITHOUT_RESERVATION = 4;
    public static int INPUT_LORRY = 5;

    private int type;
    private String chargeUtile;
    private Socket socketClient;
    
    private ObjectInputStream oisCompta;
    private ObjectOutputStream oosCompta;
    private Socket cliSockCompta=null;
    
    public RequeteTRAMAP(int t, String chu)
    {
        type = t; setChargeUtile(chu);
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequeteTRAMAP(int t, String chu, Socket s)
    {
        type = t; setChargeUtile(chu); socketClient =s;
    }
    
    public Runnable createRunnable (final Socket s, final ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE TRAMAP 2: "+getType());
//        System.out.println("TEST chrge TRAMAP 2: "+getChargeUtile());
        if (type==LOGIN)
            return new Runnable()
            {
                public void run()
                {
                    traiteLOGIN(s, cs);
                }
            };
        else if (type==INSCRIPTION)
            return new Runnable()
            {
                public void run()
                {
                    traiteINSCRIPTION(s, cs);
                }
            };
        else if (type==OPERATIONS)
            return new Runnable()
            {
                public void run()
                {
                    traiteOPERATIONS(s, cs);
                }
            };
        else if (type==INPUT_LORRY_WITHOUT_RESERVATION)
            return new Runnable()
            {
                public void run()
                {                   
                    traiteINPUT_LORRY_WR(s, cs);
                }
            };
        else if (type==INPUT_LORRY)
            return new Runnable()
            {
                public void run()
                {                   
                    traiteINPUT_LORRY(s, cs);
                }
            };
        else return null;
    }
    
    private void traiteLOGIN(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLOGIN : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLOGIN : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        String pass = st.nextToken();
        
        ReponseTRAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_mouvements");
            db.openConnection();
            
            if(db!=null)
            {
                String query = "SELECT password FROM users WHERE login = '"+user+"'";
                ResultSet rs = db.select(query);
                
                //ReponseTRAMAP rep = null;
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
                        String mdp = data.elementAt(0);

                        if(pass.equals(mdp))
                        {
                            System.out.println("traiteLOGIN OK. Debut de LOGIN_TRAF");
                            //rep = new ReponseTRAMAP(ReponseTRAMAP.OK, user);
                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Log in: "+user+"#thread serveur");
                            
                        //DEBUT CODE LOGIN_TRAF voir 1h45 dans la video RTI - Cours du 23/10/2020 + page 231(240/437) syllabus                                      

                            // Connexion au serveur
                            oisCompta=null; oosCompta=null; //cliSock = null;
                            String adresse=null;
                            int port=-1;
                            
                            Properties prop = new Properties();
                            String config = System.getProperty("user.dir") + System.getProperty("file.separator")+ "configServ.properties";
                            System.out.println("Path fichier config TRAMAP : "+config);
                            try
                            {
                                prop.load(new FileInputStream(config));
                                adresse = prop.getProperty("HOST");
                                port = Integer.parseInt(prop.getProperty("PORT_MOUVEMENTS"));
                            }
                            catch(FileNotFoundException e) { System.out.println("Fichier properties (TRAMAP) non trouvé ! message : " + e.getMessage()); }
                            catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); }                           

                            try
                            {
                                cliSockCompta = new Socket(adresse, port);
                                System.out.println("infos socket compta: "+cliSockCompta.getInetAddress().toString());
                            }
                            catch (UnknownHostException e)
                            { 
                                System.err.println("Erreur de connexion au serveur compta ! Host compta non trouvé [" + e + "]"); 
                            }
                            catch (IOException e)
                            { 
                                System.err.println("Erreur de connexion au serveur compta ! Pas de connexion ? [" + e + "]");
                            }
                            
                            // Construction du Digest
                            System.out.println("Construction du Digest client dans TRAMAP");
                            try
                            {
                                MessageDigest md = MessageDigest.getInstance(prop.getProperty("ALGODIGEST"), prop.getProperty("PROVIDERCODE"));
                                
                                md.update(user.getBytes());
                                md.update(pass.getBytes());
                                
                                long temps = (new Date()).getTime();
                                double alea = Math.random();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                DataOutputStream bdos = new DataOutputStream(baos);
                                try
                                {
                                    bdos.writeLong(temps);
                                    bdos.writeDouble(alea);
                                }
                                catch (IOException e)
                                { System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); }
                                
                                md.update(baos.toByteArray());
                                byte[] msgDigest = md.digest();
                                                                                       
                                // Construction de la requête
                                String chargeUtileCompta = user+"#"+temps+"#"+alea+"#"+msgDigest.length;
                                //System.out.println("TEST CHARGE UTILE COMPTA : "+chargeUtileCompta);
                                RequeteCHAMAP reqCompta = null;
                                reqCompta = new RequeteCHAMAP(RequeteCHAMAP.LOGIN_TRAF, chargeUtileCompta, msgDigest);

                                // Envoie de la requête
                                try
                                {
                                    System.out.println("Envoi de la requete LOGIN_TRAF : "+reqCompta.getChargeUtile());
                                    oosCompta = new ObjectOutputStream(cliSockCompta.getOutputStream());
                                    oosCompta.writeObject(reqCompta);
                                    oosCompta.flush();
                                    FichierLog.UpdateFich("Envoi d'une requete de type LOGIN_TRAF : " +reqCompta.getChargeUtile());
                                }
                                catch (IOException e)
                                { System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); }
                            }
                            catch(NoSuchAlgorithmException | NoSuchProviderException e)
                            {
                                System.err.println("Erreur dans le Digest TRAMAP");
                            }
                                
                                
                                // Lecture de la réponse
                                ReponseCHAMAP repCompta = null;
                                try
                                {
                                    oisCompta = new ObjectInputStream(cliSockCompta.getInputStream());
                                    repCompta = (ReponseCHAMAP)oisCompta.readObject();
                                    System.out.println(" *** Reponse compta reçue : " + repCompta.getChargeUtile());
                                    FichierLog.UpdateFich(" Reponse compta reçue : " + repCompta.getChargeUtile());

                                    if(repCompta.getCode() == ReponseCHAMAP.OK)
                                    {
                                        rep = new ReponseTRAMAP(ReponseTRAMAP.OK, user);
                                    }
                                    else
                                    {
                                        rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL_COMPTA, user);
                                    }
                                }
                                catch (ClassNotFoundException e)
                                { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
                                catch (IOException e)
                                { System.out.println("--- erreur IO = " + e.getMessage()); }
                            
                            
                        //FIN CODE LOGIN_TRAF
                        }
                        else
                        {
                            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_PASSWORD, user);
                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Mdp incorrect : "+user+"#thread serveur");
                        }
                    }
                    else
                    {
                        System.err.println("Erreur dans le rs.next()");
                        rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, user);
                        guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                    }
                }
                else
                {
                    rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, user);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, user);
                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep); oos.flush();
                db.closeConnection();
                db = null;
            }
            catch (SQLException e)
            {
                System.err.println("Erreur lors de la déconnexion de la DB : " + e.getMessage());
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
        }
        
        // Construction d'une réponse

    }
    
    private void traiteINSCRIPTION(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteINSCRIPTION : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteINSCRIPTION : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        String pass = st.nextToken();
        
        ReponseTRAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_mouvements");
            db.openConnection();
            
            if(db!=null)
            {
                String query = "INSERT INTO users (login, password) VALUES ('"+user+"', '"+pass+"')";
                db.insert(query);
                
                //ReponseTRAMAP rep = null;
                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Creation utilisateur : "+user+"#thread serveur");
                rep = new ReponseTRAMAP(ReponseTRAMAP.OK, user);
                
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, user);
                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep); oos.flush();
                db.closeConnection();
                db = null;
            }
            catch (SQLException e)
            {
                System.err.println("Erreur lors de la déconnexion de la DB : " + e.getMessage());
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
        }
        
        // Construction d'une réponse

    }
    
    private void traiteOPERATIONS(Socket sock, ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE TRAMAP 3: "+getType());
//        System.out.println("TEST chrge TRAMAP 3: "+getChargeUtile());
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteOPERATIONS : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteOPERATIONS : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String DateDebut = st.nextToken();
        String DateFin = st.nextToken();
        String Societe = st.nextToken();
        String Destination = st.nextToken();
        
        ReponseTRAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_mouvements");
            db.openConnection();
            
            if(db!=null)
            {
                String query = null;
                
                if(Societe.equals("_") == false && Destination.equals("_") == false)
                {
                    query = "SELECT mouvements.container_id, containers.societe_id, mouvements.transport_entrant, mouvements.date_arriv, "
                                    + "mouvements.transport_sortant, mouvements.poids, mouvements.date_depart, mouvements.destination "
                                    + "FROM mouvements, containers WHERE mouvements.container_id = containers.id "
                                    + "AND containers.societe_id = '"+Societe+"' "
                                    + "AND mouvements.destination = '"+Destination+"' "
                                    + "AND ((mouvements.date_arriv >= '"+DateDebut+"' AND mouvements.date_arriv <= '"+DateFin+"') "
                                    + "OR (mouvements.date_depart >= "+DateDebut+" AND mouvements.date_depart <= "+DateFin+"))";
                            //+ "LEFT JOIN containers ON mouvements.container_id = containers.id ";
                    System.out.println("Query : "+query);
                }
                else if(Societe.equals("_") == false && Destination.equals("_") == true)
                {
                    query = "SELECT mouvements.container_id, containers.societe_id, mouvements.transport_entrant, mouvements.date_arriv, "
                                    + "mouvements.transport_sortant, mouvements.poids, mouvements.date_depart, mouvements.destination "
                                    + "FROM mouvements, containers WHERE mouvements.container_id = containers.id "
                                    + "AND containers.societe_id = '"+Societe+"' "
                                    + "AND ((mouvements.date_arriv >= '"+DateDebut+"' AND mouvements.date_arriv <= '"+DateFin+"') "
                                    + "OR (mouvements.date_depart >= "+DateDebut+" AND mouvements.date_depart <= "+DateFin+"))";
                            //+ "LEFT JOIN containers ON mouvements.container_id = containers.id ";
                    System.out.println("Query : "+query);
                }
                else if(Societe.equals("_") == true && Destination.equals("_") == false)
                {
                    query = "SELECT mouvements.container_id, containers.societe_id, mouvements.transport_entrant, mouvements.date_arriv, "
                                    + "mouvements.transport_sortant, mouvements.poids, mouvements.date_depart, mouvements.destination "
                                    + "FROM mouvements, containers WHERE mouvements.container_id = containers.id "
                                    + "AND mouvements.destination = '"+Destination+"' "
                                    + "AND ((mouvements.date_arriv >= '"+DateDebut+"' AND mouvements.date_arriv <= '"+DateFin+"') "
                                    + "OR (mouvements.date_depart >= "+DateDebut+" AND mouvements.date_depart <= "+DateFin+"))";
                            //+ "LEFT JOIN containers ON mouvements.container_id = containers.id ";
                    System.out.println("Query : "+query);
                }
                else
                {
                    query = "SELECT mouvements.container_id, containers.societe_id, mouvements.transport_entrant, mouvements.date_arriv, "
                                    + "mouvements.transport_sortant, mouvements.poids, mouvements.date_depart, mouvements.destination "
                                    + "FROM mouvements, containers WHERE mouvements.container_id = containers.id "
                                    + "AND ((mouvements.date_arriv >= '"+DateDebut+"' AND mouvements.date_arriv <= '"+DateFin+"') "
                                    + "OR (mouvements.date_depart >= "+DateDebut+" AND mouvements.date_depart <= "+DateFin+"))";
                            //+ "LEFT JOIN containers ON mouvements.container_id = containers.id ";
                    System.out.println("Query : "+query);
                }

                ResultSet rs = db.select(query);
                
                ResultSetMetaData metaData = rs.getMetaData();
                DefaultTableModel model = new DefaultTableModel();
                for(int cpt = 1; cpt <= metaData.getColumnCount() ;cpt++)
                {
                    model.addColumn(metaData.getColumnName(cpt));
                }

                while(rs.next())
                {
                    Vector<String> data = new Vector<>();
                    for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                    {
                        System.out.println("rs : " + rs.getString(cpt));
                        data.add(rs.getString(cpt));
                    }
                    System.out.println("data : " + data);
                    model.addRow(data);
                }
                
                //ReponseTRAMAP rep = null;
                if(rs != null)
                {                    
                    rep = new ReponseTRAMAP(ReponseTRAMAP.OK, model);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#SQL : "+getChargeUtile()+"#thread serveur");                  
                }
                else
                {
                    rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, model);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Erreur ResultSet Operations#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, getChargeUtile());
                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+getChargeUtile()+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                oos.writeObject(rep); oos.flush();
                db.closeConnection();
                db = null;
            }
            catch (SQLException e)
            {
                System.err.println("Erreur lors de la déconnexion de la DB : " + e.getMessage());
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
        }
        
        // Construction d'une réponse

    }
    
    private void traiteINPUT_LORRY_WR(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteINPUT_LORRY_WR : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteINPUT_LORRY_WR : adresse distante = " + adresseDistante);
        
        ReponseTRAMAP rep = null;
        
        // la charge utile
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String tImmatriculation = st.nextToken();
        String tSociete = st.nextToken();
        String tType = st.nextToken();
        int tCapacite = -1;
        try
        {
             tCapacite = Integer.parseInt(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, "Capacite transporteur");
            System.err.println("Erreur parseInt Capacite transporteur");
        }
        String tCaracteristique = st.nextToken();
        String cId = st.nextToken();
        String cSociete = st.nextToken();
        String cNature = st.nextToken();
        int cCapacite = -1;
        try
        {
            //System.out.println("TEST PARSEINT cCAPACITE : "+Integer.parseInt(st.nextToken()));
            cCapacite = Integer.parseInt(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, "Capacite container");
            System.err.println("Erreur parseInt Capacite container");
        }
        String cDangers = st.nextToken();
        int poids = -1;
        try
        {
             poids = Integer.parseInt(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, "Poids");
            System.err.println("Erreur parseInt Poids");
            
        }
        String destination = st.nextToken();
        
        if(tCapacite>0 && cCapacite>0 && poids >0)
        {
            try
            {
                ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_mouvements");
                db.openConnection();

                if(db!=null)
                {
                    String query = "INSERT INTO transporteurs (id, societe_id, type, capacite, caracteristiques) "
                                    + "VALUES ('"+tImmatriculation+"', '"+tSociete+"', '"+tType+"', '"+tCapacite+"', '"+tCaracteristique+"')";
                    db.insert(query);

                    String query2 = "INSERT INTO containers (id, societe_id, nature_contenu, capacite, dangers) "
                                    + "VALUES ('"+cId+"', '"+cSociete+"', '"+cNature+"', '"+cCapacite+"', '"+cDangers+"')";
                    db.insert(query2);
                    
                    String query3 = "INSERT INTO mouvements (container_id, transport_entrant, date_arriv, transport_sortant, poids, date_depart, destination) "
                                    + "VALUES ('"+cId+"', '"+tImmatriculation+"', CURDATE(), NULL, '"+poids+"', NULL, '"+destination+"')";
                    db.insert(query3);
                    
                    String query4 = "SELECT * FROM parc WHERE container_id IS NULL LIMIT 1";
                    ResultSet rs = db.select(query4);

                    String query5 = "";
                    String emplacement = "0,0";
                    //ReponseTRAMAP rep = null;
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
                                            + "SET container_id = '"+cId+"', etat_emplacement = 2, date_arriv = CURDATE(), poids = '"+poids+"', destination = '"+destination+"' "
                                            + "WHERE id = "+idParc;
                            db.update(query5);
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs.next()");
                            rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, query4);
                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Parc introuvable : "+query4+"#thread serveur");
                        }
                    }
                    else
                    {
                        rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, query4);
                        guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Parc introuvable : "+query4+"#thread serveur");
                    }
                    
                    System.out.println("Query 1 : "+query);
                    System.out.println("Query 2 : "+query2);
                    System.out.println("Query 3 : "+query3);
                    System.out.println("Query 4 : "+query4);
                    System.out.println("Query 5 : "+query5);

                    //ReponseTRAMAP rep = null;
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Arrivée camion. emplacement : "+emplacement+"#thread serveur");
                    rep = new ReponseTRAMAP(ReponseTRAMAP.OK, emplacement);

                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, tImmatriculation);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+tImmatriculation+"#thread serveur");
                }

                ObjectOutputStream oos;
                try
                {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep); oos.flush();
                    db.closeConnection();
                    db = null;
                }
                catch (SQLException e)
                {
                    System.err.println("Erreur lors de la déconnexion de la DB : " + e.getMessage());
                }
                catch (IOException e)
                {
                    System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
                }
            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
            }
        }
        else
        {
            System.err.println("Erreur dans les données tCapacité, cCapacité ou Poids");
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, tImmatriculation);
            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Erreur data pour : "+tImmatriculation+"#thread serveur");
        }
        // Construction d'une réponse

    }
    
    private void traiteINPUT_LORRY(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteINPUT_LORRY : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteINPUT_LORRY : adresse distante = " + adresseDistante);
        
        ReponseTRAMAP rep = null;
        
        // la charge utile
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String tImmatriculation = st.nextToken();
        String tSociete = st.nextToken();
        String tType = st.nextToken();
        int tCapacite = -1;
        try
        {
             tCapacite = Integer.parseInt(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, "Capacite transporteur");
            System.err.println("Erreur parseInt Capacite transporteur");
        }
        String tCaracteristique = st.nextToken();
        String idReservation = st.nextToken();
        String idContainer = st.nextToken();
        int poids = -1;
        try
        {
             poids = Integer.parseInt(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, "Poids");
            System.err.println("Erreur parseInt Poids");
            
        }
        //String destination = st.nextToken();
        
        if(poids >0)
        {
            try
            {
                ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_mouvements");
                db.openConnection();

                if(db!=null)
                {
                    
                    String query4 = "SELECT * FROM reservations WHERE num_reserv = '"+idReservation+"'";
                    ResultSet rs2 = db.select(query4);

                    String query = "";
                    String query5 = "";
                    String query6 = "";
                    String query2 = "";
                    String query3 = "";
                    String query7 = "";
                    String emplacement = "0,0";
                    //ReponseTRAMAP rep = null;
                    if(rs2 != null)
                    {
                        if(rs2.next())
                        {
                            ResultSetMetaData metaData2 = rs2.getMetaData();

                            Vector<String> data2 = new Vector<>();
                            for(int cpt = 1; cpt <= metaData2.getColumnCount(); cpt++)
                            {
                                System.out.println("rs2 : " + rs2.getString(cpt));
                                data2.add(rs2.getString(cpt));
                            }
                            String idRes = data2.elementAt(0);
                            String destination =data2.elementAt(2);
                            
                            query2 = "SELECT * FROM reservations WHERE container_id = '"+idContainer+"'";
                            ResultSet rs = db.select(query2);

                            query3 = "";
                            emplacement = "0,0";
                            //ReponseTRAMAP rep = null;
                            if(rs != null)
                            {
                                if(rs.next())
                                {
                                    query7 = "SELECT * FROM parc WHERE container_id = '"+idContainer+"'";
                                    ResultSet rs3 = db.select(query7);
                                    
                                    if(rs3 != null)
                                    {
                                        if(rs3.next())
                                        {
                                            ResultSetMetaData metaData = rs3.getMetaData();

                                            Vector<String> data = new Vector<>();
                                            for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                                            {
                                                System.out.println("rs : " + rs3.getString(cpt));
                                                data.add(rs3.getString(cpt));
                                            }
                                            int idParc = Integer.parseInt(data.elementAt(0));
                                            emplacement = data.elementAt(1);
                                             
                                            query5 = "UPDATE reservations "
                                                        + "SET etat = 1 "
                                                        + "WHERE num_reserv = '"+idRes+"'";
                                            db.update(query5);

                                            query3 = "UPDATE parc "
                                                            + "SET etat_emplacement = 2, date_arriv = CURDATE(), poids = '"+poids+"'"
                                                            + "WHERE id = "+idParc;
                                            db.update(query3);

                                            query = "INSERT INTO transporteurs (id, societe_id, type, capacite, caracteristiques) "
                                                    + "VALUES ('"+tImmatriculation+"', '"+tSociete+"', '"+tType+"', '"+tCapacite+"', '"+tCaracteristique+"')";
                                            db.insert(query);

                                            query6 = "INSERT INTO mouvements (container_id, transport_entrant, date_arriv, transport_sortant, poids, date_depart, destination) "
                                                            + "VALUES ('"+idContainer+"', '"+tImmatriculation+"', CURDATE(), NULL, '"+poids+"', NULL, '"+destination+"')";
                                            db.insert(query6);

                                            //ReponseTRAMAP rep = null;
                                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Arrivée camion. emplacement : "+emplacement+"#thread serveur");
                                            rep = new ReponseTRAMAP(ReponseTRAMAP.OK, emplacement);
                                        }
                                        else
                                        {
                                            System.err.println("Erreur dans le rs3.next()");
                                            rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idContainer);
                                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#container introuvable dans Parc : "+idContainer+"#thread serveur");
                                        }
                                    }
                                    else
                                    {
                                        rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idContainer);
                                        guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#container introuvable dans Parc : "+idContainer+"#thread serveur");
                                    }
                                     
                                }
                                else
                                {
                                    System.err.println("Erreur dans le rs.next()");
                                    rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idContainer);
                                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Res du container introuvable : "+idContainer+"#thread serveur");
                                }
                            }
                            else
                            {
                                rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idContainer);
                                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Res du container introuvable : "+idContainer+"#thread serveur");
                            }
                            
                        }
                        else
                        {
                            System.err.println("Erreur dans le rs2.next()");
                            rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idReservation);
                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Reservation introuvable : "+idReservation+"#thread serveur");
                        }
                    }
                    else
                    {
                        rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, idReservation);
                        guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Reservation introuvable : "+idReservation+"#thread serveur");
                    }
                                       
                    
                    System.out.println("Query 1 : "+query);
                    System.out.println("Query 2 : "+query2);
                    System.out.println("Query 3 : "+query3);
                    System.out.println("Query 4 : "+query4);
                    System.out.println("Query 5 : "+query5);
                    System.out.println("Query 6 : "+query6);
                    System.out.println("Query 7 : "+query7);

                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    rep = new ReponseTRAMAP(ReponseTRAMAP.FAIL, tImmatriculation);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+tImmatriculation+"#thread serveur");
                }

                ObjectOutputStream oos;
                try
                {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(rep); oos.flush();
                    db.closeConnection();
                    db = null;
                }
                catch (SQLException e)
                {
                    System.err.println("Erreur lors de la déconnexion de la DB : " + e.getMessage());
                }
                catch (IOException e)
                {
                    System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
                }
            }
            catch (ClassNotFoundException | SQLException e)
            {
                System.err.println("Erreur lors de la connexion à la DB : " + e.getMessage());
            }
        }
        else
        {
            System.err.println("Erreur dans les données du Poids");
            rep = new ReponseTRAMAP(ReponseTRAMAP.WRONG_DATA, tImmatriculation);
            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Erreur data pour : "+tImmatriculation+"#thread serveur");
        }
        // Construction d'une réponse

    }
    
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile)
    {
        this.chargeUtile = chargeUtile;
    }
    public int getType() { return type; }
}
