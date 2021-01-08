/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_chamap;

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
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isen0
 */
public class RequeteCHAMAP implements Requete, Serializable
{
    private ConsoleServeur guiApplication;
    
    public static int LOGIN_TRAF = 1;
    public static int MAKE_BILL = 2;


    private int type;
    private String chargeUtile;
    private byte[] rcvDigest;
    private Socket socketClient;
    public RequeteCHAMAP(int t, String chu)
    {
        type = t; setChargeUtile(chu); rcvDigest=null;
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequeteCHAMAP(int t, String chu, byte[] dig)
    {
        type = t; setChargeUtile(chu); rcvDigest=dig;
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequeteCHAMAP(int t, String chu, Socket s)
    {
        type = t; setChargeUtile(chu); socketClient =s;
    }

    public RequeteCHAMAP(int type, String chargeUtile, byte[] rcvDigest, Socket socketClient) {
        this.type = type;
        this.chargeUtile = chargeUtile;
        this.rcvDigest = rcvDigest;
        this.socketClient = socketClient;
    }
    
    
    
    public Runnable createRunnable (final Socket s, final ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE CHAMAP 2: "+getType());
//        System.out.println("TEST chrge CHAMAP 2: "+getChargeUtile());
        if (type==LOGIN_TRAF)
            return new Runnable()
            {
                public void run()
                {
                    traiteLOGIN_TRAF(s, cs);
                }
            };
        else if (type==MAKE_BILL)
            return new Runnable()
            {
                public void run()
                {
                    traiteMAKE_BILL(s, cs);
                }
            };
        else return null;
    }
    
    public String getChargeUtile() { return chargeUtile; }
    public void setChargeUtile(String chargeUtile)
    {
        this.chargeUtile = chargeUtile;
    }
    public byte[] getRcvDigest() {
        return rcvDigest;
    }

    public void setRcvDigest(byte[] rcvDigest) {
        this.rcvDigest = rcvDigest;
    }
    public int getType() { return type; }
    
    private void traiteLOGIN_TRAF(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLOGIN_TRAF : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLOGIN_TRAF : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client +#+ la date +#+ le nombre aleatoire +#+ taille du Digest
        // rcvDigest est le Digest du client
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        long temps = Long.parseLong(st.nextToken());
        double alea = Double.parseDouble(st.nextToken());
        int tailleD = Integer.parseInt(st.nextToken());
        
        ReponseCHAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
            db.openConnection();
            
            if(db!=null)
            {
                String query = "SELECT password FROM personnel WHERE login = '"+user+"'";
                ResultSet rs = db.select(query);
                
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
                        
                        Properties prop = new Properties();
                        String config = System.getProperty("user.dir") + System.getProperty("file.separator")+ "configServ.properties";
                        System.out.println("Path fichier config CHAMAP : "+config);
                        try
                        {
                            prop.load(new FileInputStream(config));
                        }
                        catch(FileNotFoundException e) { System.out.println("Fichier properties (CHAMAP) non trouvé ! message : " + e.getMessage()); }
                        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); } 
                        
                        // Construction du Digest local
                            System.out.println("Construction du Digest serveur dans CHAMAP");
                            try
                            {
                                MessageDigest md = MessageDigest.getInstance(prop.getProperty("ALGODIGEST"), prop.getProperty("PROVIDERCODE"));
                                md.update(user.getBytes());
                                md.update(mdp.getBytes());
                                
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                DataOutputStream bdos = new DataOutputStream(baos);
                                bdos.writeLong(temps);
                                bdos.writeDouble(alea);
                                
                                md.update(baos.toByteArray());
                                byte[] localDigest = md.digest();
                                
                                if(MessageDigest.isEqual(rcvDigest, localDigest))
                                {
                                    rep = new ReponseCHAMAP(ReponseCHAMAP.OK, user);
                                    guiApplication.TraceEvenements("CHAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Log in : "+user+"#thread serveur");
                                }
                                else
                                {
                                    rep = new ReponseCHAMAP(ReponseCHAMAP.WRONG_PASSWORD, user);
                                    guiApplication.TraceEvenements("CHAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Mdp incorrect : "+user+"#thread serveur");
                                }
                                                                                       
                            }
                            catch (Exception e)
                            {
                                System.out.println("Aie imprévu construction Digest TRAMAP: " + e.getMessage() + e.getClass());
                                rep = new ReponseCHAMAP(ReponseCHAMAP.FAIL, user);
                            }

                    }
                    else
                    {
                        System.err.println("Erreur dans le rs.next()");
                        rep = new ReponseCHAMAP(ReponseCHAMAP.FAIL, user);
                        guiApplication.TraceEvenements("CHAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                    }
                }
                else
                {
                    rep = new ReponseCHAMAP(ReponseCHAMAP.FAIL, user);
                    guiApplication.TraceEvenements("CHAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseCHAMAP(ReponseCHAMAP.FAIL, user);
                guiApplication.TraceEvenements("CHAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("CHAMAP: Envoi de la reponse a TRAMAP : "+rep.getCode()+" - "+rep.getChargeUtile());
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
    
    private void traiteMAKE_BILL(Socket sock, ConsoleServeur cs)
    {/*
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteMAKE_BILL : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteMAKE_BILL : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        String pass = st.nextToken();
        
        ReponseCHAMAP rep = null;
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
                            rep = new ReponseTRAMAP(ReponseTRAMAP.OK, user);
                            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Log in : "+user+"#thread serveur");
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
*/
    }
}
