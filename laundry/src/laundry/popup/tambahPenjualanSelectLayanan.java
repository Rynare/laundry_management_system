/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import tools.comboBoxControl;
import tools.tableControl.TableUpdateMode;
import tools.tableControl;

/**
 *
 * @author Rynare
 */
public class tambahPenjualanSelectLayanan extends java.awt.Dialog {

    /**
     * Creates new form tambahPenjualanSelectLayanan1
     */
    
    laundry_management_connection koneksiDB = new laundry_management_connection();
    tableControl tableControl = new tableControl();
    ResultSet rs;
    ResultSetMetaData rsmd;
    tambahPenjualan parent;

    int prevRow = -1;
    int clickCount = 0;
    /**
     * Creates new form tambahPenjualanSelectLayanan
     * @param parent
     */
    
    public void setParent(tambahPenjualan parent){
        this.parent = parent;
    }
    
    public void loadTableFromFilter() {
        String query = "SELECT * FROM searchLayanan";
        String userInput = SearchBarLayanan.getText();
        String tipeKondisi = comboBoxControl.getComboBoxValue(comboBoxLayanan);
        String kondisi = "";

        if (!userInput.isEmpty()) {
            if (tipeKondisi.equals("Harga")) {
                kondisi += " WHERE searchLayanan.harga = " + userInput;
            } else if (tipeKondisi.equals("Nama_Layanan")) {
                kondisi += " WHERE searchLayanan.`Nama Layanan` LIKE '%" + userInput + "%'";
            }

            try {
                ResultSet resultSet = koneksiDB.getData(query + kondisi);
                loadTableLayanan(query + kondisi, TableUpdateMode.ALWAYS_UPDATE);
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println("Gagal menjalankan query: " + ex.getMessage());
            } finally {
                koneksiDB.closeConnection();
            }
        }else{
            tableControl.loadTableFromDB(tabelLayanan, TableUpdateMode.ALWAYS_UPDATE ,"SELECT * FROM searchLayanan");
        }
    }

    public Object[][] getDataTableFromDB(String query) {
        Object[][] result = null;
        try {
            // Execute query and get the ResultSet
            rs = koneksiDB.getData(query);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Get header
            Object[] header = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                header[i - 1] = rsmd.getColumnLabel(i);
            }

            // Get data
            ArrayList<Object[]> dataList = new ArrayList<>();
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                dataList.add(rowData);
            }

            // Convert List to array
            Object[][] data = new Object[dataList.size()][columnCount];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            // Set the result
            result = new Object[][]{header, data};
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal:\n" + e.getMessage());
        } finally {
            koneksiDB.closeConnection();
        }
        return result;
    }

// =============================================================================
// =============================/ Loader Method /===============================
// =============================================================================
    public void setCustomTableSetup(JTable table) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableCellEditor nonEditableEditor = new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(java.util.EventObject e) {
                return false;
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellEditor(nonEditableEditor);
        }
        table.getTableHeader().setReorderingAllowed(false);
    }

    public void loadTableLayanan(String query, TableUpdateMode mode) {
        tableControl.loadTableFromDB(tabelLayanan, mode, query);
    }

    
    public tambahPenjualanSelectLayanan(tambahPenjualan parent, boolean modal) {
        super(parent, modal);
        setParent(parent);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelLayanan = new javax.swing.JTable();
        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        SearchBarLayanan = new javax.swing.JTextField();
        rSPanelImage3 = new rojerusan.RSPanelImage();
        jSeparator1 = new javax.swing.JSeparator();
        comboBoxLayanan = new javax.swing.JComboBox<>();
        kButton7 = new com.k33ptoo.components.KButton();
        jLabel1 = new javax.swing.JLabel();

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
        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        tabelLayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelLayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelLayananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelLayanan);

        kGradientPanel1.setBackground(new java.awt.Color(51, 51, 51));
        kGradientPanel1.setkBorderRadius(20);
        kGradientPanel1.setkEndColor(new java.awt.Color(102, 102, 102));
        kGradientPanel1.setkStartColor(new java.awt.Color(102, 102, 102));

        SearchBarLayanan.setBackground(new java.awt.Color(102, 102, 102));
        SearchBarLayanan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SearchBarLayanan.setForeground(new java.awt.Color(255, 255, 255));
        SearchBarLayanan.setBorder(null);
        SearchBarLayanan.setCaretColor(new java.awt.Color(255, 255, 255));
        SearchBarLayanan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        SearchBarLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchBarLayananActionPerformed(evt);
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

        comboBoxLayanan.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        comboBoxLayanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nama_Layanan", "Harga" }));
        comboBoxLayanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxLayananItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchBarLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(comboBoxLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchBarLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxLayanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1))
        );

        kButton7.setText("Refresh Table\n");
        kButton7.setActionCommand("");
        kButton7.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton7.setkEndColor(new java.awt.Color(24, 144, 255));
        kButton7.setkHoverEndColor(new java.awt.Color(24, 144, 255));
        kButton7.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton7.setkStartColor(new java.awt.Color(24, 144, 255));
        kButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton7MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 3, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("* klik 2 kali pada layanan untuk memilih");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addGap(10, 10, 10))
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

    private void tabelLayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelLayananMouseClicked
        clickCount++;
        if (clickCount == 2) {
            if (prevRow == tabelLayanan.getSelectedRow()) {
                int baris = tabelLayanan.getSelectedRow();
                System.out.println("Double-clicked on row: " + prevRow);
                int id = (int) tabelLayanan.getValueAt(baris, 0);
                String nama_layanan = (String) tabelLayanan.getValueAt(baris, 1);
                String harga = (String) tabelLayanan.getValueAt(baris, 2).toString();
                parent.setLayanan(id, nama_layanan, harga);
                this.dispose();
            }
            clickCount = 0;
        } else {
            prevRow = tabelLayanan.getSelectedRow();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clickCount = 0;
                }
            }, 500); // Waktu dalam milidetik untuk mereset clickCount jika tidak ada double-click berikutnya dalam 200ms
        }
    }//GEN-LAST:event_tabelLayananMouseClicked

    private void SearchBarLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchBarLayananActionPerformed
        // TODO add your handling code here:
        loadTableFromFilter();
    }//GEN-LAST:event_SearchBarLayananActionPerformed

    private void comboBoxLayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxLayananItemStateChanged
        // TODO add your handling code here:
        loadTableFromFilter();
    }//GEN-LAST:event_comboBoxLayananItemStateChanged

    private void kButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton7MouseClicked
        // TODO add your handling code here:
        loadTableLayanan("SELECT * FROM searchLayanan", TableUpdateMode.ALWAYS_UPDATE);
    }//GEN-LAST:event_kButton7MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        loadTableLayanan("SELECT * FROM searchLayanan", TableUpdateMode.ALWAYS_UPDATE);
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                tambahPenjualanSelectLayanan dialog = new tambahPenjualanSelectLayanan(new tambahPenjualan(), true);
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
    private javax.swing.JTextField SearchBarLayanan;
    private javax.swing.JComboBox<String> comboBoxLayanan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private com.k33ptoo.components.KButton kButton7;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private rojerusan.RSPanelImage rSPanelImage3;
    private javax.swing.JTable tabelLayanan;
    // End of variables declaration//GEN-END:variables
}
