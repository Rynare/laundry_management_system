/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.pages;

import Util.laundry_management_connection;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import laundry.frames.MainContent;
import laundry.popup.Pembayaran;
import laundry.popup.lihatPenjualan;
import laundry.popup.tambahPenjualan;
import tools.comboBoxControl;
import tools.dialogControl;
import tools.queryControl;
import tools.tableControl;
import tools.tableControl.TableUpdateMode;
import tools.tools;

/**
 *
 * @author Rynare
 */
public class Beranda extends javax.swing.JPanel {

    /**
     * Creates new form beranda
     */
    private int clickCount = 0;
    private int prevRow;
    private int id_laundry;
    private MainContent parent;
    private String currentTableMode = "pre-normal";
    private boolean pageControllerStatus = false;
    
    laundry_management_connection koneksi = new laundry_management_connection();
    tableControl tableControl = new tableControl();
    lihatPenjualan LihatPenjualan = new lihatPenjualan(parent, true);
    tambahPenjualan TambahPenjualan = new tambahPenjualan();
    Pembayaran Pembayaran = new Pembayaran(parent, true);

    public void setParent(MainContent parent) {
        this.parent = parent;
    }

    public void setID(int id_laundry) {
        this.id_laundry = id_laundry;
    }

    public void updatePage(String... values) {
        loadDashboardTrivia();
        tableControl.insertFirstRow(tabelDashboard, values);
    }

    enum pageLoad {
        Next, Prev, To
    }

    public boolean loadDashboardWithFilterDivisionPage(int limitAmount, int startFromRow) {
        String query = "SELECT * FROM searchPenjualan WHERE id_laundry = ? AND searchPenjualan.Status IN ('belum dikerjakan', 'belum bayar')";
        String userInput = dashboardSearchBarPenjualan.getText();
        String tipeKondisi = comboBoxControl.getComboBoxValue(dashboardComboPenjualan);
        ArrayList<Object> values = new ArrayList<>();
        values.add(id_laundry);
        String[] ignoredColumns = {"id_laundry"};
        boolean isLoad = false;
        currentTableMode = currentTableMode.contains("filter") ? currentTableMode.equals("filter : " + tipeKondisi + " " + userInput) ? "filter : " + tipeKondisi + " " + userInput : "filter" : "filter";

        if (!userInput.isEmpty() && currentTableMode.toLowerCase().contains("filter")) {
            if (tipeKondisi.equals("ID_Penjualan")) {
                query += " AND searchPenjualan.ID_Penjualan = ?";
                values.add(userInput);
            } else if (tipeKondisi.equals("Nama_Pelanggan")) {
                query += " AND (";
                String[] words = userInput.split(" ");
                for (int i = 0; i < words.length; i++) {
                    if (i > 0) {
                        query += " AND";
                    }
                    query += " searchPenjualan.Nama_Pelanggan LIKE ?";
                    values.add("%" + words[i] + "%");
                }
                query += ")";
            }
            query += " LIMIT " + limitAmount + " OFFSET " + (startFromRow - 1);

            Object[] val = new Object[values.size()];

            for (int i = 0; i < values.size(); i++) {
                val[i] = values.get(i);
            }
            
                isLoad = tableControl.loadTableFromDB(tabelDashboard, TableUpdateMode.ALWAYS_UPDATE, ignoredColumns, query, val);
            
                if (isLoad && !currentTableMode.equals("filter : " + tipeKondisi + " " + userInput)) {
                    String query2 = query;
                    query2 = query2.replace(" LIMIT " + limitAmount + " OFFSET " + (startFromRow - 1), "");
                    query2 = query2.replace("*", "COUNT(ID_Penjualan) AS jumlah");
                    loadPageController(query2, val);
                    currentTableMode = "filter : " + tipeKondisi + " " + userInput;
                }
            
        } else {
            loadDashboardWithNormalDivisionPage(20, 1);
        }
        return isLoad;
    }

    public boolean loadSpecificPage(pageLoad mode, int currentPage) {
        boolean isLoad = false;
        int next_page = (currentPage * 20) + 1;
        int prev_page = (currentPage * 20) - 40 + 1;
        int to_page = (currentPage * 20) - 20 + 1;
        int max_page = (pageSelector.getItemCount() * 20) - 20 + 1;

//        System.out.println("current page = " + currentPage);
//        System.out.println("next number = " + next_page);
//        System.out.println("prev number = " + prev_page);
//        System.out.println("max number = " + max_page);
        if (currentTableMode.equals("normal")) {
            switch (mode) {
                case Next:
                    if (next_page <= max_page) {
                        isLoad = loadDashboardWithNormalDivisionPage(20, next_page);
                    }
                    break;
                case Prev:
                    if (prev_page >= 1) {
                        isLoad = loadDashboardWithNormalDivisionPage(20, prev_page);
                    }
                    break;
                case To:
                    isLoad = loadDashboardWithNormalDivisionPage(20, to_page);
                    break;
                default:
                    break;
            }
        }
        if (currentTableMode.contains("filter : ")) {
            switch (mode) {
                case Next:
                    if (next_page <= max_page) {
                        isLoad = loadDashboardWithFilterDivisionPage(20, next_page);
                    }
                    break;
                case Prev:
                    if (prev_page >= 1) {
                        isLoad = loadDashboardWithFilterDivisionPage(20, prev_page);
                    }
                    break;
                case To:
                    isLoad = loadDashboardWithFilterDivisionPage(20, to_page);
                    break;
                default:
                    break;
            }
        }

//        System.out.println("load : " + isLoad);
        if (isLoad && currentPage >= 1 && currentPage <= pageSelector.getItemCount()) {
            pageControllerStatus = false;
            switch (mode) {
                case Next:
                    if (currentPage < pageSelector.getItemCount()) {
                        pageSelector.setSelectedIndex(currentPage);
                    }
                    break;
                case Prev:
                    if (currentPage > 1) {
                        pageSelector.setSelectedIndex(currentPage - 2);
                    }
                    break;
                case To:
                    break;
                default:
                    throw new AssertionError();
            }
            pageControllerStatus = true;
        }
        return isLoad;
    }

    public boolean loadDashboardWithNormalDivisionPage(int limitAmount, int startFromRow) {
        currentTableMode = currentTableMode.equals("normal") ? "normal" : "pre-normal";
        boolean isLoad = false;
        if (currentTableMode.toLowerCase().equals("normal") || currentTableMode.toLowerCase().equals("pre-normal")) {
            String query = "CALL GetPenjualanByPage(?,?,?)";
            Object[] value = {id_laundry, limitAmount, startFromRow - 1};
            String[] ignoredColumns = {"id_laundry"};

            isLoad = tableControl.loadTableFromDB(tabelDashboard, TableUpdateMode.ALWAYS_UPDATE, ignoredColumns, query, value);
            if (isLoad && currentTableMode.equals("pre-normal")) {
                String queryPageCount = "SELECT COUNT(ID_Penjualan) AS jumlah FROM `searchpenjualan` WHERE id_laundry = ?";
                loadPageController(queryPageCount, id_laundry);
                currentTableMode = "normal";
            }
        }
        return isLoad;
    }
    
    public void loadPageController(String query, Object... values) {
        pageSelector.removeAllItems();
        try {
            ResultSet results = koneksi.getDataWithPreparedStatement(query, values);
            if (results.next()) {
                int data = results.getInt(1);
                int dataInPage = 20;
                int pageCount = (int) Math.ceil((double) data / dataInPage);
                for (int i = 1; i <= pageCount; i++) {
                    pageSelector.addItem(String.valueOf(i));
                }
                System.out.println("jumlah data : " + data);
            }
        } catch (SQLException e) {
        } finally {
            pageControllerStatus = true;
            koneksi.closeConnection();
        }
    }

    public void loadDashboardTrivia() {
        try {
            ResultSet layanan_populer_rs = koneksi.getDataWithPreparedStatement("CALL GetMostOrderedService(?)", id_laundry);
            ResultSet parfum_populer_rs = koneksi.getDataWithPreparedStatement("CALL GetMostOrderedItem(?)", id_laundry);
            ResultSet pendapatan_bulan_ini_rs = koneksi.getDataWithPreparedStatement("CALL GetMonthlyRevenue(?) ", id_laundry);
            ResultSet pendapatan_hari_ini_rs = koneksi.getDataWithPreparedStatement("CALL GetDailyRevenue(?)", id_laundry);

            if (layanan_populer_rs.next()) {
                layanan_populer.setText(layanan_populer_rs.getString("nama_layanan"));
                jumlah_layanan_populer.setText(layanan_populer_rs.getString("jumlah_pesanan") + "x");
            } else {
                layanan_populer.setText("Tidak Ada");
                jumlah_layanan_populer.setText("0x");
            }

            if (parfum_populer_rs.next()) {
                parfum_populer.setText(parfum_populer_rs.getString("nama_barang"));
                jumlah_parfum_populer.setText(parfum_populer_rs.getString("jumlah_pesanan") + "x");
            } else {
                parfum_populer.setText("Tidak ada");
                jumlah_parfum_populer.setText("0x");
            }

            if (pendapatan_hari_ini_rs.next()) {
                pendapatan_hari_ini.setText("Rp. " + tools.currencyFormatter(Integer.toString(pendapatan_hari_ini_rs.getInt(1))).format());
            } else {
                pendapatan_hari_ini.setText("Rp. 0");
            }

            if (pendapatan_bulan_ini_rs.next()) {
                pendapatan_bulan_ini.setText("Rp. " + tools.currencyFormatter(Integer.toString(pendapatan_bulan_ini_rs.getInt(1))).format());
            } else {
                pendapatan_bulan_ini.setText("Rp. 0");
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

//    public void loadTableDashboard(String query, tableControl.TableUpdateMode mode, Object... value) {
//        String[] ignored = {"id_laundry"};
//        tableControl.loadTableFromDB(tabelDashboard, mode, ignored, query, value);
//    }
    public void render(TableUpdateMode mode) {
        loadDashboardTrivia();
//        loadTableDashboard("CALL GetPenjualanByPage(?,20,0)", mode, id_laundry);
        loadDashboardWithNormalDivisionPage(20, 1);
        pageSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED && pageControllerStatus) {
                    loadSpecificPage(pageLoad.To, Integer.parseInt(pageSelector.getSelectedItem().toString()));
                }
            }
        });
    }

    public Beranda() {
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

        kGradientPanel1 = new com.k33ptoo.components.KGradientPanel();
        jLabel7 = new javax.swing.JLabel();
        parfum_populer = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jumlah_parfum_populer = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        kGradientPanel5 = new com.k33ptoo.components.KGradientPanel();
        jLabel8 = new javax.swing.JLabel();
        pendapatan_hari_ini = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pendapatan_bulan_ini = new javax.swing.JLabel();
        kGradientPanel4 = new com.k33ptoo.components.KGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        layanan_populer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jumlah_layanan_populer = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        dashTableGroup = new javax.swing.JPanel();
        dashSeacrhGroup = new javax.swing.JPanel();
        dashboardSearchBarPenjualan = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        rSPanelImage3 = new rojerusan.RSPanelImage();
        dashboardComboPenjualan = new javax.swing.JComboBox<>();
        kButton2 = new com.k33ptoo.components.KButton();
        jPanel1 = new javax.swing.JPanel();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle5 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle6 = new rojerusan.RSMaterialButtonRectangle();
        jLabel1 = new javax.swing.JLabel();
        pageSelector = new javax.swing.JComboBox<>();
        next_page_btn = new javax.swing.JButton();
        prev_page_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelDashboard = new javax.swing.JTable();

        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        kGradientPanel1.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel1.setkBorderRadius(30);
        kGradientPanel1.setkEndColor(new java.awt.Color(5, 46, 80));
        kGradientPanel1.setkStartColor(new java.awt.Color(9, 120, 217));
        kGradientPanel1.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Parfum Populer");

        parfum_populer.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        parfum_populer.setForeground(new java.awt.Color(255, 255, 0));
        parfum_populer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        parfum_populer.setText("none");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24pxNew/icons8_perfume_bottle_30px.png"))); // NOI18N

        jumlah_parfum_populer.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jumlah_parfum_populer.setForeground(new java.awt.Color(255, 255, 0));
        jumlah_parfum_populer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jumlah_parfum_populer.setText("none");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Dipesan sebanyak");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(parfum_populer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jumlah_parfum_populer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(parfum_populer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jumlah_parfum_populer)
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        kGradientPanel5.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel5.setkBorderRadius(30);
        kGradientPanel5.setkEndColor(new java.awt.Color(5, 46, 80));
        kGradientPanel5.setkStartColor(new java.awt.Color(9, 120, 217));
        kGradientPanel5.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Pendapatan Hari Ini");

        pendapatan_hari_ini.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        pendapatan_hari_ini.setForeground(new java.awt.Color(255, 255, 0));
        pendapatan_hari_ini.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pendapatan_hari_ini.setText("none");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24pxNew/icons8_sales_performance_24px.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Pendapatan Bulan Ini");

        pendapatan_bulan_ini.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        pendapatan_bulan_ini.setForeground(new java.awt.Color(255, 255, 0));
        pendapatan_bulan_ini.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pendapatan_bulan_ini.setText("none");

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel5Layout.createSequentialGroup()
                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kGradientPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addGap(61, 61, 61))
                            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pendapatan_bulan_ini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pendapatan_hari_ini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(20, 20, 20))
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendapatan_hari_ini)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pendapatan_bulan_ini)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kGradientPanel4.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel4.setkBorderRadius(30);
        kGradientPanel4.setkEndColor(new java.awt.Color(5, 46, 80));
        kGradientPanel4.setkStartColor(new java.awt.Color(9, 120, 217));
        kGradientPanel4.setOpaque(false);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Layanan Populer");

        layanan_populer.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        layanan_populer.setForeground(new java.awt.Color(255, 255, 0));
        layanan_populer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        layanan_populer.setText("none");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24pxNew/icons8_washing_machine_30px_4.png"))); // NOI18N

        jumlah_layanan_populer.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jumlah_layanan_populer.setForeground(new java.awt.Color(255, 255, 0));
        jumlah_layanan_populer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jumlah_layanan_populer.setText("none");

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Dipesan sebanyak");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(135, 135, 135))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layanan_populer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jumlah_layanan_populer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(20, 20, 20))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(layanan_populer)
                .addGap(5, 5, 5)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jumlah_layanan_populer)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        dashTableGroup.setBackground(new java.awt.Color(102, 102, 102));
        dashTableGroup.setOpaque(false);

        dashSeacrhGroup.setBackground(new java.awt.Color(102, 102, 102));
        dashSeacrhGroup.setOpaque(false);

        dashboardSearchBarPenjualan.setBackground(new java.awt.Color(19, 25, 47));
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
        rSPanelImage3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSPanelImage3MouseClicked(evt);
            }
        });

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
        dashboardComboPenjualan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID_Penjualan", "Nama_Pelanggan" }));
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
                    .addComponent(jSeparator1)
                    .addGroup(dashSeacrhGroupLayout.createSequentialGroup()
                        .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dashboardSearchBarPenjualan)))
                .addGap(20, 20, 20)
                .addComponent(dashboardComboPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        dashSeacrhGroupLayout.setVerticalGroup(
            dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashSeacrhGroupLayout.createSequentialGroup()
                .addGroup(dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dashboardComboPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashSeacrhGroupLayout.createSequentialGroup()
                .addGroup(dashSeacrhGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dashboardSearchBarPenjualan, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rSPanelImage3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setOpaque(false);

        rSMaterialButtonRectangle3.setText("Tambah");
        rSMaterialButtonRectangle3.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        rSMaterialButtonRectangle3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle3MouseClicked(evt);
            }
        });

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(255, 77, 79));
        rSMaterialButtonRectangle2.setText("Hapus");
        rSMaterialButtonRectangle2.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        rSMaterialButtonRectangle2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle2MouseClicked(evt);
            }
        });

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(204, 204, 204));
        rSMaterialButtonRectangle1.setText("Refresh");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        rSMaterialButtonRectangle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle1MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });

        rSMaterialButtonRectangle5.setBackground(new java.awt.Color(46, 177, 98));
        rSMaterialButtonRectangle5.setText("Bayar");
        rSMaterialButtonRectangle5.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        rSMaterialButtonRectangle5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle5MouseClicked(evt);
            }
        });

        rSMaterialButtonRectangle6.setBackground(new java.awt.Color(76, 17, 181));
        rSMaterialButtonRectangle6.setText("Ubah Status Pengerjaan");
        rSMaterialButtonRectangle6.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        rSMaterialButtonRectangle6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle6MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("Halaman ke-");

        pageSelector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pageSelectorItemStateChanged(evt);
            }
        });
        pageSelector.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pageSelectorMouseClicked(evt);
            }
        });
        pageSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageSelectorActionPerformed(evt);
            }
        });

        next_page_btn.setText("Next");
        next_page_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                next_page_btnMouseClicked(evt);
            }
        });

        prev_page_btn.setText(" Prev");
        prev_page_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prev_page_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pageSelector, 0, 85, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(prev_page_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(next_page_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(rSMaterialButtonRectangle3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSMaterialButtonRectangle2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSMaterialButtonRectangle6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rSMaterialButtonRectangle3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rSMaterialButtonRectangle1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(prev_page_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(next_page_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pageSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setOpaque(false);

        tabelDashboard.setAutoCreateRowSorter(true);
        tabelDashboard.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabelDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        )
    );
    tabelDashboard.setToolTipText("Klik 2x Pada Tabel untuk melihat detail pesanan");
    tabelDashboard.setGridColor(new java.awt.Color(255, 255, 255));
    tabelDashboard.setOpaque(false);
    tabelDashboard.setRowHeight(24);
    tabelDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tabelDashboardMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tabelDashboard);

    javax.swing.GroupLayout dashTableGroupLayout = new javax.swing.GroupLayout(dashTableGroup);
    dashTableGroup.setLayout(dashTableGroupLayout);
    dashTableGroupLayout.setHorizontalGroup(
        dashTableGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(dashSeacrhGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(dashTableGroupLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1)
            .addContainerGap())
    );
    dashTableGroupLayout.setVerticalGroup(
        dashTableGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dashTableGroupLayout.createSequentialGroup()
            .addGap(4, 4, 4)
            .addComponent(dashSeacrhGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
            .addGap(0, 0, 0))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dashTableGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(10, 10, 10)
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(10, 10, 10)
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(20, 20, 20))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 456, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 456, Short.MAX_VALUE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(20, 20, 20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(dashTableGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(20, 20, 20))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 268, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 269, Short.MAX_VALUE)))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardSearchBarPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardSearchBarPenjualanActionPerformed
        // TODO add your handling code here:
        loadDashboardWithFilterDivisionPage(20, 1);
    }//GEN-LAST:event_dashboardSearchBarPenjualanActionPerformed

    private void dashboardComboPenjualanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dashboardComboPenjualanItemStateChanged
        // TODO add your handling code here:
        loadDashboardWithFilterDivisionPage(20, 1);
    }//GEN-LAST:event_dashboardComboPenjualanItemStateChanged

    private void kButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton2MouseClicked
        // TODO add your handling code here:
        loadDashboardWithFilterDivisionPage(20, 1);
    }//GEN-LAST:event_kButton2MouseClicked

    private void tabelDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDashboardMouseClicked
        clickCount++;
        if (clickCount == 2) {
            if (prevRow == tabelDashboard.getSelectedRow()) {
                int baris = tabelDashboard.getSelectedRow();
                String id = tabelDashboard.getValueAt(baris, 0).toString().replaceAll(" - Baru", "");
                LihatPenjualan.setIdPenjualan(Integer.parseInt(id));
                if (!LihatPenjualan.isVisible()) {
                    LihatPenjualan.setVisible(true);
                }
            }
            clickCount = 0;
        } else {
            prevRow = tabelDashboard.getSelectedRow();
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clickCount = 0;
                }
            }, 500); // Waktu dalam milidetik untuk mereset clickCount jika tidak ada double-click berikutnya dalam 200ms
        }
    }//GEN-LAST:event_tabelDashboardMouseClicked

    private void rSMaterialButtonRectangle3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3MouseClicked
        if (!TambahPenjualan.isVisible()) {
            TambahPenjualan.setData(id_laundry);
            TambahPenjualan.setPanel(this);
            TambahPenjualan.setTable(tabelDashboard);
            TambahPenjualan.setVisible(true);
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle3MouseClicked

    private void rSMaterialButtonRectangle2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2MouseClicked
        // TODO add your handling code here:
        int baris = tabelDashboard.getSelectedRow();
        if (baris >= 0) {
            String id = tabelDashboard.getValueAt(baris, 0).toString().replaceAll(" - Baru", "");
            String nama = tabelDashboard.getValueAt(baris, 1).toString();
            String status = tabelDashboard.getValueAt(baris, (tabelDashboard.getColumnCount() - 1)).toString();
            int option = dialogControl.showWarningOption(parent, "Konfirmasi", "Yakin ingin menghapus Transaksi dengan\nID\t : " + id + "\nNama\t : " + nama + "\nStatus\t : " + status);
            if (option == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM penjualan WHERE penjualan.id_penjualan = ?";
                boolean isUpdate = queryControl.updateData(koneksi, query, id);
                if (isUpdate) {
                    tableControl.deleteTableRow(tabelDashboard, baris);
                    dialogControl.showSuccessMessage(parent, "Data Berhasil dihapus");
                }
            }
        } else {
            dialogControl.showWarningMessage(parent, "Harap Pilih Baris pada tabel yang ingin dihapus!!!");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle2MouseClicked

    private void rSMaterialButtonRectangle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1MouseClicked
        loadDashboardTrivia();
        loadDashboardWithNormalDivisionPage(20, 1);
    }//GEN-LAST:event_rSMaterialButtonRectangle1MouseClicked

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5MouseClicked
        // TODO add your handling code here:
        int baris = tabelDashboard.getSelectedRow();
        if (baris >= 0) {
            if (tabelDashboard.getValueAt(baris, 5).equals("belum dikerjakan")) {
                int isValids = dialogControl.showWarningOption(parent, "Konfirmasi", "Pesanan ini BELUM DIKERJAKAN!!!\nIngin tetap membayar meskipun pesanan BELUM DIKERJAKAN?");
                if (isValids == JOptionPane.YES_OPTION) {
                    String id = tabelDashboard.getValueAt(baris, 0).toString().replaceAll(" - Baru", "");
                    int harga = Integer.parseInt(tabelDashboard.getValueAt(baris, 2).toString());
                    Pembayaran.setData(Integer.parseInt(id), harga);
                    Pembayaran.setParent(parent);
                    Pembayaran.setTable(tabelDashboard, baris);
                    Pembayaran.showFrame();
                }
            } else if (tabelDashboard.getValueAt(baris, 5).equals("belum bayar")) {
                String id = tabelDashboard.getValueAt(baris, 0).toString().replaceAll(" - Baru", "");
                int harga = Integer.parseInt(tabelDashboard.getValueAt(baris, 2).toString());
                Pembayaran.setData(Integer.parseInt(id), harga);
                Pembayaran.setParent(parent);
                Pembayaran.setTable(tabelDashboard, baris);
                Pembayaran.showFrame();
            }
        } else {
            dialogControl.showWarningMessage(parent, "Harap Pilih Baris Dari Tabel!!!");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle5MouseClicked

    private void rSMaterialButtonRectangle6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle6MouseClicked
        // TODO add your handling code here:
        int baris = tabelDashboard.getSelectedRow();
        if (baris >= 0) {
            int last_column = tabelDashboard.getColumnCount() - 1;
            String id_penjualan = tabelDashboard.getValueAt(baris, 0).toString().replaceAll(" - Baru", "");
            String status = tabelDashboard.getValueAt(baris, last_column).toString();
            if (status.equals("belum dikerjakan")) {
                int option = dialogControl.showQuestionOption(parent, "Konfirmasi", "Ingin mengerjakan pesanan ini?");
                if (option == JOptionPane.YES_OPTION) {
                    String query = "update penjualan set penjualan.status = 'belum bayar' where penjualan.id_penjualan = ?";
                    boolean isUpdate = queryControl.updateData(koneksi, query, id_penjualan);
                    if (isUpdate) {
                        tabelDashboard.setValueAt("belum bayar", baris, last_column);
                        dialogControl.showSuccessMessage(parent, "Status pengerjaan berhasil diubah");
                    }
                }
            } else if (status.equals("belum bayar")) {
                int option = dialogControl.showQuestionOption(parent, "Konfirmasi", "Ingin mengubah status pesanan ini menjadi \"Belum Dikerjakan\"?");
                if (option == JOptionPane.YES_OPTION) {
                    String query = "update penjualan set penjualan.status = 'belum dikerjakan' where penjualan.id_penjualan = ?";
                    boolean isUpdate = queryControl.updateData(koneksi, query, id_penjualan);
                    if (isUpdate) {
                        tabelDashboard.setValueAt("belum dikerjakan", baris, last_column);
                        dialogControl.showSuccessMessage(parent, "Status pengerjaan berhasil diubah");
                    }
                }
            }
        } else {
            dialogControl.showWarningMessage(parent, "Harap Pilih Baris pada Tabel!!!");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle6MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void next_page_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_next_page_btnMouseClicked
        // TODO add your handling code here:
        int currentPage = Integer.parseInt(pageSelector.getSelectedItem().toString());
        loadSpecificPage(pageLoad.Next, currentPage);
    }//GEN-LAST:event_next_page_btnMouseClicked

    private void prev_page_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prev_page_btnMouseClicked
        // TODO add your handling code here:
        int currentPage = Integer.parseInt(pageSelector.getSelectedItem().toString());
        loadSpecificPage(pageLoad.Prev, currentPage);
    }//GEN-LAST:event_prev_page_btnMouseClicked

    private void pageSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pageSelectorItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_pageSelectorItemStateChanged

    private void pageSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageSelectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pageSelectorActionPerformed

    private void pageSelectorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pageSelectorMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_pageSelectorMouseClicked

    private void rSPanelImage3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSPanelImage3MouseClicked
        // TODO add your handling code here:
        System.out.println("table status " + currentTableMode);
    }//GEN-LAST:event_rSPanelImage3MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dashSeacrhGroup;
    private javax.swing.JPanel dashTableGroup;
    private javax.swing.JComboBox<String> dashboardComboPenjualan;
    private javax.swing.JTextField dashboardSearchBarPenjualan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jumlah_layanan_populer;
    private javax.swing.JLabel jumlah_parfum_populer;
    private com.k33ptoo.components.KButton kButton2;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private com.k33ptoo.components.KGradientPanel kGradientPanel4;
    private com.k33ptoo.components.KGradientPanel kGradientPanel5;
    private javax.swing.JLabel layanan_populer;
    private javax.swing.JButton next_page_btn;
    private javax.swing.JComboBox<String> pageSelector;
    private javax.swing.JLabel parfum_populer;
    private javax.swing.JLabel pendapatan_bulan_ini;
    private javax.swing.JLabel pendapatan_hari_ini;
    private javax.swing.JButton prev_page_btn;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle5;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle6;
    private rojerusan.RSPanelImage rSPanelImage3;
    private javax.swing.JTable tabelDashboard;
    // End of variables declaration//GEN-END:variables
}
