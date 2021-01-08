/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_compta;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import reqrep_bisamap.ReponseBISAMAP;
import reqrep_bisamap.RequeteBISAMAP;

/**
 *
 * @author isen0
 */
public class FenAppCompta extends javax.swing.JFrame {

    /**
     * Creates new form FenAppCompta
     */
    private static String formDate;
    private static String formTime;
    private Socket cliSock=null;
    private String ChargeRecue=null;
    public static String cryptoUtilPath;
    public static SecretKey secretKey=null;
    public static SecretKey secretKey2=null;
    public FenAppCompta() {
        initComponents();
        setLocation(900, 200); 
        setTitle("Application Compta");
        setIcon();
        Affichagedate();
        cryptoUtilPath=null;
    }
    public FenAppCompta(Socket loginSock, String ChargeLogin) {
        initComponents();
        setLocation(900, 200); 
        setTitle("Application Compta");
        setIcon();
        Affichagedate();
        cliSock = loginSock;
        ChargeRecue = ChargeLogin;
        jLabelResponsable.setText(ChargeRecue);
        
        cryptoUtilPath = System.getProperty("user.home")+System.getProperty("file.separator")+"Documents"+System.getProperty("file.separator")+
                "HEPL"+System.getProperty("file.separator")+"20-21"+System.getProperty("file.separator")+"RTI"+System.getProperty("file.separator")+
                "Java"+System.getProperty("file.separator")+"CryptoUtilities";
        
        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;
        
        String adresse = app_compta.FichierConfig.get("HOST");
        int port = Integer.parseInt(app_compta.FichierConfig.get("PORT_COMPTA"));
        RequeteBISAMAP req = null;

        try
        {
            cliSock = new Socket(adresse, port);
            System.out.println("infos socket : "+cliSock.getInetAddress().toString());
        }
        catch (UnknownHostException e)
        { 
            System.err.println("Erreur ! Host non trouvé [" + e + "]"); 
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur : " + e.getMessage());
        }
        catch (IOException e)
        { 
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur : " + e.getMessage());
        }

        String chargeUtile = ChargeRecue;

        req = null;
        req = new RequeteBISAMAP(RequeteBISAMAP.HANDSHAKE, chargeUtile);
        ois=null; oos=null;
        try
        {
            System.out.println("Envoi de la requete HANDSHAKE : "+req.getChargeUtile());
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);
            oos.flush();
            FichierLog.UpdateFich("Envoi d'une requete de type HANDSHAKE : " +req.getChargeUtile());
        }
        catch (IOException e)
        { System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); }

        // Lecture de la réponse
        ReponseBISAMAP rep = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseBISAMAP)ois.readObject();
            System.out.println(" *** Reponse reçue : " + rep.getCryptoObj().toString());
            //FichierLog.UpdateFich(" Reponse reçue : " + rep.getChargeUtile());

            if(rep.getCode() == ReponseBISAMAP.OK)
            {                     
                System.out.println(" Requete Handshake : "+req.getChargeUtile()+" effectué avec succès !! " );
                FichierLog.UpdateFich(" Requete Handshake : "+req.getChargeUtile()+" effectué avec succès !! " );

                byte[] texteCrypte = rep.getCryptoObj();                      
                // Dechiffrement
                try
                {
                    System.out.println("Handshake: Acces au KeyStore");
                    KeyStore clientKS = KeyStore.getInstance(app_compta.FichierConfig.get("KSALGO"), app_compta.FichierConfig.get("PROVIDERCODE"));
                    clientKS.load(new FileInputStream(cryptoUtilPath+System.getProperty("file.separator")+ app_compta.FichierConfig.get("KSCLIENTFILE")),app_compta.FichierConfig.get("KSPWD").toCharArray());
                    
                    System.out.println("Handshake: Recuperation de la cle privee");
                    PrivateKey clePrivee;
                    clePrivee = (PrivateKey) clientKS.getKey("IsenClient", app_compta.FichierConfig.get("KSENTRYPWD").toCharArray());
                    System.out.println(" *** Cle privee recuperee = " + clePrivee.toString());
                    
                    System.out.println("Handshake: Decryptage de la cle secrete");
                    Cipher chiffrement = Cipher.getInstance(app_compta.FichierConfig.get("ALGOCIPHER"),app_compta.FichierConfig.get("PROVIDERCODE"));
                    chiffrement.init(Cipher.DECRYPT_MODE, clePrivee);
                    byte[] texteDecode = chiffrement.doFinal(rep.getCryptoObj());
                    secretKey = new SecretKeySpec(texteDecode, 0, texteDecode.length, app_compta.FichierConfig.get("ALGOSYM"));
                    System.out.println("Handshake: Decryptage de la cle secrete OK : "+secretKey.toString());
                    
                    System.out.println("Handshake: Decryptage de la cle secrete 2");
                    Cipher chiffrement2 = Cipher.getInstance(app_compta.FichierConfig.get("ALGOCIPHER"),app_compta.FichierConfig.get("PROVIDERCODE"));
                    chiffrement2.init(Cipher.DECRYPT_MODE, clePrivee);
                    byte[] texteDecode2 = chiffrement2.doFinal(rep.getCryptoObj2());
                    secretKey2 = new SecretKeySpec(texteDecode2, 0, texteDecode2.length, app_compta.FichierConfig.get("ALGOSYM"));
                    System.out.println("Handshake: Decryptage de la cle secrete 2 OK : "+secretKey2.toString());
                }
                catch (Exception ee)
                { System.out.println("Aie aie imprévu " + ee.getMessage() + ee.getClass()); }
            }
            else
            {
                System.err.println("Erreur lors de la requete de HANDSHAKE : "+req.getChargeUtile());
            }
        }
        catch (ClassNotFoundException e)
        { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
        catch (IOException e)
        { System.out.println("--- erreur IO = " + e.getMessage()); }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTime = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabelResponsable = new javax.swing.JLabel();
        jLabelTitre = new javax.swing.JLabel();
        jLabelicon1 = new javax.swing.JLabel();
        jButtonNextBill = new javax.swing.JButton();
        jButtonRecPay = new javax.swing.JButton();
        jButtonListeFactures = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jLogout = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuAPropos = new javax.swing.JMenuItem();
        jMenuItemLogs = new javax.swing.JMenuItem();
        jMenuAide = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(400, 275));

        jLabelTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel4.setText("Responsable :");

        jLabelTitre.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabelTitre.setForeground(new java.awt.Color(0, 0, 204));
        jLabelTitre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitre.setText("Application Compta");
        jLabelTitre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabelicon1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonNextBill.setText("Obtenir une facture non validée");
        jButtonNextBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextBillActionPerformed(evt);
            }
        });

        jButtonRecPay.setText("Recevoir un paiement");
        jButtonRecPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecPayActionPerformed(evt);
            }
        });

        jButtonListeFactures.setText("Consulter la liste des factures");
        jButtonListeFactures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListeFacturesActionPerformed(evt);
            }
        });

        jMenu1.setText("Utilisateurs");

        jLogout.setText("Logout");
        jLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLogoutActionPerformed(evt);
            }
        });
        jMenu1.add(jLogout);

        jMenuBar1.add(jMenu1);

        jMenu10.setText("A Propos");

        jMenuAPropos.setText("Infos");
        jMenuAPropos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAProposActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuAPropos);

        jMenuItemLogs.setText("Fichier Log");
        jMenuItemLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogsActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItemLogs);

        jMenuAide.setText("Aide");
        jMenuAide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAideActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuAide);

        jMenuBar1.add(jMenu10);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelTime, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelicon1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonNextBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRecPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonListeFactures, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelResponsable, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelTime, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTitre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelicon1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNextBill)
                        .addGap(42, 42, 42)
                        .addComponent(jButtonRecPay)
                        .addGap(42, 42, 42)
                        .addComponent(jButtonListeFactures)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public final void setIcon()
    {
        String logo1 = FichierConfig.getNomsFichs("compta");
        ImageIcon image1 = new ImageIcon(logo1);
        jLabelicon1.setText(null);        
        jLabelicon1.setIcon(image1);
    }
    
    public static void Affichagedate()
    {
        Thread t_date = new Thread()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    String datecur = getCurrentDate();
                    jLabelTime.setText(datecur);
                    try
                    {
                        sleep(1000);
                    }
                    catch(InterruptedException ex)
                    {
                        ex.getMessage();
                    }
                }
            }
        };
        t_date.start();
    }
    
    public static String getCurrentDate()
    {
        Date cur_date = new Date();
        String madate = new String();
        
        String str = FichierConfig.getdateConfig();
        
        StringTokenizer st = new StringTokenizer(str, "-");
        formDate = st.nextElement().toString();
        formTime = st.nextElement().toString();
        String form = formDate + " " + formTime;
        
        madate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.FRANCE).format(cur_date);

        
        return madate;
    }
    
    private void jLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLogoutActionPerformed

            FenLogin.logornot = false;
            jLabelResponsable.setText("");
            this.dispose();
            FenLogin fenlog = new FenLogin(this, rootPaneCheckingEnabled);
            FichierLog.UpdateFich("Déconnection de l'utilisateur" );
            fenlog.setVisible(true);  

    }//GEN-LAST:event_jLogoutActionPerformed

    private void jMenuAProposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAProposActionPerformed

            FenAbout about = new FenAbout(this, rootPaneCheckingEnabled);
            FichierLog.UpdateFich("Ouverture fenetre d'a propos " );
            about.setVisible(true);

    }//GEN-LAST:event_jMenuAProposActionPerformed

    private void jMenuAideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAideActionPerformed

            FenHelp help = new FenHelp(this, rootPaneCheckingEnabled);
            FichierLog.UpdateFich("Ouverture fenetre d'help " );
            help.setVisible(true);

    }//GEN-LAST:event_jMenuAideActionPerformed

    private void jMenuItemLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogsActionPerformed
        FenAfficherLog log = new FenAfficherLog(this,rootPaneCheckingEnabled);
        FichierLog.UpdateFich("Ouverture fenêtre des logs " );
        log.setVisible(true);
    }//GEN-LAST:event_jMenuItemLogsActionPerformed

    private void jButtonNextBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextBillActionPerformed
        // TODO add your handling code here:
        
        ObjectInputStream ois=null;
        ObjectOutputStream oos=null;
        
        String adresse = app_compta.FichierConfig.get("HOST");
        int port = Integer.parseInt(app_compta.FichierConfig.get("PORT_COMPTA"));
        RequeteBISAMAP req = null;

        try
        {
            cliSock = new Socket(adresse, port);
            System.out.println("infos socket : "+cliSock.getInetAddress().toString());
        }
        catch (UnknownHostException e)
        { 
            System.err.println("Erreur ! Host non trouvé [" + e + "]"); 
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur : " + e.getMessage());
        }
        catch (IOException e)
        { 
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur : " + e.getMessage());
        }

        String chargeUtile = ChargeRecue;

        req = null;
        req = new RequeteBISAMAP(RequeteBISAMAP.GET_NEXT_BILL, chargeUtile);
        ois=null; oos=null;
        try
        {
            System.out.println("Envoi de la requete GET_NEXT_BILL : "+req.getChargeUtile());
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);
            oos.flush();
            FichierLog.UpdateFich("Envoi d'une requete de type GET_NEXT_BILL : " +req.getChargeUtile());
        }
        catch (IOException e)
        { System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); }

        // Lecture de la réponse
        ReponseBISAMAP rep = null;
        try
        {
            ois = new ObjectInputStream(cliSock.getInputStream());
            rep = (ReponseBISAMAP)ois.readObject();
            System.out.println(" *** Reponse string reçue : " + rep.getChargeUtile());
//            System.out.println(" *** Reponse crypto reçue : " + rep.getCryptoObj().toString());

            if(rep.getCode() == ReponseBISAMAP.OK)
            {                     
                System.out.println(" Requete GET_NEXT_BILL : "+req.getChargeUtile()+" effectué avec succès !! " );
                FichierLog.UpdateFich(" Requete GET_NEXT_BILL : "+req.getChargeUtile()+" effectué avec succès !! " );

                // Dechiffrement
//                try
//                {
//                    
//                    System.out.println("GET_NEXT_BILL: Decryptage de la reponse");
//                    Cipher chiffrement = Cipher.getInstance(app_compta.FichierConfig.get("ALGOCIPHER"),app_compta.FichierConfig.get("PROVIDERCODE"));
//                    chiffrement.init(Cipher.DECRYPT_MODE, secretKey);
//                    byte[] texteDecode = chiffrement.doFinal(rep.getCryptoObj());
//                    String texteDecodeClair = new String (texteDecode);
//                    System.out.println("GET_NEXT_BILL: Decryptage de la reponse OK : "+texteDecodeClair);
//                }
//                catch (Exception ee)
//                { System.out.println("Aie aie imprévu " + ee.getMessage() + ee.getClass()); }
//                
//                FenFacture maFacture = new FenFacture(cliSock, texteDecodeClair);
                FenFacture maFacture = new FenFacture(cliSock, rep.getChargeUtile());
                FichierLog.UpdateFich("Ouverture fenêtre FenFacture (GET_NEXT_BILL)" );
                maFacture.setVisible(true);
            }
            else
            {
                System.err.println("Erreur lors de la requete de GET_NEXT_BILL : "+req.getChargeUtile());
            }
        }
        catch (ClassNotFoundException e)
        { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
        catch (IOException e)
        { System.out.println("--- erreur IO = " + e.getMessage()); }
        
    }//GEN-LAST:event_jButtonNextBillActionPerformed

    private void jButtonRecPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecPayActionPerformed
        // TODO add your handling code here:
        FenPaiement pay = new FenPaiement(ChargeRecue, secretKey, secretKey2);
        FichierLog.UpdateFich("Ouverture fenêtre FenPaiement (REC_PAY)" );
        pay.setVisible(true);
    }//GEN-LAST:event_jButtonRecPayActionPerformed

    private void jButtonListeFacturesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListeFacturesActionPerformed
        // TODO add your handling code here:
        FenListeFactures op = new FenListeFactures(cliSock);
        FichierLog.UpdateFich("Ouverture fenêtre FenListeOperations" );
        op.setVisible(true);
    }//GEN-LAST:event_jButtonListeFacturesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FenAppCompta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenAppCompta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenAppCompta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenAppCompta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FenAppCompta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonListeFactures;
    private javax.swing.JButton jButtonNextBill;
    private javax.swing.JButton jButtonRecPay;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JLabel jLabelResponsable;
    public static javax.swing.JLabel jLabelTime;
    private javax.swing.JLabel jLabelTitre;
    private javax.swing.JLabel jLabelicon1;
    private javax.swing.JMenuItem jLogout;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenuItem jMenuAPropos;
    private javax.swing.JMenuItem jMenuAide;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemLogs;
    // End of variables declaration//GEN-END:variables
}
