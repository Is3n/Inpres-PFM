/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_compta;

/**
 *
 * @author isen0
 */
public class FenHelp extends javax.swing.JDialog {

    private static final int coord_x = 600;
    private static final int coord_y = 300;
    /**
     * Creates new form DialogAbout
     * @param parent
     * @param modal
     */
    public FenHelp(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("Aide");
        setLocation(coord_x, coord_y);
        initComponents();
        jTextAreaAide.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonOk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAide = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonOk.setText("Ok");
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jTextAreaAide.setColumns(20);
        jTextAreaAide.setRows(5);
        jTextAreaAide.setText("Bienvenue sur l'application Compta.\nVous avez accès à différentes commandes qui sont les suivantes :\n\nprotocole BISAMAP - PORT_MOUVEMENTS\napplication cliente : Application_Compta\ncommande sémantique réponse éventuelle\nLOGIN un comptable veut se connecter\nparamètres : nom et digest \"salé\" du mot de\npasse\noui + lancement de la\nprocédure de handshake\npour le partage des\ndeux clés symétriques\nou\nnon\nGET_NEXT_\nBILL\npour obtenir la facture la plus ancienne non\nencore validée\nparamètres : -\noui + la facture chiffrée\nsymétriquement +\nattente de la commande\nsuivante\nou\nnon (pas de facture)\nVALIDATE_\nBILL\nvalider cette facture si elle est correctement\nlibellée ou invalider\nparamètres : numéro de facture, signature du\ncomptable\noui (signature vérifiée)\n+ confirmation\nou non (signature non\nvérifiée)\nLIST_BILLS pour obtenir la liste de toutes les factures\n(payées ou non) d'une société donnée pour un\nintervalle de temps donné\nparamètres : identifiant société, dates de\nl'intervalle, signature du comptable\noui (signature vérifiée)\n+ liste chiffrée\nsymétriquement\nou\nnon\nSEND_BILLS ordre d'envoyer les factures validées par le\ncomptable sauf peut-être certaines qu'il précise\nparamètres : liste des factures à ne pas\nenvoyer (éventuellement, liste vide),\nsignature du comptable\noui (signature vérifiée)\nou\nnon (certaines factures\n\"à éviter\" n'existent\npas)\nREC_PAY RECeiving PAYment: enregistrement du\npaiement d'une facture\nparamètres : numéro de la facture, montant,\ninformations bancaires + chiffré\nsymétriquement + HMAC du comptable\noui (HMAC vérifié) +\nconfirmation\nou\nnon + pourquoi (ex:\nmontant payé différent\ndu montant attendu)\nLIST_WAITING List of WAITING payements : liste des\nfactures non encore payées : toutes ou celles\nd'une société donnée ou celles qui ont été\némises depuis plus d'un mois\nparamètres : indications sur la nature de la\nliste, signature du comptable\nla liste\nou\nnon + pourquoi");
        jScrollPane1.setViewportView(jTextAreaAide);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addComponent(jButtonOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButtonOk)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        FichierLog.UpdateFich("Fermeture de la fenetre help " );
        this.dispose();
    }//GEN-LAST:event_jButtonOkActionPerformed

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
            java.util.logging.Logger.getLogger(FenHelp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenHelp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenHelp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenHelp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                FenHelp dialog = new FenHelp(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonOk;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaAide;
    // End of variables declaration//GEN-END:variables
}
