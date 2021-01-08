/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_pfmcop;

import database_acces.ConnexionDB;
import database_acces.FichierConfig;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import reqrep_poolthreads.ConsoleServeur;
import reqrep_poolthreads.Requete;

/**
 *
 * @author isen0
 */
public class RequetePFMCOP implements Requete, Serializable
{
    private ConsoleServeur guiApplication;
    
    public static int LOGIN_GROUP = 1;

    private int type;
    private String chargeUtile;
    private byte[] rcvDigest;
    private Socket socketClient;
    public RequetePFMCOP(int t, String chu)
    {
        type = t; setChargeUtile(chu); rcvDigest=null;
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequetePFMCOP(int t, String chu, byte[] dig)
    {
        type = t; setChargeUtile(chu); rcvDigest=dig;
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequetePFMCOP(int t, String chu, Socket s)
    {
        type = t; setChargeUtile(chu); socketClient =s;
    }

    public RequetePFMCOP(int type, String chargeUtile, byte[] rcvDigest, Socket socketClient) {
        this.type = type;
        this.chargeUtile = chargeUtile;
        this.rcvDigest = rcvDigest;
        this.socketClient = socketClient;
    }
    
    
    
    public Runnable createRunnable (final Socket s, final ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE CHAMAP 2: "+getType());
//        System.out.println("TEST chrge CHAMAP 2: "+getChargeUtile());
        if (type==LOGIN_GROUP)
            return new Runnable()
            {
                public void run()
                {
                    traiteLOGIN_GROUP(s, cs);
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
    
    private void traiteLOGIN_GROUP(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLOGIN_GROUP : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLOGIN_GROUP : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client +#+ la date +#+ le nombre aleatoire +#+ taille du Digest
        // rcvDigest est le Digest du client
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        long temps = Long.parseLong(st.nextToken());
        double alea = Double.parseDouble(st.nextToken());
        int tailleD = Integer.parseInt(st.nextToken());
        
        ReponsePFMCOP rep = null;
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
                        System.out.println("Path fichier config PFMCOP : "+config);
                        try
                        {
                            prop.load(new FileInputStream(config));
                        }
                        catch(FileNotFoundException e) { System.out.println("Fichier properties (PFMCOP) non trouvé ! message : " + e.getMessage()); }
                        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); } 
                        
                        // Construction du Digest local
                            System.out.println("Construction du Digest serveur dans PFMCOP");
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
                                    rep = new ReponsePFMCOP(ReponsePFMCOP.OK, user+"#"+prop.getProperty("MULTICASTADDRESS")+"#"+prop.getProperty("PORT_CHAT"));
                                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Log in : "+user+"#thread serveur");
                                }
                                else
                                {
                                    rep = new ReponsePFMCOP(ReponsePFMCOP.WRONG_PASSWORD, user);
                                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Mdp incorrect : "+user+"#thread serveur");
                                }
                                                                                       
                            }
                            catch (Exception e)
                            {
                                System.out.println("Aie imprévu construction Digest TRAMAP: " + e.getMessage() + e.getClass());
                                rep = new ReponsePFMCOP(ReponsePFMCOP.FAIL, user);
                            }

                    }
                    else
                    {
                        System.err.println("Erreur dans le rs.next()");
                        rep = new ReponsePFMCOP(ReponsePFMCOP.FAIL, user);
                        guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                    }
                }
                else
                {
                    rep = new ReponsePFMCOP(ReponsePFMCOP.FAIL, user);
                    guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponsePFMCOP(ReponsePFMCOP.FAIL, user);
                guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("PFMCOP: Envoi de la reponse a App_Chat_PFM : "+rep.getCode()+" - "+rep.getChargeUtile());
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
}
