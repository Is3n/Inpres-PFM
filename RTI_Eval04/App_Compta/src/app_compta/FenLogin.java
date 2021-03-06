/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package app_compta;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JOptionPane;
import reqrep_bisamap.*;


/**
 *
 * @author Isen0
 */
public class FenLogin extends javax.swing.JDialog {
    
    public static boolean logornot = false;
    private java.awt.Frame parent;
    private static final int coord_x = 900;
    private static final int coord_y = 300;
    private static boolean inscr;
    
    private boolean isConnected = false;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket cliSock=null;
    /**
     * Creates new form FenLogin
     * @param parent
     * @param modal
     */
    public FenLogin(java.awt.Frame parent, boolean modal ) 
    {
        super(parent, modal);
        this.parent = parent;
        initComponents();
        setLocation(coord_x, coord_y);       
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jUserName = new javax.swing.JTextField();
        jPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jValider = new javax.swing.JButton();
        jAnnuler = new javax.swing.JButton();

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nom d'utilisateur :");

        jLabel2.setText("Mot de passe :");

        jUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUserNameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Connexion");

        jValider.setText("Valider");
        jValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jValiderActionPerformed(evt);
            }
        });

        jAnnuler.setText("Annuler");
        jAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnnulerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jValider)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)))
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jAnnuler))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jValider)
                    .addComponent(jAnnuler))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnnulerActionPerformed
       FichierLog.UpdateFich("Annulation de login " );
        this.dispose();
    }//GEN-LAST:event_jAnnulerActionPerformed

    private void jValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jValiderActionPerformed
        
       // Construction de la requête
        String user = jUserName.getText();
        String pass = jPassword.getText();
        RequeteBISAMAP req = null;
        
        // Connexion au serveur
        ois=null; oos=null; //cliSock = null;
        String adresse = app_compta.FichierConfig.get("HOST");
        int port = Integer.parseInt(app_compta.FichierConfig.get("PORT_COMPTA"));
        
        
        try
        {
            cliSock = new Socket(adresse, port);
            System.out.println("infos socket AppCompta: "+cliSock.getInetAddress().toString());
        }
        catch (UnknownHostException e)
        { 
            System.err.println("Erreur ! Host non trouvé [" + e + "]"); 
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur AppCompta: " + e.getMessage());
        }
        catch (IOException e)
        { 
            System.err.println("Erreur ! Pas de connexion ? [" + e + "]");
            JOptionPane.showMessageDialog(this,"Erreur de connexion au serveur AppCompta: " + e.getMessage());
        }
        
        // Construction du Digest
                            System.out.println("Construction du Digest client dans AppCompta");
                            try
                            {
                                MessageDigest md = MessageDigest.getInstance(app_compta.FichierConfig.get("ALGODIGEST"), app_compta.FichierConfig.get("PROVIDERCODE"));
                                
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
                                String chargeUtile = user+"#"+temps+"#"+alea+"#"+msgDigest.length;
                                //System.out.println("TEST CHARGE UTILE COMPTA : "+chargeUtileCompta);
                            
                                req = new RequeteBISAMAP(RequeteBISAMAP.LOGIN, chargeUtile, msgDigest);

                                // Envoie de la requête
                                try
                                {
                                    System.out.println("Envoi de la requete LOGIN : "+req.getChargeUtile());
                                    oos = new ObjectOutputStream(cliSock.getOutputStream());
                                    oos.writeObject(req);
                                    oos.flush();
                                    FichierLog.UpdateFich("Envoi d'une requete de type LOGIN : " +req.getChargeUtile());
                                }
                                catch (IOException e)
                                { System.err.println("Erreur réseau ? [" + e.getMessage() + "]"); }
                            }
                            catch(NoSuchAlgorithmException | NoSuchProviderException e)
                            {
                                System.err.println("Erreur dans le Digest TRAMAP");
                            }
            
            
            // Lecture de la réponse
            ReponseBISAMAP rep = null;
            try
            {
                ois = new ObjectInputStream(cliSock.getInputStream());
                rep = (ReponseBISAMAP)ois.readObject();
                System.out.println(" *** Reponse reçue : " + rep.getChargeUtile());
                FichierLog.UpdateFich(" Reponse reçue : " + rep.getChargeUtile());

                if(rep.getCode() == ReponseBISAMAP.OK)
                {
                    FenAppCompta fen = new FenAppCompta(cliSock, rep.getChargeUtile());
                    fen.setVisible(true);
                    this.dispose();
                }
                else if(rep.getCode() == ReponseBISAMAP.WRONG_PASSWORD)
                {
                    JOptionPane.showMessageDialog(this,"Erreur de mot de passe avec les identifiants : "+req.getChargeUtile()+"Veuillez réessayer ou vous inscrire");
                }
                else
                {
                    JOptionPane.showMessageDialog(this,"Erreur de connexion avec les identifiants : "+req.getChargeUtile()+"Veuillez réessayer ou vous inscrire");
                }
            }
            catch (ClassNotFoundException e)
            { System.out.println("--- erreur sur la classe = " + e.getMessage()); }
            catch (IOException e)
            { System.out.println("--- erreur IO = " + e.getMessage()); }
        
        
    }//GEN-LAST:event_jValiderActionPerformed

    private void jUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUserNameActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jUserNameActionPerformed

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
            java.util.logging.Logger.getLogger(FenLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FenLogin dialog = new FenLogin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jAnnuler;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPassword;
    private javax.swing.JTextField jUserName;
    private javax.swing.JButton jValider;
    // End of variables declaration//GEN-END:variables
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */                                                            
          
}
