/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import com.toedter.calendar.JTextFieldDateEditor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import tools.dialogControl;
import tools.printControl;
import tools.printControl.printMode;
import tools.queryControl;

/**
 *
 * @author Rynare
 */
public class TambahPenjualanTangggalSelesai extends java.awt.Dialog {

    /**
     * Creates new form TambahPenjualanTangggalSelesai1
     */
    laundry_management_connection koneksi = new laundry_management_connection();
    int id_penjualan = 1;
    boolean isUpdate = false;

    tambahPenjualan frameParent;
    printControl printControl = new printControl();

    public void setParent(tambahPenjualan frameParent) {
        this.frameParent = frameParent;
    }

    public void submit() {
        Calendar JCalendar_value = TanggalSelesaiChoser.getCalendar();
        String time = jamBox.getSelectedItem().toString();
        if (JCalendar_value == null) {
            dialogControl.showWarningMessage(this, "Masukkan Tanggal Terlebih Dahulu");
        } else {
            // Perbarui JCalendar_value dengan waktu saat ini
            Calendar currentCalendar = Calendar.getInstance();
            JCalendar_value.set(Calendar.HOUR_OF_DAY, currentCalendar.get(Calendar.HOUR_OF_DAY));
            JCalendar_value.set(Calendar.MINUTE, currentCalendar.get(Calendar.MINUTE));
            JCalendar_value.set(Calendar.SECOND, currentCalendar.get(Calendar.SECOND));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tanggal_selesai = dateFormat.format(JCalendar_value.getTime());

            if (time.equals("auto")) {
                SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");
                String jam_selesai = time_format.format(JCalendar_value.getTime());
                time = " " + jam_selesai;
            } else {
                time = " " + time + ":00:00";
            }

            String query = "UPDATE penjualan SET penjualan.tanggal_selesai = ? WHERE penjualan.id_penjualan = ?";
            Object[] value = {tanggal_selesai + time, id_penjualan};
            isUpdate = queryControl.updateData(koneksi, query, value);
            System.out.println("ID : " + id_penjualan);
            System.out.println(tanggal_selesai + time);

            if (isUpdate) {
                boolean isPrinted = false;
                int option = JOptionPane.NO_OPTION;
                try {
                    String[] keys = {"id_penjualan"};
                    Object[] values = {id_penjualan};

                    printControl.setLocation("C:\\this is college\\S2\\Workshop Pemrogaman Aplikasi\\laundry\\src\\report\\Nota.jrxml");
                    printControl.setParameter(keys, values);
                    printControl.setConnection(koneksi.getConnection());
                    if (auto_print_check.isSelected()) {
                        isPrinted = printControl.print(printMode.Without_Print_Dialog);
                    } else {
                        option = dialogControl.showQuestionOption(this, "Cetak Nota", "Ingin mencetak nota?");
                        if (option == JOptionPane.YES_OPTION) {
                            isPrinted = printControl.print(printMode.With_Print_Dialog);
                        } else if (option == JOptionPane.NO_OPTION) {
                            option = JOptionPane.NO_OPTION;
                        } else {
                            int exitOption = dialogControl.showWarningOption(this, "Keluar", "Ingin Menutup Jendela Print?");
                            if (exitOption == JOptionPane.YES_OPTION) {
                                option = JOptionPane.NO_OPTION;
                            } else {
                                option = JOptionPane.YES_OPTION;
                            }
                        }
                    }
                } catch (JRException e) {
                } finally {
                    System.out.println("printed : " + isPrinted);
                    if (auto_print_check.isSelected() && isPrinted) {
                        frameParent.setStatus(isUpdate);
                        frameParent.clearAllValue();
                        this.dispose();
                        frameParent.dispose();
                    } else {
                        if (!auto_print_check.isSelected() && option == JOptionPane.NO_OPTION) {
                            frameParent.setStatus(isUpdate);
                            frameParent.clearAllValue();
                            this.dispose();
                            frameParent.dispose();
                        }
                    }
                    frameParent.updateBeranda();
                }
            }
        }
    }
    
    public TambahPenjualanTangggalSelesai(java.awt.Frame parent, boolean modal) {
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
        jLabel23 = new javax.swing.JLabel();
        TanggalSelesaiChoser = new com.toedter.calendar.JDateChooser();
        kButton1 = new com.k33ptoo.components.KButton();
        jLabel24 = new javax.swing.JLabel();
        jamBox = new javax.swing.JComboBox<>();
        auto_print_check = new javax.swing.JCheckBox();

        setUndecorated(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Tanggal Selesai");

        TanggalSelesaiChoser.setDateFormatString("yyyy-MM-dd");
        TanggalSelesaiChoser.setMinSelectableDate(new java.util.Date(-62135791124000L));

        kButton1.setBorder(null);
        kButton1.setText("Konfirmasi");
        kButton1.setkEndColor(new java.awt.Color(0, 153, 153));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 153, 153));
        kButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton1MouseClicked(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Jam");

        jamBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "auto", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));
        jamBox.setToolTipText("");

        auto_print_check.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        auto_print_check.setForeground(new java.awt.Color(255, 255, 255));
        auto_print_check.setText("Auto Print");
        auto_print_check.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(auto_print_check))
                    .addComponent(jamBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(TanggalSelesaiChoser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TanggalSelesaiChoser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jamBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auto_print_check)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        if (isUpdate) {
            setVisible(false);
            dispose();
        } else {
            setVisible(true);
        }
    }//GEN-LAST:event_closeDialog

    private void kButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton1MouseClicked
        // TODO add your handling code here:
        submit();
    }//GEN-LAST:event_kButton1MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        TanggalSelesaiChoser.setMinSelectableDate(new Date());
        TanggalSelesaiChoser.setDate(new Date());
        jamBox.setSelectedIndex(0);

        // Mengatur editor teks agar tidak dapat diedit oleh pengguna
        JTextFieldDateEditor editor = (JTextFieldDateEditor) TanggalSelesaiChoser.getDateEditor();
        editor.setEditable(false);
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TambahPenjualanTangggalSelesai dialog = new TambahPenjualanTangggalSelesai(new java.awt.Frame(), true);
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
    private com.toedter.calendar.JDateChooser TanggalSelesaiChoser;
    private javax.swing.JCheckBox auto_print_check;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private static javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> jamBox;
    private com.k33ptoo.components.KButton kButton1;
    // End of variables declaration//GEN-END:variables
}
