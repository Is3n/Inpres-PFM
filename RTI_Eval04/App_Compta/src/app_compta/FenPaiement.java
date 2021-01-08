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
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import reqrep_bisamap.ReponseBISAMAP;
import reqrep_bisamap.RequeteBISAMAP;

/**
 *
 * @author isen0
 */
public class FenPaiement extends javax.swing.JFrame {

    private Socket cliSock=null;
    private ResultSet rs;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String chargeRecue;
    private String user;
    private SecretKey secretKey;
    private SecretKey secretKey2;
    
    /**
     * Creates new form FenPaiement
     */
    public FenPaiement() {
        initComponents();
    }
    public FenPaiement(String s) {
        initComponents();
        setTitle("GET_NEXT_BILL");        
        user = s;
    }
    public FenPaiement(String s, SecretKey key, SecretKey key2) {
        initComponents();
        setTitle("GET_NEXT_BILL");        
        user = s;
        secretKey=key;
        secretKey2=key2;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonValider = new javax.swing.JButton();
        jButtonAnnuler = new javax.swing.JButton();
        IdTextField = new javax.swing.JTextField();
        MontantTextField = new javax.swing.JTextField();
        BankTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonValider.setText("Valider");
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

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Réception d'un paiement");

        jLabel2.setText("Id de la facture :");

        jLabel3.setText("Montant du paiement :");

        jLabel4.setText("Infos bancaires :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MontantTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(BankTextField)
                                    .addComponent(IdTextField))
                                .addGap(52, 52, 52))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jButtonValider, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 87, Short.MAX_VALUE)))
                        .addGap(3, 3, 3)))
                .addContainerGap())
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
                    .addComponent(MontantTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(BankTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonValider)
                    .addComponent(jButtonAnnuler))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        // TODO add your handling code here:
        // Construction de la requête
        
        String facture = IdTextField.getText();
        String montant = MontantTextField.getText();
        String infosBancaires = BankTextField.getText();
        
        String charge = user+"#"+facture+"#"+montant;
        
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
        
        System.out.println("Chiffrement des infos bancaires REC_PAY");
        byte[] texteCrypte=null;
//        try
//        {
//            System.out.println("REC_PAY: Cryptage de la reponse");
//            Cipher chiffrement = Cipher.getInstance(FichierConfig.get("ALGOCIPHER"),FichierConfig.get("PROVIDERCODE"));
//            chiffrement.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] texteClair = infosBancaires.getBytes();
//            texteCrypte = chiffrement.doFinal(texteClair);
//            String texteCrypteAff = new String (texteCrypte);
//            System.out.println(new String(texteClair) + " ---> " + texteCrypteAff);
//        }
//        catch (Exception e)
//        {
//            System.out.println("Aie imprévu cryptage REC_PAY: " + e.getMessage() + e.getClass());
//        }
//
        byte[] hb=null;
//        try
//        {
//                System.out.println("REC_PAY: Instanciation du HMAC");
//                Mac hmac = Mac.getInstance(app_compta.FichierConfig.get("ALGOHMAC"),app_compta.FichierConfig.get("PROVIDERCODE"));
//                hmac.init(secretKey2);
//                System.out.println("REC_PAY: Hachage du message");
//                hmac.update(charge.getBytes());
//                System.out.println("REC_PAY: Generation des bytes");
//                hb = hmac.doFinal();
//                System.out.println("REC_PAY: Termine : HMAC construit");
//                System.out.println("REC_PAY: HMAC = " + new String(hb));
//                System.out.println("REC_PAY: Longueur du HMAC = " + hb.length);
//                charge = user+"#"+facture+"#"+montant+"#"+hb.length;
//        }
//        catch (Exception e)
//        {
//                System.err.println("Aie aie imprévu " + e.getMessage());
//        }

        req = null;
        req = new RequeteBISAMAP(RequeteBISAMAP.REC_PAY, charge, texteCrypte, hb);
        ois=null; oos=null;
        try
        {
            System.out.println("Envoi de la requete REC_PAY : "+req.getChargeUtile());
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            oos.writeObject(req);
            oos.flush();
            FichierLog.UpdateFich("Envoi d'une requete de type REC_PAY : " +req.getChargeUtile());
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
                System.out.println(" Requete REC_PAY : "+req.getChargeUtile()+" effectué avec succès !! " );
                FichierLog.UpdateFich(" Requete REC_PAY : "+req.getChargeUtile()+" effectué avec succès !! " );

                JOptionPane.showMessageDialog(this,"La facture "+facture+" à été payée avec succès !");
                this.dispose();
            }
            else if(rep.getCode() == ReponseBISAMAP.WRONG_DATA)
            {
                System.out.println(" Requete REC_PAY : Les montants ne correspondent pas !! " );
                FichierLog.UpdateFich(" Requete REC_PAY : Les montants ne correspondent pas !! " );

                JOptionPane.showMessageDialog(this,"Le montant payé ne correpond pas au montant de la facture !");
            }
            else
            {
                System.err.println("Erreur lors de la requete de REC_PAY : "+req.getChargeUtile());
                FichierLog.UpdateFich(" Requete REC_PAY : Les montants ne correspondent pas !! " );

            }
        }
        catch (ClassNotFoundException e)
        { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
        catch (IOException e)
        { System.out.println("--- erreur IO = " + e.getMessage()); }

    }//GEN-LAST:event_jButtonValiderActionPerformed

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

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
            java.util.logging.Logger.getLogger(FenPaiement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenPaiement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenPaiement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenPaiement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FenPaiement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BankTextField;
    public static javax.swing.JTextField IdTextField;
    public static javax.swing.JTextField MontantTextField;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
