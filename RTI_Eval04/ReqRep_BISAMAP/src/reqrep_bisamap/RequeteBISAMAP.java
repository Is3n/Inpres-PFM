/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reqrep_bisamap;

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
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isen0
 */
public class RequeteBISAMAP implements Requete, Serializable
{
    private ConsoleServeur guiApplication;
    
    public static int HANDSHAKE = 0;
    public static int LOGIN = 1;
    public static int GET_NEXT_BILL = 2;
    public static int VALIDATE_BILL = 3;
    public static int LIST_BILLS = 4;
    public static int SEND_BILLS = 5;
    public static int REC_PAY = 6;
    public static int LIST_WAITING = 7;

    private int type;
    private String chargeUtile;
    private byte[] rcvDigest;
    private byte[] rcvBytes2;
    private Socket socketClient;
    private final String cryptoUtilPath;
    public RequeteBISAMAP(int t, String chu)
    {
        type = t; setChargeUtile(chu); rcvDigest=null;
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequeteBISAMAP(int t, String chu, byte[] dig)
    {
        type = t; setChargeUtile(chu); rcvDigest=dig;
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
//        System.out.println("TEST TYPE TRAMAP : "+getType());
//        System.out.println("TEST chrge TRAMAP : "+getChargeUtile());
    }
    public RequeteBISAMAP(int t, String chu, Socket s)
    {
        type = t; setChargeUtile(chu); socketClient =s;
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
    }

    public RequeteBISAMAP(int type, String chargeUtile, byte[] rcvDigest, Socket socketClient) {
        this.type = type;
        this.chargeUtile = chargeUtile;
        this.rcvDigest = rcvDigest;
        this.socketClient = socketClient;
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
    }

    public RequeteBISAMAP(int type, String chargeUtile, byte[] rcvDigest, byte[] rcvBytes2) {
        this.type = type;
        this.chargeUtile = chargeUtile;
        this.rcvDigest = rcvDigest;
        this.rcvBytes2 = rcvBytes2;
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
    }
    
    
    
    public Runnable createRunnable (final Socket s, final ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE BISAMAP 2: "+getType());
//        System.out.println("TEST chrge BISAMAP 2: "+getChargeUtile());
        if (type==LOGIN)
            return new Runnable()
            {
                public void run()
                {
                    traiteLOGIN(s, cs);
                }
            };
        else if (type==GET_NEXT_BILL)
            return new Runnable()
            {
                public void run()
                {
                    traiteGET_NEXT_BILL(s, cs);
                }
            };
        else if (type==VALIDATE_BILL)
            return new Runnable()
            {
                public void run()
                {
                    traiteVALIDATE_BILL(s, cs);
                }
            };
        else if (type==LIST_BILLS)
            return new Runnable()
            {
                public void run()
                {
                    traiteLIST_BILLS(s, cs);
                }
            };
        else if (type==SEND_BILLS)
            return new Runnable()
            {
                public void run()
                {
                    traiteSEND_BILLS(s, cs);
                }
            };
        else if (type==REC_PAY)
            return new Runnable()
            {
                public void run()
                {
                    traiteREC_PAY(s, cs);
                }
            };
        else if (type==LIST_WAITING)
            return new Runnable()
            {
                public void run()
                {
                    traiteLIST_WAITING(s, cs);
                }
            };
        else if (type==HANDSHAKE)
            return new Runnable()
            {
                public void run()
                {
                    traiteHANDSHAKE(s, cs);
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

    public byte[] getRcvBytes2() {
        return rcvBytes2;
    }

    public void setRcvBytes2(byte[] rcvBytes2) {
        this.rcvBytes2 = rcvBytes2;
    }
    
    
    
    public int getType() { return type; }
    
    private void traiteHANDSHAKE(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteHANDSHAKE : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteHANDSHAKE : adresse distante = " + adresseDistante);        
        
        ReponseBISAMAP rep = null;
        
        Properties prop = new Properties();
        String config = System.getProperty("user.dir") + System.getProperty("file.separator")+ "configServ.properties";
        System.out.println("Path fichier config BISAMAP : "+config);
        try
        {
            prop.load(new FileInputStream(config));
        }
        catch(FileNotFoundException e) { System.out.println("Fichier properties (BISAMAP) non trouvé ! message : " + e.getMessage()); }
        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); } 
        
        try
        {
            System.out.println("Handshake: Génération de la clé secrète 1");
            KeyGenerator cleGen = KeyGenerator.getInstance(prop.getProperty("ALGOSYM"), prop.getProperty("PROVIDERCODE"));
            cleGen.init(new SecureRandom());
            SecretKey cleSecrete = cleGen.generateKey();
            System.out.println(" *** Clé générée = " + cleSecrete.toString());
            
            System.out.println("Handshake: Génération de la clé secrète 2");
            KeyGenerator cleGen2 = KeyGenerator.getInstance(prop.getProperty("ALGOSYM"), prop.getProperty("PROVIDERCODE"));
            cleGen2.init(new SecureRandom());
            SecretKey cleSecrete2 = cleGen2.generateKey();
            System.out.println(" *** Clé générée = " + cleSecrete2.toString());
            
            cryptoStuff.setCleSecrete1(cleSecrete);
            cryptoStuff.setCleSecrete2(cleSecrete2);
            
            System.out.println("Handshake: Acces au KeyStore");
            KeyStore serverKS = KeyStore.getInstance(prop.getProperty("KSALGO"), prop.getProperty("PROVIDERCODE"));
            System.out.println("Handshake: TEST affichage avant le LOAD");
            serverKS.load(new FileInputStream(cryptoUtilPath+System.getProperty("file.separator")+ prop.getProperty("KSSERVFILE")),prop.getProperty("KSPWD").toCharArray());
            
            System.out.println("Handshake: Recuperation du certificat");
            X509Certificate certif = (X509Certificate)serverKS.getCertificate("cerIsenClient");
            System.out.println("Handshake: Recuperation de la cle publique");
            PublicKey clePublique = certif.getPublicKey();
            System.out.println("Handshake: Cle publique recuperee = "+clePublique.toString());
            
            System.out.println("Handshake: Cryptage de la cle secrete");
            Cipher chiffrement = Cipher.getInstance(prop.getProperty("ALGOCIPHER"),prop.getProperty("PROVIDERCODE"));
            chiffrement.init(Cipher.ENCRYPT_MODE, clePublique);
            byte[] texteClair = cleSecrete.getEncoded();
            byte[] texteCrypte = chiffrement.doFinal(texteClair);
            String texteCryptéAff = new String (texteCrypte);
            System.out.println(new String(texteClair) + " ---> " + texteCryptéAff);
            
            System.out.println("Handshake: Cryptage de la cle secrete 2");
            Cipher chiffrement2 = Cipher.getInstance(prop.getProperty("ALGOCIPHER"),prop.getProperty("PROVIDERCODE"));
            chiffrement.init(Cipher.ENCRYPT_MODE, clePublique);
            byte[] texteClair2 = cleSecrete2.getEncoded();
            byte[] texteCrypte2 = chiffrement2.doFinal(texteClair2);
            String texteCryptéAff2 = new String (texteCrypte2);
            System.out.println(new String(texteClair2) + " ---> " + texteCryptéAff2);
            
            System.out.println("Handshake: Envoi de la clé secrète crytée");
            rep = new ReponseBISAMAP(ReponseBISAMAP.OK, texteCrypte, texteCrypte2);
        }
        catch (Exception e)
        {
            System.err.println("Aie aie imprévu " + e.getMessage());
            rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, getChargeUtile());
        }   
            
        ObjectOutputStream oos;
        try
        {
            oos = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("BISAMAP: Envoi de la reponse a App_Compta : "+rep.getCode());
            oos.writeObject(rep); oos.flush();

        }
        catch (IOException e)
        {
            System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
        }

    }
    
    private void traiteLOGIN(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLOGIN : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLOGIN : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client +#+ la date +#+ le nombre aleatoire +#+ taille du Digest
        // rcvDigest est le Digest du client
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        long temps = Long.parseLong(st.nextToken());
        double alea = Double.parseDouble(st.nextToken());
        int tailleD = Integer.parseInt(st.nextToken());
        
        ReponseBISAMAP rep = null;
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
                        System.out.println("Path fichier config BISAMAP : "+config);
                        try
                        {
                            prop.load(new FileInputStream(config));
                        }
                        catch(FileNotFoundException e) { System.out.println("Fichier properties (BISAMAP) non trouvé ! message : " + e.getMessage()); }
                        catch(IOException e) { System.out.println("Aie: "+ e.getMessage()); } 
                        
                        // Construction du Digest local
                            System.out.println("Construction du Digest serveur dans BISAMAP");
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
                                    rep = new ReponseBISAMAP(ReponseBISAMAP.OK, user);
                                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Log in : "+user+"#thread serveur");
                                }
                                else
                                {
                                    rep = new ReponseBISAMAP(ReponseBISAMAP.WRONG_PASSWORD, user);
                                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Mdp incorrect : "+user+"#thread serveur");
                                }
                                                                                       
                            }
                            catch (Exception e)
                            {
                                System.out.println("Aie imprévu construction Digest TRAMAP: " + e.getMessage() + e.getClass());
                                rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                            }

                    }
                    else
                    {
                        System.err.println("Erreur dans le rs.next()");
                        rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                    }
                }
                else
                {
                    rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#User introuvable : "+user+"#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("BISAMAP: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
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
    
    private void traiteGET_NEXT_BILL(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteGET_NEXT_BILL : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteGET_NEXT_BILL : adresse distante = " + adresseDistante);
        
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        
        ReponseBISAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
            db.openConnection();
            
            if(db!=null)
            {
                String query = "SELECT id, societe_id, date_facture, montantHTVA, montantTVAC FROM factures WHERE facture_validee = 0 LIMIT 1";
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
                        String id = data.elementAt(0);
                        String societe = data.elementAt(1);
                        String dateFacture = data.elementAt(2);
                        String htva = data.elementAt(3);
                        String tvac = data.elementAt(4);
                        
                        String resultat = user+"#"+id+"#"+societe+"#"+dateFacture+"#"+htva+"#"+tvac;
                        System.out.println("Resultat de la requete : "+resultat);
                        
                        rep = new ReponseBISAMAP(ReponseBISAMAP.OK, resultat);
                        
//                        System.out.println("Chiffrement de la reponse dans BISAMAP - traiteGET_NEXT_BILL");
//                        try
//                        {
//                            System.out.println("traiteGET_NEXT_BILL: Cryptage de la reponse");
//                            Cipher chiffrement = Cipher.getInstance(FichierConfig.get("ALGOCIPHER"),FichierConfig.get("PROVIDERCODE"));
//                            chiffrement.init(Cipher.ENCRYPT_MODE, cryptoStuff.getCleSecrete());
//                            byte[] texteClair = resultat.getBytes();
//                            byte[] texteCrypté = chiffrement.doFinal(texteClair);
//                            String texteCryptéAff = new String (texteCrypté);
//                            System.out.println(new String(texteClair) + " ---> " + texteCryptéAff);
//
//                            System.out.println("traiteGET_NEXT_BILL: Envoi de la reponse crytée");
//                            rep = new ReponseBISAMAP(ReponseBISAMAP.OK, texteCrypté);
//
//                        }
//                        catch (Exception e)
//                        {
//                            System.out.println("Aie imprévu cryptage BISAMAP - traiteGET_NEXT_BILL: " + e.getMessage() + e.getClass());
//                            rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
//                        }

                    }
                    else
                    {
                        System.err.println("Erreur dans le rs.next()");
                        rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#No result : "+user+"#thread serveur");
                    }
                }
                else
                {
                    rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#No result : "+user+"#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
            }
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("BISAMAP - traiteGET_NEXT_BILL: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
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
    
    private void traiteVALIDATE_BILL(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteVALIDATE_BILL : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteVALIDATE_BILL : adresse distante = " + adresseDistante);
        
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        int idFacture = Integer.parseInt(st.nextToken());
        String message = user+"#"+idFacture;
        boolean ok = true;
//        boolean ok = false;
//        try
//        {
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Vérification de la signature");
//            KeyStore serverKS = null;
//            serverKS = KeyStore.getInstance(FichierConfig.get("KSALGO"), FichierConfig.get("PROVIDERCODE"));
//            serverKS.load(new FileInputStream(cryptoUtilPath+System.getProperty("file.separator")+ FichierConfig.get("KSSERVFILE")),FichierConfig.get("KSPWD").toCharArray());
//
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Recuperation du certificat");
//            X509Certificate certif = (X509Certificate)serverKS.getCertificate("cerIsenClient");
//
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Recuperation de la cle publique");
//            PublicKey clePublique = certif.getPublicKey();
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Cle publique recuperee = "+clePublique.toString());
//
//            System.out.println("Debut de verification de la signature construite");
//            Signature sv = Signature.getInstance(FichierConfig.get("ALGOSIGNATURE"),FichierConfig.get("PROVIDERCODE"));
//            sv.initVerify(clePublique);
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Hachage du message");
//            sv.update(message.getBytes());
//            System.out.println("BISAMAP - traiteVALIDATE_BILL : Verification de la signature construite");
//
//            ok = sv.verify(getRcvDigest());                  
//        }
//        catch (Exception e)
//        {
//            System.err.println("BISAMAP - traiteVALIDATE_BILL : Aie aie imprévu " + e.getMessage());
//        }
        
        ReponseBISAMAP rep = null;

        if(ok)
        {
            System.out.println("BISAMAP - traiteVALIDATE_BILL : Signature testee avec succes");
                
            try
            {
                ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
                db.openConnection();

                if(db!=null)
                {
                    String query = "SELECT matricule FROM personnel WHERE login = '"+user+"'";
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
                            int id = Integer.parseInt(data.elementAt(0));
                    
                            String query2 = "UPDATE factures SET facture_validee = 1, validateur_id = "+id+" WHERE id = "+idFacture;
                            db.update(query2);                            
                            System.out.println("BISAMAP - traiteVALIDATE_BILL : Update réalisé avec succes");
                            
                            rep = new ReponseBISAMAP(ReponseBISAMAP.OK, "Signature + Update OK");
                    
                        }
                    }
                    else
                    {
                        rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#No result : "+user+"#thread serveur");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
                }

                ObjectOutputStream oos;
                try
                {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    System.out.println("BISAMAP - traiteVALIDATE_BILL: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
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
            System.out.println("BISAMAP - traiteVALIDATE_BILL : Signature testee sans succes");
            rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL_VERIF, user+"#Fail verif signature");
            guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#fail verif signature : "+user+"#thread serveur");
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("BISAMAP - traiteVALIDATE_BILL: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
                oos.writeObject(rep); oos.flush();
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
        
    }
    
    private void traiteLIST_BILLS(Socket sock, ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE TRAMAP 3: "+getType());
//        System.out.println("TEST chrge TRAMAP 3: "+getChargeUtile());
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLIST_BILLS : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLIST_BILLS : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String DateDebut = st.nextToken();
        String DateFin = st.nextToken();
        String Societe = st.nextToken();
        
        ReponseBISAMAP rep = null;
        try
        {
            ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
            db.openConnection();
            
            if(db!=null)
            {
                String query = null;
                

                if(Societe.equals("_") == false)
                {
                    query = "SELECT factures.id, factures.societe_id, factures.date_facture, factures.montantHTVA, factures.montantTVAC, "
                                    + "factures.facture_validee, personnel.login, factures.facture_envoyee, factures.type_envoi, factures.facture_payee "
                                    + "FROM factures LEFT JOIN personnel ON factures.validateur_id = personnel.matricule "
                                    + "WHERE factures.societe_id = '"+Societe+"' "
                                    + "AND (factures.date_facture >= '"+DateDebut+"' AND factures.date_facture <= '"+DateFin+"')";
                    System.out.println("Query : "+query);
                }
                else
                {
                    query = "SELECT factures.id, factures.societe_id, factures.date_facture, factures.montantHTVA, factures.montantTVAC, "
                                    + "factures.facture_validee, personnel.login, factures.facture_envoyee, factures.type_envoi, factures.facture_payee "
                                    + "FROM factures LEFT JOIN personnel ON factures.validateur_id = personnel.matricule "
                                    + "WHERE (factures.date_facture >= '"+DateDebut+"' AND factures.date_facture <= '"+DateFin+"')";
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
                
                if(rs != null)
                {                    
                    rep = new ReponseBISAMAP(ReponseBISAMAP.OK, model);
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#SQL : "+getChargeUtile()+"#thread serveur");                  
                }
                else
                {
                    rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, model);
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Erreur ResultSet Operations#thread serveur");
                }
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
                rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, getChargeUtile());
                guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+getChargeUtile()+"#thread serveur");
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
    
    private void traiteSEND_BILLS(Socket sock, ConsoleServeur cs)
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
        
        ReponseBISAMAP rep = null;
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
    
    private void traiteREC_PAY(Socket sock, ConsoleServeur cs)
    {
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteREC_PAY : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteREC_PAY : adresse distante = " + adresseDistante);
        
        ReponseBISAMAP rep = null;
        
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String user = st.nextToken();
        int idFacture=-1;
        double montant=-1;
        try
        {
            idFacture = Integer.parseInt(st.nextToken());
            montant = Double.parseDouble(st.nextToken());
        }
        catch(NumberFormatException e)
        {
            rep = new ReponseBISAMAP(ReponseBISAMAP.WRONG_DATA, "Erreur parsing");
            System.err.println("BISAMAP - traiteREC_PAY : Erreur parsing des infos");
        }
        if(st.hasMoreTokens())
        {
            int tailleHb = Integer.parseInt(st.nextToken());
        }
        
        if(idFacture>0 && montant>=0)
        {
            boolean ok = true;
//            boolean ok = false;
//            // Dechiffrement
//            try
//            {
//
//                System.out.println("traiteREC_PAY: Decryptage de la reponse");
//                Cipher chiffrement = Cipher.getInstance(FichierConfig.get("ALGOCIPHER"),FichierConfig.get("PROVIDERCODE"));
//                chiffrement.init(Cipher.DECRYPT_MODE, cryptoStuff.getCleSecrete1());
//                byte[] texteDecode = chiffrement.doFinal(this.getRcvDigest());
//                String bankDecodeClair = new String (texteDecode);
//                System.out.println("traiteREC_PAY: Decryptage de la reponse OK : "+bankDecodeClair);
//            }
//            catch (Exception ee)
//            { System.out.println("Aie aie imprévu " + ee.getMessage() + ee.getClass()); }
//            try
//            {
//                String myMessage = user+"#"+idFacture+"#"+montant;
//                byte[] message = myMessage.getBytes();               
//    
//                System.out.println("Debut de verification de la signature construite");
//                Mac hlocal = Mac.getInstance(FichierConfig.get("ALGOHMAC"),FichierConfig.get("PROVIDERCODE"));
//                hlocal.init(cryptoStuff.getCleSecrete2());
//                System.out.println("BISAMAP - traiteREC_PAY : Hachage du message");
//                hlocal.update(message);
//                System.out.println("BISAMAP - traiteREC_PAY : Verification des HMACS");
//                byte[] hlocalb = hlocal.doFinal();
//                
//                if(MessageDigest.isEqual(this.getRcvBytes2(), hlocalb))
//                {
//                    ok = true;
//                }
//                else
//                {
//                    ok = false;
//                }
//            }
//            catch (Exception e)
//            {
//                System.err.println("BISAMAP - traiteREC_PAY : Aie aie imprévu " + e.getMessage());
//            }

            if(ok)
            {
                System.out.println("BISAMAP - traiteREC_PAY : HMAC teste avec succes");

                try
                {
                    ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
                    db.openConnection();

                    if(db!=null)
                    {
                        String query = "SELECT id, montantTVAC FROM factures WHERE id = "+idFacture;
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
                                int id = Integer.parseInt(data.elementAt(0));
                                double TVAC = Double.parseDouble(data.elementAt(1));
                                
                                System.out.println("BISAMAP - traiteREC_PAY : Montant : "+montant);
                                System.out.println("BISAMAP - traiteREC_PAY : TVAC : "+TVAC);
                                
                                if(TVAC == montant)
                                {
                                    String query2 = "UPDATE factures SET facture_payee = 1 WHERE id = "+idFacture;
                                    db.update(query2);                            
                                    System.out.println("BISAMAP - traiteREC_PAY : Update réalisé avec succes");

                                    rep = new ReponseBISAMAP(ReponseBISAMAP.OK, "Signature + Update OK");
                                }
                                else
                                {
                                    System.out.println("BISAMAP - traiteREC_PAY : Le Montant payé ne correpond pas ");

                                    rep = new ReponseBISAMAP(ReponseBISAMAP.WRONG_DATA, "Signature OK + Mauvais montant");
                                }
                            }
                        }
                        else
                        {
                            rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                            guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#No result : "+user+"#thread serveur");
                        }
                    }
                    else
                    {
                        System.err.println("Veuillez vous connecter à la base de donnee");
                        rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, user);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+user+"#thread serveur");
                    }

                    ObjectOutputStream oos;
                    try
                    {
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        System.out.println("BISAMAP - traiteREC_PAY: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
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
                System.out.println("BISAMAP - traiteREC_PAY : HMAC teste sans succes");
                rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL_VERIF, user+"#Fail verif HMAC");
                guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#fail verif HMAC : "+user+"#thread serveur");

                ObjectOutputStream oos;
                try
                {
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    System.out.println("BISAMAP - traiteREC_PAY: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
                    oos.writeObject(rep); oos.flush();
                }
                catch (IOException e)
                {
                    System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
                }
            }
        }
        else
        {
            System.err.println("BISAMAP - traiteREC_PAY: Erreur dans le parsing des données");
            rep = new ReponseBISAMAP(ReponseBISAMAP.WRONG_DATA, "Erreur Parsing");
            guiApplication.TraceEvenements(socketClient.getRemoteSocketAddress().toString()+"#Erreur data pour : "+idFacture+"#thread serveur");
        }
    }
    
    private void traiteLIST_WAITING(Socket sock, ConsoleServeur cs)
    {
//        System.out.println("TEST TYPE TRAMAP 3: "+getType());
//        System.out.println("TEST chrge TRAMAP 3: "+getChargeUtile());
        guiApplication = cs;
        socketClient=sock;
        // Affichage des informations
        String adresseDistante = sock.getRemoteSocketAddress().toString();
        System.out.println("Début de traiteLIST_BILLS : adresse distante = " + adresseDistante);
        FichierLog.UpdateFich("Début de traiteLIST_BILLS : adresse distante = " + adresseDistante);
        
        // la charge utile est le nom du client + # + le password
        StringTokenizer st = new StringTokenizer(getChargeUtile(), "#");
        String DateDebut = st.nextToken();
        String DateFin = st.nextToken();
        String Societe = st.nextToken();
        
        ReponseBISAMAP rep = null;
        
        String message = getChargeUtile();
        boolean ok = true;
//        boolean ok = false;
//        try
//        {
//            System.out.println("BISAMAP - traiteLIST_WAITING : Vérification de la signature");
//            KeyStore serverKS = null;
//            serverKS = KeyStore.getInstance(FichierConfig.get("KSALGO"), FichierConfig.get("PROVIDERCODE"));
//            serverKS.load(new FileInputStream(cryptoUtilPath+System.getProperty("file.separator")+ FichierConfig.get("KSSERVFILE")),FichierConfig.get("KSPWD").toCharArray());
//
//            System.out.println("BISAMAP - traiteLIST_WAITING : Recuperation du certificat");
//            X509Certificate certif = (X509Certificate)serverKS.getCertificate("cerIsenClient");
//
//            System.out.println("BISAMAP - traiteLIST_WAITING : Recuperation de la cle publique");
//            PublicKey clePublique = certif.getPublicKey();
//            System.out.println("BISAMAP - traiteLIST_WAITING : Cle publique recuperee = "+clePublique.toString());
//
//            System.out.println("Debut de verification de la signature construite");
//            Signature sv = Signature.getInstance(FichierConfig.get("ALGOSIGNATURE"),FichierConfig.get("PROVIDERCODE"));
//            sv.initVerify(clePublique);
//            System.out.println("BISAMAP - traiteLIST_WAITING : Hachage du message");
//            sv.update(message.getBytes());
//            System.out.println("BISAMAP - traiteLIST_WAITING : Verification de la signature construite");
//
//            ok = sv.verify(getRcvDigest());                  
//        }
//        catch (Exception e)
//        {
//            System.err.println("BISAMAP - traiteVALIDATE_BILL : Aie aie imprévu " + e.getMessage());
//        }

        if(ok)
        {
            try
            {
                ConnexionDB db = new ConnexionDB(FichierConfig.getNomsFichs("config"),"bd_compta");
                db.openConnection();

                if(db!=null)
                {
                    String query = null;


                    if(Societe.equals("_") == false)
                    {
                        query = "SELECT factures.id, factures.societe_id, factures.date_facture, factures.montantHTVA, factures.montantTVAC, "
                                        + "factures.facture_validee, personnel.login, factures.facture_envoyee, factures.type_envoi, factures.facture_payee "
                                        + "FROM factures LEFT JOIN personnel ON factures.validateur_id = personnel.matricule "
                                        + "WHERE factures.societe_id = '"+Societe+"' "
                                        + "AND factures.facture_payee = 0 "
                                        + "AND (factures.date_facture >= '"+DateDebut+"' AND factures.date_facture <= '"+DateFin+"')";
                        System.out.println("Query : "+query);
                    }
                    else
                    {
                        query = "SELECT factures.id, factures.societe_id, factures.date_facture, factures.montantHTVA, factures.montantTVAC, "
                                        + "factures.facture_validee, personnel.login, factures.facture_envoyee, factures.type_envoi, factures.facture_payee "
                                        + "FROM factures LEFT JOIN personnel ON factures.validateur_id = personnel.matricule "
                                        + "WHERE (factures.date_facture >= '"+DateDebut+"' AND factures.date_facture <= '"+DateFin+"') "
                                        + "AND factures.facture_payee = 0 ";
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

                    if(rs != null)
                    {                    
                        rep = new ReponseBISAMAP(ReponseBISAMAP.OK, model);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#SQL : "+getChargeUtile()+"#thread serveur");                  
                    }
                    else
                    {
                        rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, model);
                        guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Erreur ResultSet Operations#thread serveur");
                    }
                }
                else
                {
                    System.err.println("Veuillez vous connecter à la base de donnee");
                    rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL, getChargeUtile());
                    guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#Non connecté à la DB : "+getChargeUtile()+"#thread serveur");
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
            System.out.println("BISAMAP - traiteLIST_WAITING : Signature testee sans succes");
            rep = new ReponseBISAMAP(ReponseBISAMAP.FAIL_VERIF, "Fail verif signature");
            guiApplication.TraceEvenements("BISAMAP#"+socketClient.getRemoteSocketAddress().toString()+"#fail verif signature#thread serveur");
            
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("BISAMAP - traiteLIST_WAITING: Envoi de la reponse a App_Compta : "+rep.getCode()+" - "+rep.getChargeUtile());
                oos.writeObject(rep); oos.flush();
            }
            catch (IOException e)
            {
                System.err.println("Erreur réseau ? [" + e.getMessage() + "]");
            }
        }
        

    }
}