/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import laundry.frames.LoginForm;
import tools.dialogControl;
import tools.queryControl;
import tools.tools;

/**
 *
 * @author Rynare
 */
public class Pertanyaan extends java.awt.Dialog {

    /**
     * Creates new form pertanyaana
     */
    ResultSet rs;

    laundry_management_connection koneksi = new laundry_management_connection();
    LoginForm parent;

    public String createAccQuery;
    public Object[] createAccValue;
    public String Username;

    public void setParent(LoginForm parent) {
        this.parent = parent;
    }
    
    public Pertanyaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Pertanyaan_field = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Jawaban_field = new javax.swing.JTextField();
        OK_btn = new com.k33ptoo.components.KButton();
        jButton1 = new javax.swing.JButton();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Langkah Terakhir Penyiapan AKUN");

        jLabel2.setText("Pertanyaan");

        Pertanyaan_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Pertanyaan_fieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Jawaban");

        OK_btn.setBackground(new java.awt.Color(255, 255, 255));
        OK_btn.setBorder(null);
        OK_btn.setText("OK!");
        OK_btn.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        OK_btn.setkEndColor(new java.awt.Color(0, 163, 163));
        OK_btn.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        OK_btn.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        OK_btn.setkStartColor(new java.awt.Color(0, 163, 163));
        OK_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OK_btnMouseClicked(evt);
            }
        });
        OK_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OK_btnActionPerformed(evt);
            }
        });

        jButton1.setText("Random");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Jawaban_field)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Pertanyaan_field, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Pertanyaan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jawaban_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(OK_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void Pertanyaan_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Pertanyaan_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Pertanyaan_fieldActionPerformed

    private void OK_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OK_btnMouseClicked
        // TODO add your handling code here:
        try {
            if (!(Jawaban_field.getText().isEmpty() || Pertanyaan_field.getText().isEmpty())) {
                boolean isInsertAcc = queryControl.insertData(koneksi, createAccQuery, createAccValue);
                int id_laundry = 0;
                ResultSet rsGetId = koneksi.getDataWithPreparedStatement("SELECT id_laundry FROM laundry WHERE username = ?", Username);
                if (isInsertAcc && rsGetId.next()) {
                    id_laundry = rsGetId.getInt("id_laundry");
                    String query = "INSERT INTO lupa_sandi (id_laundry, pertanyaan, jawaban) VALUES (?, ?, ?)";
                    Object[] value = {id_laundry, Pertanyaan_field.getText(), Jawaban_field.getText()};
                    boolean isInsert = koneksi.executeUpdatePreparedStatement(query, value);
                    if (isInsert) {
                        parent.setUsernameSignin(Username);
                        parent.openSignIn();
                        this.setVisible(false);
                        dialogControl.showSuccessMessage(this, "Akun anda selesai dibuat");
                    }
                }
            } else {
                dialogControl.showWarningMessage(this, "Harap isi semua data yang diperlukan!!");
            }
        } catch (SQLException e) {
            dialogControl.SQLFailedInsert(this, e);
        } finally {
            koneksi.closeConnection();
        }
    }//GEN-LAST:event_OK_btnMouseClicked

    private void OK_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OK_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OK_btnActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        String currentQuestion = Pertanyaan_field.getText();
        String randomQuestion = tools.getRandomQuestion();

        while (randomQuestion.equals(currentQuestion)) {
            randomQuestion = tools.getRandomQuestion();
        }

        Pertanyaan_field.setText(randomQuestion);
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Pertanyaan dialog = new Pertanyaan(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Jawaban_field;
    private com.k33ptoo.components.KButton OK_btn;
    private javax.swing.JTextField Pertanyaan_field;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
