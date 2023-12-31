/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.pages;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import laundry.frames.MainContent;
import tools.comboBoxControl;
import tools.dialogControl;
import tools.queryControl;
import tools.tableControl;
import tools.tableControl.TableUpdateMode;

/**
 *
 * @author Rynare
 */
public class Layanan extends javax.swing.JPanel {

    /**
     * Creates new form layanan
     */
    laundry_management_connection koneksiDB = new laundry_management_connection();
    tableControl tableControl = new tableControl();

    private int id_laundry;
    private MainContent parent;
    private ResultSet rs;

    public void setParent(MainContent parent) {
        this.parent = parent;
    }

    public void setID(int id_laundry) {
        this.id_laundry = id_laundry;
    }

    public void render(TableUpdateMode mode) {
        loadTableLayanan("Select * from searchLayanan", mode);
    }

    public void loadTableLayanan(String query, TableUpdateMode mode, Object... value) {
        tableControl.loadTableFromDB(tabelLayanan, mode, query, value);
    }

    public void loadTableLayananFromFilter() {
        String query = "SELECT * FROM searchLayanan";
        String userInput = searchBarLayanan.getText();
        String tipeKondisi = comboBoxControl.getComboBoxValue(comboBox_Layanan);
        String kondisi = "";

        if (!userInput.isEmpty()) {
            if (tipeKondisi.equals("Harga")) {
                kondisi += " WHERE searchLayanan.harga = " + userInput;
            } else if (tipeKondisi.equals("Nama Layanan")) {
                kondisi += " WHERE searchLayanan.`Nama Layanan` LIKE '%" + userInput + "%'";
            }

            try {
                rs = koneksiDB.getData(query + kondisi);
                loadTableLayanan(query + kondisi, TableUpdateMode.ALWAYS_UPDATE);
            } catch (SQLException ex) {
                System.out.println("Gagal menjalankan query: " + ex.getMessage());
            } finally {
                koneksiDB.closeConnection();
            }
        } else {
            tableControl.loadTableFromDB(tabelLayanan, TableUpdateMode.ALWAYS_UPDATE, "SELECT * FROM searchLayanan");
        }
    }

    public Layanan() {
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

        kGradientPanel9 = new com.k33ptoo.components.KGradientPanel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        insert_nama_layanan_field = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        insert_harga_layanan_field = new javax.swing.JTextField();
        kButton16 = new com.k33ptoo.components.KButton();
        kGradientPanel10 = new com.k33ptoo.components.KGradientPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelLayanan = new javax.swing.JTable();
        kGradientPanel8 = new com.k33ptoo.components.KGradientPanel();
        kButton13 = new com.k33ptoo.components.KButton();
        jPanel4 = new javax.swing.JPanel();
        rSPanelImage7 = new rojerusan.RSPanelImage();
        searchBarLayanan = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        comboBox_Layanan = new javax.swing.JComboBox<>();
        kButton28 = new com.k33ptoo.components.KButton();
        kButton17 = new com.k33ptoo.components.KButton();
        kGradientPanel14 = new com.k33ptoo.components.KGradientPanel();
        jLabel36 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        ID_layanan_field = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        nama_layanan_field = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        harga_layanan_field = new javax.swing.JTextField();
        kButton24 = new com.k33ptoo.components.KButton();

        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        kGradientPanel9.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel9.setkBorderRadius(20);
        kGradientPanel9.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel9.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel9.setOpaque(false);

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Tambah Data Layanan");

        jLabel28.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Nama Layanan");

        jLabel29.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Harga");

        kButton16.setBorder(null);
        kButton16.setText("Tambah Layanan");
        kButton16.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton16.setkEndColor(new java.awt.Color(0, 153, 153));
        kButton16.setkHoverEndColor(new java.awt.Color(0, 153, 153));
        kButton16.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton16MouseClicked(evt);
            }
        });
        kButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel9Layout = new javax.swing.GroupLayout(kGradientPanel9);
        kGradientPanel9.setLayout(kGradientPanel9Layout);
        kGradientPanel9Layout.setHorizontalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10)
                    .addGroup(kGradientPanel9Layout.createSequentialGroup()
                        .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29))
                        .addGap(16, 16, 16)
                        .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kButton16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                            .addComponent(insert_harga_layanan_field)
                            .addComponent(insert_nama_layanan_field))))
                .addContainerGap())
        );
        kGradientPanel9Layout.setVerticalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insert_nama_layanan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(insert_harga_layanan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        kGradientPanel10.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel10.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel10.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel10.setOpaque(false);

        tabelLayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelLayanan.setRowHeight(24);
        tabelLayanan.setShowHorizontalLines(false);
        tabelLayanan.setShowVerticalLines(false);
        tabelLayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelLayananMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelLayanan);

        kGradientPanel8.setBackground(new java.awt.Color(30, 39, 66));
        kGradientPanel8.setkBorderRadius(20);
        kGradientPanel8.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel8.setkStartColor(new java.awt.Color(30, 39, 66));

        kButton13.setBorder(null);
        kButton13.setText("Cari Layanan");
        kButton13.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton13.setkEndColor(new java.awt.Color(0, 153, 153));
        kButton13.setkHoverEndColor(new java.awt.Color(0, 153, 153));
        kButton13.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton13MouseClicked(evt);
            }
        });
        kButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton13ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(30, 39, 66));

        rSPanelImage7.setImagen(new javax.swing.ImageIcon(getClass().getResource("/laundry/assets/24px/icons8_search_24px.png"))); // NOI18N
        rSPanelImage7.setPreferredSize(new java.awt.Dimension(24, 24));

        javax.swing.GroupLayout rSPanelImage7Layout = new javax.swing.GroupLayout(rSPanelImage7);
        rSPanelImage7.setLayout(rSPanelImage7Layout);
        rSPanelImage7Layout.setHorizontalGroup(
            rSPanelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        rSPanelImage7Layout.setVerticalGroup(
            rSPanelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        searchBarLayanan.setBackground(new java.awt.Color(30, 39, 66));
        searchBarLayanan.setForeground(new java.awt.Color(255, 255, 255));
        searchBarLayanan.setBorder(null);
        searchBarLayanan.setOpaque(false);
        searchBarLayanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarLayananActionPerformed(evt);
            }
        });

        comboBox_Layanan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        comboBox_Layanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nama Layanan", "Harga" }));
        comboBox_Layanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBox_LayananItemStateChanged(evt);
            }
        });
        comboBox_Layanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox_LayananActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rSPanelImage7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBarLayanan))
                    .addComponent(jSeparator6))
                .addGap(10, 10, 10)
                .addComponent(comboBox_Layanan, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(rSPanelImage7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(searchBarLayanan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                    .addComponent(comboBox_Layanan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout kGradientPanel8Layout = new javax.swing.GroupLayout(kGradientPanel8);
        kGradientPanel8.setLayout(kGradientPanel8Layout);
        kGradientPanel8Layout.setHorizontalGroup(
            kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        kGradientPanel8Layout.setVerticalGroup(
            kGradientPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        kButton28.setBorder(null);
        kButton28.setText("Hapus Data");
        kButton28.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton28.setkEndColor(new java.awt.Color(231, 64, 51));
        kButton28.setkHoverEndColor(new java.awt.Color(231, 64, 51));
        kButton28.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton28.setkStartColor(new java.awt.Color(231, 64, 51));
        kButton28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton28MouseClicked(evt);
            }
        });
        kButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton28ActionPerformed(evt);
            }
        });

        kButton17.setBorder(null);
        kButton17.setText("Refresh Table");
        kButton17.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton17.setkEndColor(new java.awt.Color(0, 153, 229));
        kButton17.setkHoverEndColor(new java.awt.Color(0, 153, 229));
        kButton17.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton17.setkHoverStartColor(new java.awt.Color(0, 118, 249));
        kButton17.setkStartColor(new java.awt.Color(0, 153, 229));
        kButton17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel10Layout = new javax.swing.GroupLayout(kGradientPanel10);
        kGradientPanel10.setLayout(kGradientPanel10Layout);
        kGradientPanel10Layout.setHorizontalGroup(
            kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(kGradientPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kButton28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(kGradientPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        kGradientPanel10Layout.setVerticalGroup(
            kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(kGradientPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(kButton28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        kGradientPanel14.setBackground(new java.awt.Color(102, 102, 102));
        kGradientPanel14.setkBorderRadius(20);
        kGradientPanel14.setkEndColor(new java.awt.Color(30, 39, 66));
        kGradientPanel14.setkStartColor(new java.awt.Color(30, 39, 66));
        kGradientPanel14.setOpaque(false);

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Ubah Data Layanan");

        ID_layanan_field.setEditable(false);

        jLabel37.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("ID Layanan");

        jLabel38.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Nama Layanan");

        jLabel39.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Harga");

        kButton24.setBorder(null);
        kButton24.setText("Simpan");
        kButton24.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton24.setkEndColor(new java.awt.Color(0, 153, 153));
        kButton24.setkHoverEndColor(new java.awt.Color(0, 153, 153));
        kButton24.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton24MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel14Layout = new javax.swing.GroupLayout(kGradientPanel14);
        kGradientPanel14.setLayout(kGradientPanel14Layout);
        kGradientPanel14Layout.setHorizontalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator16)
                    .addGroup(kGradientPanel14Layout.createSequentialGroup()
                        .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(16, 16, 16)
                        .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(harga_layanan_field)
                            .addComponent(ID_layanan_field)
                            .addComponent(nama_layanan_field)
                            .addComponent(kButton24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        kGradientPanel14Layout.setVerticalGroup(
            kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel14Layout.createSequentialGroup()
                        .addComponent(ID_layanan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nama_layanan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kGradientPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(harga_layanan_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)))
                    .addComponent(jLabel37))
                .addGap(10, 10, 10)
                .addComponent(kButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(kGradientPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kGradientPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(kGradientPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kGradientPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(kGradientPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void kButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton16MouseClicked
        // TODO add your handling code here:
        String id_layanan = "";
        String nama_layanan = insert_nama_layanan_field.getText();
        String harga_layanan = insert_harga_layanan_field.getText();

        if (!(nama_layanan.isEmpty() || harga_layanan.isEmpty())) {
            try {
                String getIdLayananQuery = "SELECT ID FROM searchLayanan ORDER BY ID DESC LIMIT 1";
                ResultSet resultSet = koneksiDB.getData(getIdLayananQuery);
                if (resultSet.next()) {
                    id_layanan = Integer.toString(Integer.parseInt(resultSet.getString("ID")) + 1);
                }
                String query = "INSERT INTO layanan (id_layanan, nama_layanan, harga_layanan) VALUES (?, ?, ?)";
                Object[] value = {id_layanan, nama_layanan, harga_layanan};
                boolean isInsert = queryControl.updateData(koneksiDB, query, value);
                if (isInsert) {
                    insert_nama_layanan_field.setText("");
                    insert_harga_layanan_field.setText("");
                    loadTableLayanan("SELECT * FROM searchLayanan", TableUpdateMode.ALWAYS_UPDATE);
                    JOptionPane.showMessageDialog(parent, "Layanan Baru Berhasil ditambahkan", "Yeayyy", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                System.out.println("Gagal mengambil ID layanan: " + ex.getMessage());
            } finally {
                koneksiDB.closeConnection();
            }
        } else {
            dialogControl.showWarningMessage(parent, "Harap isi semua kolom!!!");
        }
    }//GEN-LAST:event_kButton16MouseClicked

    private void kButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton16ActionPerformed

    private void tabelLayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelLayananMouseClicked
        // TODO add your handling code here:
        int row = tabelLayanan.rowAtPoint(evt.getPoint());

        String id_layanan_value = tabelLayanan.getValueAt(row, 0).toString();
        String nama_layanan_value = tabelLayanan.getValueAt(row, 1).toString();
        String harga_layanan_value = tabelLayanan.getValueAt(row, 2).toString();

        ID_layanan_field.setText(id_layanan_value);
        nama_layanan_field.setText(nama_layanan_value);
        harga_layanan_field.setText(harga_layanan_value);
    }//GEN-LAST:event_tabelLayananMouseClicked

    private void kButton13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton13MouseClicked
        // TODO add your handling code here:
        loadTableLayananFromFilter();
    }//GEN-LAST:event_kButton13MouseClicked

    private void kButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton13ActionPerformed

    private void searchBarLayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarLayananActionPerformed
        // TODO add your handling code here:
        loadTableLayananFromFilter();
    }//GEN-LAST:event_searchBarLayananActionPerformed

    private void comboBox_LayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBox_LayananItemStateChanged
        // TODO add your handling code here:
        loadTableLayananFromFilter();
    }//GEN-LAST:event_comboBox_LayananItemStateChanged

    private void comboBox_LayananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox_LayananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBox_LayananActionPerformed

    private void kButton28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton28MouseClicked
        // TODO add your handling code here:
        int baris = tabelLayanan.getSelectedRow();
        if (baris >= 0) {
            String nama_layanan = tabelLayanan.getValueAt(baris, 1).toString();
            int option = dialogControl.showWarningOption(parent, "Konfirmasi", "Yakin ingin menghapus \"" + nama_layanan + "\"");
            if (option == JOptionPane.YES_OPTION) {
                int id = (int) tabelLayanan.getValueAt(baris, 0);
                String query = "DELETE FROM layanan WHERE layanan.id_layanan = ?";
                boolean isDelete = queryControl.deleteData(koneksiDB, query, id);
                if (isDelete) {
                    tableControl.deleteTableRow(tabelLayanan,baris);
                }
            }
        } else {
            dialogControl.showWarningMessage(parent, "Harap pilih layanan yang ingin dihapus!!!");
        }
    }//GEN-LAST:event_kButton28MouseClicked

    private void kButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton28ActionPerformed

    private void kButton17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton17MouseClicked
        // TODO add your handling code here:
        loadTableLayanan("Select * from searchLayanan", TableUpdateMode.ALWAYS_UPDATE);
    }//GEN-LAST:event_kButton17MouseClicked

    private void kButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton24MouseClicked
        // TODO add your handling code here:
        String id = ID_layanan_field.getText();
        String nama_layanan = nama_layanan_field.getText();
        String harga = harga_layanan_field.getText();

        if (!(id.isEmpty() || nama_layanan.isEmpty() || harga.isEmpty())) {
            int option = dialogControl.showQuestionOption(parent, "Konfirmasi", "Ingin Menyimpan Perubahan Data Layanan?");
            if (option == JOptionPane.YES_OPTION) {
                String query = "UPDATE layanan SET layanan.nama_layanan = ?, layanan.harga_layanan = ? WHERE layanan.id_layanan = ?";
                Object[] value = {nama_layanan, harga, id};
                boolean isUpdate = queryControl.updateData(koneksiDB, query, value);
                if (isUpdate) {
                    dialogControl.showSuccessMessage(parent, "Data Layanan berhasil diperbarui.");
                }
            }
        } else {
            dialogControl.showWarningMessage(parent, id);
        }
    }//GEN-LAST:event_kButton24MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:

    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ID_layanan_field;
    private javax.swing.JComboBox<String> comboBox_Layanan;
    private javax.swing.JTextField harga_layanan_field;
    private javax.swing.JTextField insert_harga_layanan_field;
    private javax.swing.JTextField insert_nama_layanan_field;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator6;
    private com.k33ptoo.components.KButton kButton13;
    private com.k33ptoo.components.KButton kButton16;
    private com.k33ptoo.components.KButton kButton17;
    private com.k33ptoo.components.KButton kButton24;
    private com.k33ptoo.components.KButton kButton28;
    private com.k33ptoo.components.KGradientPanel kGradientPanel10;
    private com.k33ptoo.components.KGradientPanel kGradientPanel14;
    private com.k33ptoo.components.KGradientPanel kGradientPanel8;
    private com.k33ptoo.components.KGradientPanel kGradientPanel9;
    private javax.swing.JTextField nama_layanan_field;
    private rojerusan.RSPanelImage rSPanelImage7;
    private javax.swing.JTextField searchBarLayanan;
    private javax.swing.JTable tabelLayanan;
    // End of variables declaration//GEN-END:variables
}
