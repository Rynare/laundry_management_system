/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.pages;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import javax.swing.table.DefaultTableModel;
import laundry.frames.MainContent;
import laundry.popup.RiwayatDetailPenjualan;
import tools.tableControl;
import tools.tableControl.TableUpdateMode;
import tools.tools;

/**
 *
 * @author Rynare
 */
public class Riwayat extends javax.swing.JPanel {

    /**
     * Creates new form riwayat
     */
    laundry_management_connection koneksi = new laundry_management_connection();
    tableControl tableControl = new tableControl();

    private int prev_riwayat_tahun;
    private int prev_riwayat_bulan;
    private int clickCount = 0;
    private int prevRow;
    private int id_laundry;
    private MainContent parent;
    private ResultSet rs;

    RiwayatDetailPenjualan riwayatDetailPenjualan = new RiwayatDetailPenjualan(parent, true);

    public void setParent(MainContent parent) {
        this.parent = parent;
    }

    public void setID(int id_laundry) {
        this.id_laundry = id_laundry;
    }

    public void render(TableUpdateMode mode) {
        if (mode == TableUpdateMode.NORMAL) {
            if (!(tabelRiwayatBulan.getRowCount() > 0 || tabelRiwayatTahun.getRowCount() > 0 || tabelRiwayatTanggal.getRowCount() > 0)) {
                loadAllRiwayat();
            }
        } else if (mode == TableUpdateMode.ALWAYS_UPDATE) {
            loadAllRiwayat();
        }
    }

    public void loadRiwayatTahun(int id_laundry) {
        try {
            ResultSet rsTahun = koneksi.getDataWithPreparedStatement("CALL GetPenjualanTahunan(?)", id_laundry);
            DefaultTableModel modelTahun = new DefaultTableModel();
            modelTahun.setColumnIdentifiers(new String[]{"Tahun", "Pendapatan"});
            while (rsTahun.next()) {
                String tahun_ = rsTahun.getString(1);
                String pendapatan = "Rp. " + tools.currencyFormatter(rsTahun.getString("Pendapatan")).format();
                modelTahun.addRow(new Object[]{tahun_, pendapatan});
            }
            tabelRiwayatTahun.setModel(modelTahun);
            tableControl.setCustomTableSetup(tabelRiwayatTahun);
        } catch (SQLException e) {
        } finally {
            koneksi.closeConnection();
            if (tabelRiwayatTahun.getRowCount() == 0) {
                tableControl.insertFirstRow(tabelRiwayatTahun, String.valueOf(tools.TimeUtil.getCurrentYear()), "Rp. 0");
            } else {
                int tahun = Integer.parseInt(tabelRiwayatTahun.getValueAt(tabelRiwayatTahun.getRowCount() - 1, 0).toString());
                if (!(tahun == tools.TimeUtil.getCurrentYear())) {
                    tableControl.insertRow(tabelRiwayatTahun, tabelRiwayatTahun.getRowCount(), String.valueOf(tools.TimeUtil.getCurrentYear()), "Rp. 0");
                }
            }
            int last_row = tabelRiwayatTahun.getRowCount() - 1;
            tabelRiwayatTahun.setRowSelectionInterval(last_row, last_row);
        }
    }

    public void loadRiwayatBulan(int _tahun, int _id_laundry) {
        try {
            ResultSet rsBulan = koneksi.getDataWithPreparedStatement("CALL GetPenjualanBulanan(?, ?)", _tahun, id_laundry);
            DefaultTableModel modelBulan = new DefaultTableModel();
            modelBulan.setColumnIdentifiers(new String[]{"Bulan", "Pendapatan"});

            while (rsBulan.next()) {
                String bulan_ = tools.convertToMonthName(rsBulan.getInt("Bulan"));
                String pendapatan = "Rp. " + tools.currencyFormatter(rsBulan.getString("Pendapatan")).format();
                modelBulan.addRow(new Object[]{bulan_, pendapatan});
            }
            tabelRiwayatBulan.setModel(modelBulan);
            tableControl.setCustomTableSetup(tabelRiwayatBulan);
        } catch (SQLException e) {
        } finally {
            koneksi.closeConnection();
            int tahun = Integer.parseInt(tabelRiwayatTahun.getValueAt(tabelRiwayatTahun.getSelectedRow(), 0).toString());
            if (!tabelRiwayatTahun.getValueAt(tabelRiwayatTahun.getSelectedRow(), 1).toString().equals("Rp. 0")) {
                if (tabelRiwayatBulan.getRowCount() == 0) {
                    tableControl.insertFirstRow(tabelRiwayatBulan, tools.TimeUtil.getCurrentMonthString(), "Rp. 0");
                } else {
                    String bulan = tabelRiwayatBulan.getValueAt(tabelRiwayatBulan.getRowCount() - 1, 0).toString();
                    if (tahun == tools.TimeUtil.getCurrentYear() && !bulan.equals(tools.TimeUtil.getCurrentMonthString())) {
                        tableControl.insertRow(tabelRiwayatBulan, tabelRiwayatBulan.getRowCount(), tools.TimeUtil.getCurrentMonthString(), "Rp. 0");
                    }
                }

                if (_tahun == tools.TimeUtil.getCurrentYear()) {
                    for (int i = 0; i < tools.TimeUtil.getCurrentMonthInteger(); i++) {
                        int month = tools.convertToMonthNumber(tabelRiwayatBulan.getValueAt(i, 0).toString());
                        if (i + 1 < month) {
                            tableControl.insertRow(tabelRiwayatBulan, i, tools.convertToMonthName(i + 1), "Rp. 0");
                        }
                    }
                } else {
                    for (int i = 0; i < 12; i++) {
                        int bulan = 0;
                        if (i + 1 <= tabelRiwayatBulan.getRowCount()) {
                            bulan = tools.convertToMonthNumber(tabelRiwayatBulan.getValueAt(i, 0).toString());
                        } else {
                            bulan = i;
                        }

                        if (!(bulan == i + 1)) {
                            tableControl.insertRow(tabelRiwayatBulan, i, tools.convertToMonthName(i + 1), "Rp. 0");
                        }
                    }
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    tableControl.insertRow(tabelRiwayatBulan, i, tools.convertToMonthName(i + 1), "Rp. 0");
                }
            }

            if (tahun == tools.TimeUtil.getCurrentYear()) {
                int last_row = tabelRiwayatBulan.getRowCount() - 1;
                tabelRiwayatBulan.setRowSelectionInterval(last_row, last_row);
            }
        }
    }

    public void loadRiwayatTanggal(int _bulan, int _tahun, int _id_laundry) {
        try {
            ResultSet rsTanggal = koneksi.getDataWithPreparedStatement("CALL GetPenjualanHarian(?,?,?)", _bulan, _tahun, id_laundry);

            DefaultTableModel modelTanggal = new DefaultTableModel();
            modelTanggal.setColumnIdentifiers(new String[]{"Tanggal", "Pendapatan"});

            while (rsTanggal.next()) {
                String tanggal_ = rsTanggal.getString(1);
                String pendapatan = "Rp. " + tools.currencyFormatter(rsTanggal.getString("Pendapatan")).format();
                modelTanggal.addRow(new Object[]{tanggal_, pendapatan});
            }

            tabelRiwayatTanggal.setModel(modelTanggal);
            tableControl.setCustomTableSetup(tabelRiwayatTanggal);
        } catch (SQLException e) {
            // Handle the exception
        } finally {
            koneksi.closeConnection();
            String nama_bulan = tools.convertToMonthName(_bulan);
            if (!tabelRiwayatBulan.getValueAt(tabelRiwayatBulan.getSelectedRow(), 1).toString().equals("Rp. 0")) {
                if (tabelRiwayatTanggal.getRowCount() == 0 && nama_bulan.equals(tools.TimeUtil.getCurrentMonthString())) {
                    tableControl.insertFirstRow(tabelRiwayatTanggal, String.valueOf(tools.TimeUtil.getCurrentDayInteger()), "Rp. 0");
                } else {
                    int tanggal = Integer.parseInt(tabelRiwayatTanggal.getValueAt(tabelRiwayatTanggal.getRowCount() - 1, 0).toString());
                    if (_tahun == tools.TimeUtil.getCurrentYear() && nama_bulan.equals(tools.TimeUtil.getCurrentMonthString()) && !(tanggal == tools.TimeUtil.getCurrentDayInteger())) {
                        tableControl.insertRow(tabelRiwayatTanggal, tabelRiwayatTanggal.getRowCount(), String.valueOf(tools.TimeUtil.getCurrentDayInteger()), "Rp. 0");
                    }
                }
                if (_tahun == tools.TimeUtil.getCurrentYear() && nama_bulan.equals(tools.TimeUtil.getCurrentMonthString())) {
                    for (int i = 0; i < tools.TimeUtil.getCurrentDayInteger(); i++) {
                        int tanggal = Integer.parseInt(tabelRiwayatTanggal.getValueAt(i, 0).toString());
                        if (i + 1 < tanggal) {
                            tableControl.insertRow(tabelRiwayatTanggal, i, String.valueOf(i + 1), "Rp. 0");
                        }
                    }
                } else {
                    for (int i = 0; i < tools.TimeUtil.getMaxDayInMonth(_tahun, _bulan); i++) {
                        int tanggal = 0;
                        if (i + 1 <= tabelRiwayatTanggal.getRowCount()) {
                            tanggal = Integer.parseInt(tabelRiwayatTanggal.getValueAt(i, 0).toString());
                        } else {
                            tanggal = i;
                        }

                        if (!(tanggal == i + 1)) {
                            tableControl.insertRow(tabelRiwayatTanggal, i, String.valueOf(i + 1), "Rp. 0");
                        }
                    }
                }
            } else {
                for (int i = 0; i < tools.TimeUtil.getMaxDayInMonth(_tahun, _bulan); i++) {
                    tableControl.insertRow(tabelRiwayatTanggal, i, String.valueOf(i + 1), "Rp. 0");
                }
            }

            if (_tahun == tools.TimeUtil.getCurrentYear() && nama_bulan.equals(tools.TimeUtil.getCurrentMonthString())) {
                int last_row = tabelRiwayatTanggal.getRowCount() - 1;
                tabelRiwayatTanggal.setRowSelectionInterval(last_row, last_row);
            }
        }
    }

    public void loadAllRiwayat() {
        int _tahun = tools.TimeUtil.getCurrentYear();
        int _bulan = tools.TimeUtil.getCurrentMonthInteger();
        loadRiwayatTahun(id_laundry);
        loadRiwayatBulan(_tahun, id_laundry);
        loadRiwayatTanggal(_bulan, _tahun, id_laundry);
    }

    public Riwayat() {
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

        kGradientPanel20 = new com.k33ptoo.components.KGradientPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelRiwayatTahun = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        kGradientPanel21 = new com.k33ptoo.components.KGradientPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelRiwayatTanggal = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        kGradientPanel22 = new com.k33ptoo.components.KGradientPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelRiwayatBulan = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        kGradientPanel20.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel20.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel20.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel20.setOpaque(false);

        tabelRiwayatTahun.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabelRiwayatTahun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelRiwayatTahun.setRowHeight(24);
        tabelRiwayatTahun.setShowHorizontalLines(false);
        tabelRiwayatTahun.setShowVerticalLines(false);
        tabelRiwayatTahun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatTahunMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelRiwayatTahun);

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Tahun");

        javax.swing.GroupLayout kGradientPanel20Layout = new javax.swing.GroupLayout(kGradientPanel20);
        kGradientPanel20.setLayout(kGradientPanel20Layout);
        kGradientPanel20Layout.setHorizontalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                .addContainerGap())
        );
        kGradientPanel20Layout.setVerticalGroup(
            kGradientPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        kGradientPanel21.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel21.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel21.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel21.setOpaque(false);

        tabelRiwayatTanggal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabelRiwayatTanggal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelRiwayatTanggal.setRowHeight(24);
        tabelRiwayatTanggal.setShowHorizontalLines(false);
        tabelRiwayatTanggal.setShowVerticalLines(false);
        tabelRiwayatTanggal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatTanggalMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tabelRiwayatTanggal);

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Tanggal");

        javax.swing.GroupLayout kGradientPanel21Layout = new javax.swing.GroupLayout(kGradientPanel21);
        kGradientPanel21.setLayout(kGradientPanel21Layout);
        kGradientPanel21Layout.setHorizontalGroup(
            kGradientPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        kGradientPanel21Layout.setVerticalGroup(
            kGradientPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        kGradientPanel22.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel22.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel22.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel22.setOpaque(false);

        tabelRiwayatBulan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabelRiwayatBulan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelRiwayatBulan.setRowHeight(24);
        tabelRiwayatBulan.setShowHorizontalLines(false);
        tabelRiwayatBulan.setShowVerticalLines(false);
        tabelRiwayatBulan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatBulanMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tabelRiwayatBulan);

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Bulan");

        javax.swing.GroupLayout kGradientPanel22Layout = new javax.swing.GroupLayout(kGradientPanel22);
        kGradientPanel22.setLayout(kGradientPanel22Layout);
        kGradientPanel22Layout.setHorizontalGroup(
            kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel22Layout.createSequentialGroup()
                .addGroup(kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel22Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(kGradientPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))
                .addContainerGap())
        );
        kGradientPanel22Layout.setVerticalGroup(
            kGradientPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jLabel10.setFont(new java.awt.Font("Verdana", 3, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Laporan Penjualan");

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24pxNew/icons8_refresh_30px_1.png"))); // NOI18N
        jLabel1.setText("Refresh");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kGradientPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(kGradientPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tabelRiwayatTahunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatTahunMouseClicked
        // TODO add your handling code here:
        int baris_tabel_Tahun = tabelRiwayatTahun.getSelectedRow();
        int tahun = Integer.parseInt(tabelRiwayatTahun.getValueAt(baris_tabel_Tahun, 0).toString());
        int bulan = tools.TimeUtil.getCurrentMonthInteger();
        if (!(prev_riwayat_tahun == tahun)) {
            loadRiwayatBulan(tahun, id_laundry);
            loadRiwayatTanggal(bulan, tahun, id_laundry);
            prev_riwayat_tahun = tahun;
        }
    }//GEN-LAST:event_tabelRiwayatTahunMouseClicked

    private void tabelRiwayatTanggalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatTanggalMouseClicked
        // TODO add your handling code here:
        if (!(tabelRiwayatBulan.getSelectedRow() >= 0) && tabelRiwayatBulan.getRowCount() > 0) {
            int lastRowIndex = tabelRiwayatBulan.getRowCount() - 1;
            tabelRiwayatBulan.changeSelection(lastRowIndex, 0, false, false);
        }
        if (!(tabelRiwayatTahun.getSelectedRow() >= 0) && tabelRiwayatTahun.getRowCount() > 0) {
            int lastRowIndex = tabelRiwayatTahun.getRowCount() - 1;
            tabelRiwayatTahun.changeSelection(lastRowIndex, 0, false, false);
        }

        clickCount++;
        int _tanggal = Integer.valueOf(tabelRiwayatTanggal.getValueAt(tabelRiwayatTanggal.getSelectedRow(), 0).toString());
        int _bulan = tools.convertToMonthNumber(tabelRiwayatBulan.getValueAt(tabelRiwayatBulan.getSelectedRow(), 0).toString());
        String _tahun = tabelRiwayatTahun.getValueAt(tabelRiwayatTahun.getSelectedRow(), 0).toString();
        if (clickCount == 2) {
            if (prevRow == tabelRiwayatTanggal.getSelectedRow()) {
                if (_tanggal < 10) {
                    if (_bulan < 10) {
                        riwayatDetailPenjualan.setData(_tahun + "-0" + _bulan + "-0" + _tanggal, id_laundry);
                    } else {
                        riwayatDetailPenjualan.setData(_tahun + "-" + _bulan + "-0" + _tanggal, id_laundry);
                    }
                } else {
                    if (_bulan < 10) {
                        riwayatDetailPenjualan.setData(_tahun + "-0" + _bulan + "-" + _tanggal, id_laundry);
                    } else {
                        riwayatDetailPenjualan.setData(_tahun + "-" + _bulan + "-" + _tanggal, id_laundry);
                    }
                }

                if (!riwayatDetailPenjualan.isVisible()) {
                    riwayatDetailPenjualan.setVisible(true);
                }
            }
            clickCount = 0;
        } else {
            prevRow = tabelRiwayatTanggal.getSelectedRow();
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clickCount = 0;
                }
            }, 500); // Waktu dalam milidetik untuk mereset clickCount jika tidak ada double-click berikutnya dalam 200ms
        }
    }//GEN-LAST:event_tabelRiwayatTanggalMouseClicked

    private void tabelRiwayatBulanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatBulanMouseClicked
        // TODO add your handling code here:
        int baris_tabel_Bulan = tabelRiwayatBulan.getSelectedRow();
        int baris_tabel_Tahun = tabelRiwayatTahun.getSelectedRow();

        if (!(tabelRiwayatTahun.getSelectedRow() >= 0) && tabelRiwayatTahun.getRowCount() > 0) {
            int lastRowIndex = tabelRiwayatTahun.getRowCount() - 1;
            tabelRiwayatTahun.changeSelection(lastRowIndex, 0, false, false);
            baris_tabel_Tahun = tabelRiwayatTahun.getSelectedRow();
        }
        if (tabelRiwayatTahun.getSelectedRow() >= 0 && tabelRiwayatTahun.getRowCount() > 0 && !(tools.convertToMonthNumber(tabelRiwayatBulan.getValueAt(baris_tabel_Bulan, 0).toString()) == prev_riwayat_bulan)) {
            int _bulan = tools.convertToMonthNumber(tabelRiwayatBulan.getValueAt(baris_tabel_Bulan, 0).toString());
            int _tahun = Integer.parseInt(tabelRiwayatTahun.getValueAt(baris_tabel_Tahun, 0).toString());

            loadRiwayatTanggal(_bulan, _tahun, id_laundry);
            prev_riwayat_bulan = _bulan;
        }
    }//GEN-LAST:event_tabelRiwayatBulanMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        loadAllRiwayat();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private com.k33ptoo.components.KGradientPanel kGradientPanel20;
    private com.k33ptoo.components.KGradientPanel kGradientPanel21;
    private com.k33ptoo.components.KGradientPanel kGradientPanel22;
    private javax.swing.JTable tabelRiwayatBulan;
    private javax.swing.JTable tabelRiwayatTahun;
    private javax.swing.JTable tabelRiwayatTanggal;
    // End of variables declaration//GEN-END:variables
}
