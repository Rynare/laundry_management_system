/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import tools.tableControl;
import tools.tableControl.TableUpdateMode;

/**
 *
 * @author Rynare
 */
public class RiwayatDetailPenjualan extends java.awt.Dialog {

    /**
     * Creates new form RiwayatDetailPenjualan1
     */
    
    laundry_management_connection koneksi = new laundry_management_connection();
    tableControl tableControl = new tableControl();
    int id_laundry = -1;
    
    String date = "";
    String query = "SELECT * FROM DailySales WHERE DATE(tanggal_masuk) = ? AND id_laundry = ?";

    public void setData(String date, int id_laundry) {
        this.date = date;
        this.id_laundry = id_laundry;
    }

    public void loadTable() {
        String[] ignored = {"id_laundry"};
        tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored,query, date, id_laundry);
    }

    public void loadTableFilter() {
        String queryFilter = query;
        String valueFilter = dashboardSearchBarPenjualan.getText();
        String[] ignored = {"id_laundry"};
        switch (dashboardComboPenjualan.getSelectedItem().toString()) {
            
            case "ID_Penjualan":
                queryFilter += " AND id_penjualan = ?";
                tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored, queryFilter, date, valueFilter);
                break;
            case "Nama_Pelanggan":
                queryFilter += " AND nama_pelanggan LIKE ?";
                valueFilter = "%" + valueFilter + "%";
                tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored,queryFilter, date, valueFilter);
                break;
            case "Selesai":
                queryFilter += " AND status = 'selesai'";
                tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored, queryFilter, date);
                break;
            case "Belum Bayar":
                queryFilter += " AND status = 'belum bayar'";
                tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored,queryFilter, date);
                break;
            case "Belum Dikerjakan":
                queryFilter += " AND status = 'belum dikerjakan'";
                tableControl.loadTableFromDB(jTable1, TableUpdateMode.ALWAYS_UPDATE, ignored,queryFilter, date);
                break;
            default:
                break;
        }
    }
    
    public RiwayatDetailPenjualan(java.awt.Frame parent, boolean modal) {
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
        dashSeacrhGroup = new javax.swing.JPanel();
        dashboardSearchBarPenjualan = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        rSPanelImage3 = new rojerusan.RSPanelImage();
        dashboardComboPenjualan = new javax.swing.JComboBox<>();
        kButton2 = new com.k33ptoo.components.KButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        tanggal_riwayat = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

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

        jPanel1.setBackground(new java.awt.Color(30, 39, 66));

        dashSeacrhGroup.setBackground(new java.awt.Color(102, 102, 102));
        dashSeacrhGroup.setOpaque(false);

        dashboardSearchBarPenjualan.setBackground(new java.awt.Color(30, 39, 66));
        dashboardSearchBarPenjualan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        dashboardSearchBarPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        dashboardSearchBarPenjualan.setBorder(null);
        dashboardSearchBarPenjualan.setCaretColor(new java.awt.Color(255, 255, 255));
        dashboardSearchBarPenjualan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        dashboardSearchBarPenjualan.setOpaque(false);
        dashboardSearchBarPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardSearchBarPenjualanActionPerformed(evt);
            }
        });

        rSPanelImage3.setImagen(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24px/icons8_search_24px.png"))); // NOI18N
        rSPanelImage3.setPreferredSize(new java.awt.Dimension(24, 24));

        javax.swing.GroupLayout rSPanelImage3Layout = new javax.swing.GroupLayout(rSPanelImage3);
        rSPanelImage3.setLayout(rSPanelImage3Layout);
        rSPanelImage3Layout.setHorizontalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        rSPanelImage3Layout.setVerticalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        dashboardComboPenjualan.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        dashboardComboPenjualan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID_Penjualan", "Nama_Pelanggan", "Selesai", "Belum Bayar", "Belum Dikerjakan" }));
        dashboardComboPenjualan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dashboardComboPenjualanItemStateChanged(evt);
            }
        });

        kButton2.setBackground(new java.awt.Color(0, 0, 0));
        kButton2.setBorder(null);
        kButton2.setText("Cari");
        kButton2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kButton2.setIconTextGap(0);
        kButton2.setkAllowGradient(false);
        kButton2.setkBackGroundColor(new java.awt.Color(65, 105, 225));
        kButton2.setkHoverColor(new java.awt.Color(65, 105, 200));
        kButton2.setkHoverForeGround(new java.awt.Color(204, 204, 204));
        kButton2.setkPressedColor(new java.awt.Color(65, 105, 238));
        kButton2.setOpaque(false);
        kButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dashSeacrhGroupLayout = new javax.swing.GroupLayout(dashSeacrhGroup);
        dashSeacrhGroup.setLayout(dashSeacrhGroupLayout);
        dashSeacrhGroupLayout.setHorizontalGroup(
            dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashSeacrhGroupLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashSeacrhGroupLayout.createSequentialGroup()
                        .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dashboardSearchBarPenjualan))
                    .addComponent(jSeparator1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboardComboPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        dashSeacrhGroupLayout.setVerticalGroup(
            dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashSeacrhGroupLayout.createSequentialGroup()
                .addGroup(dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dashboardSearchBarPenjualan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rSPanelImage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(dashSeacrhGroupLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashboardComboPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setRowHeight(24);
        jScrollPane1.setViewportView(jTable1);

        tanggal_riwayat.setFont(new java.awt.Font("SansSerif", 3, 24)); // NOI18N
        tanggal_riwayat.setForeground(new java.awt.Color(255, 255, 255));
        tanggal_riwayat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tanggal_riwayat.setText("Kamis, 04 September 2003");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashSeacrhGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
                    .addComponent(tanggal_riwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tanggal_riwayat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(dashSeacrhGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void dashboardSearchBarPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardSearchBarPenjualanActionPerformed
        // TODO add your handling code here:
        loadTableFilter();
    }//GEN-LAST:event_dashboardSearchBarPenjualanActionPerformed

    private void dashboardComboPenjualanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dashboardComboPenjualanItemStateChanged
        // TODO add your handling code here:
        loadTableFilter();
    }//GEN-LAST:event_dashboardComboPenjualanItemStateChanged

    private void kButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton2MouseClicked
        // TODO add your handling code here:
        loadTableFilter();
    }//GEN-LAST:event_kButton2MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        System.out.println("date : "+date);
        String formattedDate = LocalDate.parse(this.date).format(formatter);

        tanggal_riwayat.setText(formattedDate);
        loadTable();
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RiwayatDetailPenjualan dialog = new RiwayatDetailPenjualan(new java.awt.Frame(), true);
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
    private javax.swing.JPanel dashSeacrhGroup;
    private javax.swing.JComboBox<String> dashboardComboPenjualan;
    private javax.swing.JTextField dashboardSearchBarPenjualan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private com.k33ptoo.components.KButton kButton2;
    private rojerusan.RSPanelImage rSPanelImage3;
    private javax.swing.JLabel tanggal_riwayat;
    // End of variables declaration//GEN-END:variables
}
