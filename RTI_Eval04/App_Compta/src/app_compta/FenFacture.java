/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_compta;

import database_acces.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import reqrep_bisamap.ReponseBISAMAP;
import reqrep_bisamap.RequeteBISAMAP;


/**
 *
 * @author isen0
 */
public class FenFacture extends javax.swing.JFrame {

    /**
     * Creates new form FenCamion_2
     */
    
    private Socket cliSock=null;
    private ResultSet rs;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String chargeRecue;
    private String user;
    
    public FenFacture() {
        initComponents();
        setTitle("GET_NEXT_BILL");
    }
    
    public FenFacture(Socket SocketMouv) {
        initComponents();
        setTitle("GET_NEXT_BILL");
        cliSock = SocketMouv;
    }
    public FenFacture(Socket SocketMouv, String s) {
        initComponents();
        setTitle("GET_NEXT_BILL");
        cliSock = SocketMouv;
        chargeRecue = s;
        
        StringTokenizer st = new StringTokenizer(chargeRecue, "#");
        user = st.nextToken();
        IdTextField.setText(st.nextToken());
        SocieteTextField.setText(st.nextToken());
        DateTextField.setText(st.nextToken());
        HTVATextField.setText(st.nextToken());
        TVACTextField.setText(st.nextToken());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonValider = new javax.swing.JButton();
        jButtonAnnuler = new javax.swing.JButton();
        IdTextField = new javax.swing.JTextField();
        SocieteTextField = new javax.swing.JTextField();
        DateTextField = new javax.swing.JTextField();
        HTVATextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TVACTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Informations facture");

        jLabel2.setText("Id :");

        jLabel3.setText("Société :");

        jLabel4.setText("Date :");

        jLabel5.setText("Montant HTVA :");

        jButtonValider.setText("Valider la facture");
        jButtonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderActionPerformed(evt);
            }
        });

        jButtonAnnuler.setText("Annuler");
        jButtonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerActionPerformed(evt);
            }
        });

        IdTextField.setEditable(false);

        SocieteTextField.setEditable(false);

        DateTextField.setEditable(false);

        HTVATextField.setEditable(false);

        jLabel6.setText("Montant TVAC :");

        TVACTextField.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Info : Facture non validée");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(IdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(DateTextField)
                                    .addComponent(SocieteTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TVACTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                    .addComponent(HTVATextField, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(75, 75, 75))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jButtonValider)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jButtonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(IdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(SocieteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(DateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HTVATextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TVACTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(28, 28, 28)
                .addComponent(jLabel7)
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnnuler)
                    .addComponent(jButtonValider))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        // TODO add your handling code here:
         // Construction de la requête
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
        
        String facture = IdTextField.getText();
        String charge = user+"#"+facture;
        byte[] signature = null;
//        try
//        {
//            System.out.println("VALIDATE_BILL: Acces au KeyStore");
//            KeyStore clientKS = KeyStore.getInstance(app_compta.FichierConfig.get("KSALGO"), app_compta.FichierConfig.get("PROVIDERCODE"));
//            System.out.println("VALIDATE_BILL: TEST affichage avant le LOAD");
//            clientKS.load(new FileInputStream(FenAppCompta.cryptoUtilPath+System.getProperty("file.separator")+ app_compta.FichierConfig.get("KSCLIENTFILE")),app_compta.FichierConfig.get("KSPWD").toCharArray());
//            
//            System.out.println("VALIDATE_BILL: Recuperation de la cle privee");
//            PrivateKey clePrivee;
//            clePrivee = (PrivateKey) clientKS.getKey("IsenClient", app_compta.FichierConfig.get("KSENTRYPWD").toCharArray());
//            System.out.println("VALIDATE_BILL: Cle privee recuperee = " + clePrivee.toString());
//            
//            System.out.println("VALIDATE_BILL: Instanciation de la signature");
//            Signature s = Signature.getInstance(app_compta.FichierConfig.get("ALGOSIGNATURE"),app_compta.FichierConfig.get("PROVIDERCODE"));
//            System.out.println("VALIDATE_BILL: Initialisation de la signature");
//            s.initSign(clePrivee);
//            System.out.println("VALIDATE_BILL: Hachage du message");
//            s.update(charge.getBytes());
//            System.out.println("VALIDATE_BILL: Generation des bytes");
//            signature = s.sign();
//            System.out.println("VALIDATE_BILL: Termine : signature construite");
//            System.out.println("VALIDATE_BILL: Signature = " + new String(signature));
//        }
//        catch (Exception e)
//        {
//            System.err.println("Aie aie imprévu " + e.getMessage());
//        } 

        req = null;
        req = new RequeteBISAMAP(RequeteBISAMAP.VALIDATE_BILL, charge, signature);
        ois=null; oos=null;
        try
        {
            System.out.println("Envoi de la requete VALIDATE_BILL : "+req.getChargeUtile());
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);
            oos.flush();
            FichierLog.UpdateFich("Envoi d'une requete de type VALIDATE_BILL : " +req.getChargeUtile());
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
                System.out.println(" Requete VALIDATE_BILL : "+req.getChargeUtile()+" effectué avec succès !! " );
                FichierLog.UpdateFich(" Requete VALIDATE_BILL : "+req.getChargeUtile()+" effectué avec succès !! " );

                JOptionPane.showMessageDialog(this,"La facture "+facture+" à été validée avec succès !");
                this.dispose();
            }
            else
            {
                System.err.println("Erreur lors de la requete de VALIDATE_BILL : "+req.getChargeUtile());
            }
        }
        catch (ClassNotFoundException e)
        { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
        catch (IOException e)
        { System.out.println("--- erreur IO = " + e.getMessage()); }
        
    }//GEN-LAST:event_jButtonValiderActionPerformed

    
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
                }}}
         catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FenFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenFacture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FenFacture().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DateTextField;
    private javax.swing.JTextField HTVATextField;
    public static javax.swing.JTextField IdTextField;
    public static javax.swing.JTextField SocieteTextField;
    private javax.swing.JTextField TVACTextField;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
