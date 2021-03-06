/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_mouvements;

import database_acces.ConnexionDB;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author isen0
 */
public class FenTransporteurRes extends javax.swing.JFrame {

    /**
     * Creates new form FenTransporteurRes
     */
    private Socket cliSock=null;
    private ResultSet rs;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public String infosTransporteur;
    
    public FenTransporteurRes() {
        initComponents();
        setTitle("Infos transporteur");
        AddSocietes();
        AddTypes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        ImmaTextField = new javax.swing.JTextField();
        CapaciteTextField = new javax.swing.JTextField();
        jComboBoxSocietes = new javax.swing.JComboBox<>();
        jComboBoxTypes = new javax.swing.JComboBox<>();
        CaracTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonValider = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButtonAnnuler = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel6.setText("Caractéristique :");

        jLabel1.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Infos du transporteur avec réservation");

        jButtonValider.setText("Valider");
        jButtonValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonValiderActionPerformed(evt);
            }
        });

        jLabel2.setText("Immatriculation :");

        jButtonAnnuler.setText("Annuler");
        jButtonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerActionPerformed(evt);
            }
        });

        jLabel3.setText("Société :");

        jLabel4.setText("Type :");

        jLabel5.setText("Capacité :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CapaciteTextField)
                                    .addComponent(ImmaTextField)
                                    .addComponent(jComboBoxSocietes, 0, 179, Short.MAX_VALUE)
                                    .addComponent(jComboBoxTypes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CaracTextField)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(jButtonValider)
                                .addGap(63, 63, 63)
                                .addComponent(jButtonAnnuler)))
                        .addGap(0, 49, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ImmaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxSocietes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(CapaciteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(CaracTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonValider)
                    .addComponent(jButtonAnnuler))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonValiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonValiderActionPerformed
        // TODO add your handling code here:
        infosTransporteur = ImmaTextField.getText()+"#"+jComboBoxSocietes.getSelectedItem().toString()+"#"
        +jComboBoxTypes.getSelectedItem().toString()+"#"+CapaciteTextField.getText()+"#"+CaracTextField.getText();

        FenCamionRes.infosCamion = infosTransporteur;
        FenCamionRes.CamionTextField.setText(infosTransporteur);
        this.dispose();
    }//GEN-LAST:event_jButtonValiderActionPerformed

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        // TODO add your handling code here:

        this.dispose();
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

    private void AddSocietes()
    {
        try
        {
            ConnexionDB db = new ConnexionDB(database_acces.FichierConfig.getNomsFichs("configMouv"),"bd_mouvements");
            
            db.openConnection();
            FichierLog.UpdateFich("Connexion a la base de donnees dans FenListeOperations" );
            
            rs = null;
            
            if(db!=null)
            {
            
                rs = db.select("SELECT id FROM societes");

                ResultSetMetaData metaData = rs.getMetaData();

                while(rs.next())
                {
                    Vector<String> data = new Vector<>();
                    for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                    {
                        //System.out.println("rs : " + rs.getString(cpt));
                        data.add(rs.getString(cpt));
                    }
                    //System.out.println("data : " + data);
                    jComboBoxSocietes.addItem(data.firstElement());
                }

                db.closeConnection();
                db = null;
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(this,"Erreur lors de la connexion à la DB : " + e.getMessage());
        }
    }
    
    private void AddTypes()
    {
        try
        {
            ConnexionDB db = new ConnexionDB(database_acces.FichierConfig.getNomsFichs("configMouv"),"bd_mouvements");
            
            db.openConnection();
            FichierLog.UpdateFich("Connexion a la base de donnees dans FenListeOperations" );
            
            rs = null;
            
            if(db!=null)
            {
            
                rs = db.select("SELECT type FROM type_transporteur");

                ResultSetMetaData metaData = rs.getMetaData();

                while(rs.next())
                {
                    Vector<String> data = new Vector<>();
                    for(int cpt = 1; cpt <= metaData.getColumnCount(); cpt++)
                    {
                        //System.out.println("rs : " + rs.getString(cpt));
                        data.add(rs.getString(cpt));
                    }
                    //System.out.println("data : " + data);
                    jComboBoxTypes.addItem(data.firstElement());
                }

                db.closeConnection();
                db = null;
            }
            else
            {
                System.err.println("Veuillez vous connecter à la base de donnee");
            }
        }
        catch (ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(this,"Erreur lors de la connexion à la DB : " + e.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(FenTransporteurRes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenTransporteurRes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenTransporteurRes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenTransporteurRes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FenTransporteurRes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CapaciteTextField;
    private javax.swing.JTextField CaracTextField;
    private javax.swing.JTextField ImmaTextField;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonValider;
    private javax.swing.JComboBox<String> jComboBoxSocietes;
    private javax.swing.JComboBox<String> jComboBoxTypes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
