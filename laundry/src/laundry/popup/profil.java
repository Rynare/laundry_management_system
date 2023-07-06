/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import tools.dialogControl;
import tools.queryControl;

/**
 *
 * @author Rynare
 */
public class profil extends javax.swing.JFrame {

    /**
     * Creates new form profil
     */
    laundry_management_connection koneksi = new laundry_management_connection();
    ResultSet rs;
    int id_laundry = -1;

    public void setData(int id_laundry){
        this.id_laundry = id_laundry;
    }
    
    public profil() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edit_info_btn = new com.k33ptoo.components.KButton();
        nama_laundry_field = new javax.swing.JTextField();
        nomer_hp_field = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        alamat_field = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        kButton3 = new com.k33ptoo.components.KButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        peraturan_field = new javax.swing.JTextArea();
        edit_peraturan_btn = new com.k33ptoo.components.KButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(30, 39, 66));
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("INFO");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Laundry");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Alamat");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nomer HP");

        edit_info_btn.setText("Edit");
        edit_info_btn.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        edit_info_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        edit_info_btn.setkAllowGradient(false);
        edit_info_btn.setkBackGroundColor(new java.awt.Color(0, 153, 153));
        edit_info_btn.setkHoverColor(new java.awt.Color(0, 127, 127));
        edit_info_btn.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        edit_info_btn.setkPressedColor(new java.awt.Color(0, 153, 153));
        edit_info_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_info_btnMouseClicked(evt);
            }
        });
        edit_info_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edit_info_btnActionPerformed(evt);
            }
        });

        nama_laundry_field.setEditable(false);

        nomer_hp_field.setEditable(false);

        alamat_field.setEditable(false);
        alamat_field.setColumns(20);
        alamat_field.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        alamat_field.setLineWrap(true);
        alamat_field.setRows(5);
        jScrollPane1.setViewportView(alamat_field);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama_laundry_field)
                    .addComponent(nomer_hp_field)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(edit_info_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nama_laundry_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomer_hp_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edit_info_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel2.setBackground(new java.awt.Color(30, 39, 66));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        kButton3.setText("Default");
        kButton3.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton3MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Peraturan Laundry");

        peraturan_field.setEditable(false);
        peraturan_field.setColumns(20);
        peraturan_field.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        peraturan_field.setLineWrap(true);
        peraturan_field.setRows(5);
        jScrollPane2.setViewportView(peraturan_field);

        edit_peraturan_btn.setText("Edit");
        edit_peraturan_btn.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        edit_peraturan_btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        edit_peraturan_btn.setkAllowGradient(false);
        edit_peraturan_btn.setkBackGroundColor(new java.awt.Color(0, 153, 153));
        edit_peraturan_btn.setkHoverColor(new java.awt.Color(0, 127, 127));
        edit_peraturan_btn.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        edit_peraturan_btn.setkPressedColor(new java.awt.Color(0, 153, 153));
        edit_peraturan_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_peraturan_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edit_peraturan_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edit_peraturan_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void edit_info_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edit_info_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edit_info_btnActionPerformed

    private void edit_info_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_info_btnMouseClicked
        // TODO add your handling code here:
        if (edit_info_btn.getText().equals("Edit")) {
            nama_laundry_field.setEditable(true);
            nomer_hp_field.setEditable(true);
            alamat_field.setEditable(true);
            edit_info_btn.setText("Simpan");
        } else if (edit_info_btn.getText().equals("Simpan")) {
            String nama_laundry = nama_laundry_field.getText();
            String nomer_hp = nomer_hp_field.getText();
            String alamat = alamat_field.getText();
            int option = dialogControl.showQuestionOption(this, "Konfirmasi", "Ingin Menyimpan Perubahan?");

            if (option == JOptionPane.YES_OPTION) {
                String query = "UPDATE laundry SET laundry.nama_laundry = ?, laundry.no_telp = ?, laundry.alamat = ? WHERE id_laundry = ?";
                Object[] value = {nama_laundry, nomer_hp, alamat, id_laundry};
                queryControl.updateData(koneksi, query, value);
                nama_laundry_field.setEditable(false);
                nomer_hp_field.setEditable(false);
                alamat_field.setEditable(false);
                edit_info_btn.setText("Edit");
            }
        }

    }//GEN-LAST:event_edit_info_btnMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        try {
            String query = "Select laundry.nama_laundry, laundry.no_telp, laundry.alamat, laundry.peringatan from laundry WHERE id_laundry = "+id_laundry;
            rs = koneksi.getData(query);
            if (rs.next()) {
                nama_laundry_field.setText(rs.getString("nama_laundry"));
                nomer_hp_field.setText(rs.getString("no_telp"));
                alamat_field.setText(rs.getString("alamat"));
                peraturan_field.setText(rs.getString("peringatan"));
            }
        } catch (SQLException e) {
            dialogControl.SQLFailedShow(this, e);
        } finally {
            koneksi.closeConnection();
        }
    }//GEN-LAST:event_formComponentShown

    private void kButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton3MouseClicked
        // TODO add your handling code here:
        String peringatan_default = "1. Pengambilan ORDER WAJIB DISERTAI NOTA TRANSAKSI dan harus diambil sendiri tanpa diwakilkan.\n"
                + "2. Harap diperiksa dan dihitung kembali jumlah serta kondisi order bersama PETUGAS KAMI karena KOMPLAIN dan GARANSI dalam bentuk apapun setelah tanda tangan penerimaan order akan KAMI TOLAK.\n"
                + "3. Kondisi order jika tidak diambil MAKSIMAL 2x24 JAM dari tanggal selesai diluar TANGGUNG JAWAB KAMI.\n"
                + "4. Kerusakan pada order yang mudah luntur dan menyusut diluar TANGGUNG JAWAB KAMI, jika tidak ada pemberitahuan di awal.";
        int option = dialogControl.showWarningOption(this, "Konfirmasi", "Apakah anda ingin mengubah Peraturan laundry menjadi default?");
        if (option == JOptionPane.YES_OPTION) {
            String query = "UPDATE laundry SET laundry.peringatan = ? WHERE id_laundry = ?";
            boolean isUpdate = queryControl.updateData(koneksi, query, peringatan_default, id_laundry);
            if (isUpdate) {
                peraturan_field.setText(peringatan_default);
            }
        }
    }//GEN-LAST:event_kButton3MouseClicked

    private void edit_peraturan_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_peraturan_btnMouseClicked
        // TODO add your handling code here:
        if (edit_peraturan_btn.getText().equals("Edit")) {
            peraturan_field.setEditable(true);
            edit_peraturan_btn.setText("Simpan");
        } else if (edit_peraturan_btn.getText().equals("Simpan")) {
            String peraturan = peraturan_field.getText();
            int option = dialogControl.showQuestionOption(this, "Konfirmasi", "Ingin Menyimpan Perubahan?");

            if (option == JOptionPane.YES_OPTION) {
                String query = "UPDATE laundry SET laundry.peringatan = ? WHERE id_laundry = ?";
                queryControl.updateData(koneksi, query, peraturan, id_laundry);
                peraturan_field.setEditable(false);
                edit_peraturan_btn.setText("Edit");
            }
        }
    }//GEN-LAST:event_edit_peraturan_btnMouseClicked

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
            java.util.logging.Logger.getLogger(profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(profil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new profil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea alamat_field;
    private com.k33ptoo.components.KButton edit_info_btn;
    private com.k33ptoo.components.KButton edit_peraturan_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.k33ptoo.components.KButton kButton3;
    private javax.swing.JTextField nama_laundry_field;
    private javax.swing.JTextField nomer_hp_field;
    private javax.swing.JTextArea peraturan_field;
    // End of variables declaration//GEN-END:variables
}
