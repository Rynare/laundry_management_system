/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundry.popup;

import Util.laundry_management_connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import laundry.pages.Beranda;
import tools.dialogControl;
import tools.queryControl;
import tools.tableControl;
import tools.tools;

/**
 *
 * @author Rynare
 */
public class tambahPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form tambahPenjualan1
     */
    
    // Koneksi

    laundry_management_connection koneksi = new laundry_management_connection();

// Table Control
    tableControl tableControl = new tableControl();

// popup

    private int id_laundry = -1;
    private int id_penjualan = 0;
    private JTable table;
    private Beranda panel;
    private boolean status = false;
    String[] updateValues = {"", "", "", "", "", "", ""};

    tambahPenjualanSelectLayanan selectLayananPopup = new tambahPenjualanSelectLayanan(this,true);
    tambahPenjualanSelectPelanggan selectPelangganPopup = new tambahPenjualanSelectPelanggan(this, true);
    TambahPenjualanTangggalSelesai tanggalSelesaiPopup = new TambahPenjualanTangggalSelesai(this, true);
    
    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setData(int id_laundry) {
        this.id_laundry = id_laundry;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setPanel(Beranda panel) {
        this.panel = panel;
    }

    public void updateBeranda() {
        try {
            ResultSet results = koneksi.getDataWithPreparedStatement("SELECT * FROM searchPenjualan WHERE searchPenjualan.ID_Penjualan = ?", id_penjualan);
            if (results.next()) {
                updateValues[1] = (results.getString(2));
                updateValues[2] = (results.getString(3));
                updateValues[3] = (results.getString(4));
                updateValues[4] = (results.getString(5));
                updateValues[5] = (results.getString(6));
            }
            if (status) {
                panel.updatePage(updateValues);
            }
        } catch (SQLException e) {
        }
    }

    public void clearAllValue() {
        nama_pelanggan_field.setText("");
        alamat_field.setText("");
        No_telp_field.setText("");
        check_pelanggan_baru.setSelected(false);
        layanan_Field.setText("");
        berat_field.setText("");
        harga_field.setText("");
        DefaultTableModel model = (DefaultTableModel) listPesananTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
        }
        catatan_text_area.setText("");
    }

    public void getIdPenjualan() {
        int _id_penjualan = 0;
        try {
            String getIdPenjualanQuery = "SELECT id_penjualan FROM penjualan ORDER BY id_penjualan DESC LIMIT 1;";
            ResultSet resultSet = koneksi.getData(getIdPenjualanQuery);
            if (resultSet.next()) {
                _id_penjualan = resultSet.getInt("id_penjualan") + 1;
            }
        } catch (SQLException ex) {
            System.out.println("Gagal mengambil ID penjualan: " + ex.getMessage());
        } finally {
            koneksi.closeConnection();
        }
        this.id_penjualan = _id_penjualan;
        updateValues[0] = Integer.toString(id_penjualan) + " - Baru";
    }

    public void loadComboBoxLayanan(JComboBox comboBox, String query) {
        try {
            ResultSet resultSet = koneksi.getData(query);

            // Mengisi ComboBox dengan data dari database
            while (resultSet.next()) {
                String item = resultSet.getString("");
                comboBox.addItem(item);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data dari database: " + e.getMessage());
        } finally {
            // Menutup koneksi ke database
            koneksi.closeConnection();
        }
    }

    public void insertDataToTable() {
        if (!(layanan_Field.getText().isEmpty() || berat_field.getText().isEmpty() || harga_field.getText().isEmpty())) {
            DefaultTableModel model = (DefaultTableModel) listPesananTable.getModel();
            model.addRow(new Object[]{layanan_Field.getText(), harga_field.getText(), berat_field.getText(), parfum_field.getSelectedItem()});

            layanan_Field.setText("");
            harga_field.setText("");
            berat_field.setText("");
            parfum_field.setSelectedIndex(0);
        } else {
            dialogControl.showWarningMessage(this, "Harap isi semua kolom input!!!");
            berat_field.requestFocus(true);
        }
    }

    public void deleteDataFromTable(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);
        }
    }

    public void loadParfumeData() {
        parfum_field.removeAllItems();
        try {
            ResultSet data = koneksi.getData("SELECT CONCAT(barang.id_barang, ' - ', barang.nama_barang) AS id_nama FROM barang WHERE status = 'tersedia'");
            while (data.next()) {
                String value = data.getString(1);
                parfum_field.addItem(value);
            }
        } catch (Exception e) {
        }
    }
    
    public static void closeFrame() {
        tambahPenjualan tambahPenjualan_frame = new tambahPenjualan();
        tambahPenjualan_frame.dispose();
    }

    public void setLayanan(int id, String nama_layanan, String harga) {
        layanan_Field.setText(id + " - " + nama_layanan);
        harga_field.setText(harga);
        berat_field.requestFocus(true);
    }

    public static void setPelanggan(int id, String nama_pelanggan, String no_telp, String alamat) {
        nama_pelanggan_field.setText(id + " - " + nama_pelanggan);
        No_telp_field.setText(no_telp);
        alamat_field.setText(alamat);
    }
    
    public tambahPenjualan() {
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
        jPanel1 = new javax.swing.JPanel();
        check_pelanggan_baru = new javax.swing.JCheckBox();
        No_telp_field = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        alamat_field = new javax.swing.JTextArea();
        nama_pelanggan_field = new javax.swing.JTextField();
        btn_edit_pelanggan = new com.k33ptoo.components.KButton();
        jPanel2 = new javax.swing.JPanel();
        berat_field = new javax.swing.JTextField();
        harga_field = new javax.swing.JTextField();
        parfum_field = new javax.swing.JComboBox<>();
        kButton6 = new com.k33ptoo.components.KButton();
        layanan_Field = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPesananTable = new javax.swing.JTable();
        kButton7 = new com.k33ptoo.components.KButton();
        kButton5 = new com.k33ptoo.components.KButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        catatan_text_area = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        kGradientPanel1.setBackground(new java.awt.Color(51, 51, 51));
        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(0, 51, 153));
        kGradientPanel1.setkFillBackground(false);
        kGradientPanel1.setkStartColor(new java.awt.Color(0, 204, 204));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pelanggan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("SansSerif", 3, 14), new java.awt.Color(255, 255, 51))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N

        check_pelanggan_baru.setBackground(new java.awt.Color(51, 51, 51));
        check_pelanggan_baru.setFont(new java.awt.Font("SansSerif", 3, 11)); // NOI18N
        check_pelanggan_baru.setForeground(new java.awt.Color(255, 255, 255));
        check_pelanggan_baru.setText("Baru");
        check_pelanggan_baru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check_pelanggan_baruMouseClicked(evt);
            }
        });
        check_pelanggan_baru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_pelanggan_baruActionPerformed(evt);
            }
        });

        No_telp_field.setEditable(false);
        No_telp_field.setBackground(new java.awt.Color(51, 51, 51));
        No_telp_field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        No_telp_field.setForeground(new java.awt.Color(255, 255, 255));
        No_telp_field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Nomer Telpon", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N
        No_telp_field.setCaretColor(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Alamat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N

        alamat_field.setEditable(false);
        alamat_field.setBackground(new java.awt.Color(51, 51, 51));
        alamat_field.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        alamat_field.setForeground(new java.awt.Color(255, 255, 255));
        alamat_field.setLineWrap(true);
        alamat_field.setWrapStyleWord(true);
        alamat_field.setBorder(null);
        alamat_field.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(alamat_field);

        nama_pelanggan_field.setEditable(false);
        nama_pelanggan_field.setBackground(new java.awt.Color(51, 51, 51));
        nama_pelanggan_field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        nama_pelanggan_field.setForeground(new java.awt.Color(255, 255, 255));
        nama_pelanggan_field.setText("      KLIK Disini");
        nama_pelanggan_field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Nama Pelanggan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N
        nama_pelanggan_field.setCaretColor(new java.awt.Color(255, 255, 255));
        nama_pelanggan_field.setDoubleBuffered(true);
        nama_pelanggan_field.setOpaque(false);
        nama_pelanggan_field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nama_pelanggan_fieldMouseClicked(evt);
            }
        });

        btn_edit_pelanggan.setText("Edit");
        btn_edit_pelanggan.setkEndColor(new java.awt.Color(0, 127, 127));
        btn_edit_pelanggan.setkHoverEndColor(new java.awt.Color(0, 127, 127));
        btn_edit_pelanggan.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btn_edit_pelanggan.setkHoverStartColor(new java.awt.Color(0, 127, 127));
        btn_edit_pelanggan.setkPressedColor(new java.awt.Color(0, 153, 153));
        btn_edit_pelanggan.setkSelectedColor(new java.awt.Color(0, 127, 127));
        btn_edit_pelanggan.setkStartColor(new java.awt.Color(0, 127, 127));
        btn_edit_pelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_edit_pelangganMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(No_telp_field)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(nama_pelanggan_field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(check_pelanggan_baru))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_edit_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(check_pelanggan_baru))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(nama_pelanggan_field, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(No_telp_field, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btn_edit_pelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pesanan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("SansSerif", 3, 14), new java.awt.Color(255, 255, 51))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        berat_field.setBackground(new java.awt.Color(51, 51, 51));
        berat_field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        berat_field.setForeground(new java.awt.Color(255, 255, 255));
        berat_field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Berat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N
        berat_field.setCaretColor(new java.awt.Color(255, 255, 255));

        harga_field.setEditable(false);
        harga_field.setBackground(new java.awt.Color(51, 51, 51));
        harga_field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        harga_field.setForeground(new java.awt.Color(255, 255, 255));
        harga_field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Harga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N
        harga_field.setCaretColor(new java.awt.Color(255, 255, 255));

        parfum_field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        parfum_field.setForeground(new java.awt.Color(255, 255, 255));
        parfum_field.setBorder(null);

        kButton6.setText("Tambahkan ke-tabel daftar pesanan");
        kButton6.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton6.setkEndColor(new java.awt.Color(24, 144, 255));
        kButton6.setkHoverEndColor(new java.awt.Color(24, 144, 255));
        kButton6.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton6.setkStartColor(new java.awt.Color(24, 144, 255));
        kButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton6MouseClicked(evt);
            }
        });

        layanan_Field.setEditable(false);
        layanan_Field.setBackground(new java.awt.Color(51, 51, 51));
        layanan_Field.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        layanan_Field.setForeground(new java.awt.Color(255, 255, 255));
        layanan_Field.setText("      KLIK Disini");
        layanan_Field.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.lightGray), "Layanan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 11), new java.awt.Color(0, 255, 204))); // NOI18N
        layanan_Field.setCaretColor(new java.awt.Color(255, 255, 255));
        layanan_Field.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                layanan_FieldMouseClicked(evt);
            }
        });
        layanan_Field.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                layanan_FieldInputMethodTextChanged(evt);
            }
        });
        layanan_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layanan_FieldActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(43, 43, 43));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Daftar Pesanan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 3, 12), new java.awt.Color(255, 255, 51))); // NOI18N
        jPanel3.setOpaque(false);

        listPesananTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Layanan", "Harga", "Berat", "Parfum"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(listPesananTable);

        kButton7.setText("Hapus");
        kButton7.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton7.setkEndColor(new java.awt.Color(255, 77, 79));
        kButton7.setkHoverEndColor(new java.awt.Color(255, 77, 79));
        kButton7.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton7.setkStartColor(new java.awt.Color(255, 77, 79));
        kButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton7MouseClicked(evt);
            }
        });

        kButton5.setText("Buat Pesanan");
        kButton5.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        kButton5.setkEndColor(new java.awt.Color(0, 153, 153));
        kButton5.setkHoverEndColor(new java.awt.Color(0, 153, 153));
        kButton5.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kButton5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(layanan_Field)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(harga_field, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(kButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(berat_field)
            .addComponent(parfum_field, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(layanan_Field, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(harga_field, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(berat_field, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(parfum_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(kButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("SansSerif", 3, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Catatan : ");

        catatan_text_area.setColumns(20);
        catatan_text_area.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        catatan_text_area.setLineWrap(true);
        catatan_text_area.setRows(5);
        jScrollPane3.setViewportView(catatan_text_area);

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void check_pelanggan_baruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check_pelanggan_baruMouseClicked
        // TODO add your handling code here:
        String nama = nama_pelanggan_field.getText();
        String alamat = alamat_field.getText();
        String no_telp = No_telp_field.getText();

        if (btn_edit_pelanggan.isVisible()) {
            btn_edit_pelanggan.setVisible(false);
            nama_pelanggan_field.setEditable(true);
            alamat_field.setEditable(true);
            No_telp_field.setEditable(true);
        } else if (!btn_edit_pelanggan.isVisible()) {
            btn_edit_pelanggan.setVisible(true);
            nama_pelanggan_field.setEditable(false);
            alamat_field.setEditable(false);
            No_telp_field.setEditable(false);
        }

        if (!(nama.isEmpty() || alamat.isEmpty() || no_telp.isEmpty())) {
            nama_pelanggan_field.setText("");
            alamat_field.setText("");
            No_telp_field.setText("");
        }
    }//GEN-LAST:event_check_pelanggan_baruMouseClicked

    private void check_pelanggan_baruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_pelanggan_baruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check_pelanggan_baruActionPerformed

    private void nama_pelanggan_fieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nama_pelanggan_fieldMouseClicked
        // TODO add your handling code here:
        if (!(check_pelanggan_baru.isSelected() || btn_edit_pelanggan.getText().equals("Simpan"))) {
            if (!selectPelangganPopup.isVisible()) {
                selectPelangganPopup.setVisible(true);
            }
        }
    }//GEN-LAST:event_nama_pelanggan_fieldMouseClicked

    private void btn_edit_pelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit_pelangganMouseClicked
        // TODO add your handling code here:
        String no_hp = No_telp_field.getText();
        String alamat = alamat_field.getText();
        String nama_pelanggan = nama_pelanggan_field.getText();
        if (!(no_hp.isEmpty() || alamat.isEmpty() || nama_pelanggan.isEmpty())) {
            String id_pelanggan = tools.cut(nama_pelanggan).getId();
            String query = "UPDATE pelanggan SET alamat = ?, no_telp = ? WHERE id_pelanggan = ?";
            Object[] value = {alamat, no_hp, id_pelanggan};

            if (btn_edit_pelanggan.getText().equals("Simpan")) {
                boolean isUpdate = queryControl.updateData(koneksi, query, value);
                if (isUpdate) {
                    alamat_field.setEditable(false);
                    No_telp_field.setEditable(false);
                    btn_edit_pelanggan.setText("Edit");
                }
            } else if (btn_edit_pelanggan.getText().equals("Edit")) {
                alamat_field.setEditable(true);
                No_telp_field.setEditable(true);
                btn_edit_pelanggan.setText("Simpan");
            }
        }
    }//GEN-LAST:event_btn_edit_pelangganMouseClicked

    private void kButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton6MouseClicked
        // TODO add your handling code here:
        insertDataToTable();
    }//GEN-LAST:event_kButton6MouseClicked

    private void layanan_FieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_layanan_FieldMouseClicked
        // TODO add your handling code here:
        if (!selectLayananPopup.isVisible()) {
            selectLayananPopup.setVisible(true);
            selectLayananPopup.setParent(this);
        }
    }//GEN-LAST:event_layanan_FieldMouseClicked

    private void layanan_FieldInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_layanan_FieldInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_layanan_FieldInputMethodTextChanged

    private void layanan_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layanan_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_layanan_FieldActionPerformed

    private void kButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton7MouseClicked
        // TODO add your handling code here:
        deleteDataFromTable(listPesananTable);
    }//GEN-LAST:event_kButton7MouseClicked

    private void kButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kButton5MouseClicked
        getIdPenjualan();

        //Data Pelanggan
        String id_pelanggan = null;
        String nama_pelanggan = nama_pelanggan_field.getText();
        String no_telpon = No_telp_field.getText();
        String alamat = alamat_field.getText();

        //Data Tanggal Penjualan
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nextDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal_selesai = dateFormat.format(nextDate);

        //Data Catatan Penjualan
        String catatan = catatan_text_area.getText();

        //Status
        boolean isSuccess = false;

        if (!(nama_pelanggan.isEmpty() || no_telpon.isEmpty() || alamat.isEmpty())) {
            if (listPesananTable.getRowCount() > 0) {
                if (check_pelanggan_baru.isSelected()) {
                    try {
                        String getIdPelangganQuery = "SELECT ID FROM searchpelanggan ORDER BY ID DESC LIMIT 1";
                        ResultSet resultSet = koneksi.getData(getIdPelangganQuery);
                        if (resultSet.next()) {
                            id_pelanggan = Integer.toString(Integer.parseInt(resultSet.getString("ID")) + 1);
                            String queryInsertPelanggan = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, no_telp, alamat) VALUES (?, ?, ?, ?)";
                            Object[] valueInsertPelanggan = {id_pelanggan, nama_pelanggan, no_telpon, alamat};
                            boolean isInsert = queryControl.insertData(koneksi, queryInsertPelanggan, valueInsertPelanggan);
                            if (isInsert) {
                                isSuccess = isInsert;
                                dialogControl.showSuccessMessage(this, "Data Pelanggan berhasil ditambah.");
                            }
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error : " + ex);
                    } finally {
                        koneksi.closeConnection();
                    }
                } else {
                    id_pelanggan = tools.cut(nama_pelanggan_field.getText()).getId();
                }

                if (id_pelanggan != null) {
                    String penjualanInsertQuery = "INSERT INTO penjualan (id_penjualan, id_pelanggan, tanggal_selesai, id_laundry) VALUES (?, ?, ?, ?)";
                    Object[] value = {id_penjualan, id_pelanggan, tanggal_selesai, id_laundry};
                    isSuccess = queryControl.insertData(koneksi, penjualanInsertQuery, value);

                    if (!catatan.isEmpty() && isSuccess) {
                        String updateCatatanQuery = "UPDATE penjualan SET penjualan.catatan = ? WHERE penjualan.id_penjualan = ?";
                        Object[] updateCatatanValue = {catatan, id_penjualan};
                        isSuccess = queryControl.updateData(koneksi, updateCatatanQuery, updateCatatanValue);
                    }

                    if (id_penjualan >= 0 && isSuccess) {
                        for (int i = 0; i < listPesananTable.getRowCount(); i++) {
                            String detailPenjualanInsertQuery = "CALL InsertDetailPenjualan(?, ?, ?, ?)";
                            int jumlah = Integer.valueOf(listPesananTable.getValueAt(i, 2).toString());
                            int id_barang = Integer.parseInt(tools.cut(listPesananTable.getValueAt(i, 3).toString()).getId());
                            int id_layanan = Integer.parseInt(tools.cut(listPesananTable.getValueAt(i, 0).toString()).getId());
                            Object[] valueDetail = {id_penjualan, jumlah, id_barang, id_layanan};
                            isSuccess = isSuccess && queryControl.insertData(koneksi, detailPenjualanInsertQuery, valueDetail);
                        }
                    }
                }
            } else {
                dialogControl.showWarningMessage(this, "Harap masukkan daftar layanan yang dipesan kedalam tabel!!!");
            }
        } else {
            dialogControl.showWarningMessage(this, "Data Pelanggan kosong");
        }

        System.out.println("status : " + isSuccess);

        if (isSuccess) {
            tanggalSelesaiPopup.id_penjualan = this.id_penjualan;
            if (!tanggalSelesaiPopup.isVisible()) {
                tanggalSelesaiPopup.setParent(this);
                tanggalSelesaiPopup.setVisible(true);
            }
        }
    }//GEN-LAST:event_kButton5MouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        loadParfumeData();
    }//GEN-LAST:event_formComponentShown

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
            java.util.logging.Logger.getLogger(tambahPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tambahPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tambahPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tambahPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tambahPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JTextField No_telp_field;
    private static javax.swing.JTextArea alamat_field;
    private javax.swing.JTextField berat_field;
    private com.k33ptoo.components.KButton btn_edit_pelanggan;
    private javax.swing.JTextArea catatan_text_area;
    private javax.swing.JCheckBox check_pelanggan_baru;
    private static javax.swing.JTextField harga_field;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.k33ptoo.components.KButton kButton5;
    private com.k33ptoo.components.KButton kButton6;
    private com.k33ptoo.components.KButton kButton7;
    private com.k33ptoo.components.KGradientPanel kGradientPanel1;
    private static javax.swing.JTextField layanan_Field;
    private javax.swing.JTable listPesananTable;
    private static javax.swing.JTextField nama_pelanggan_field;
    private javax.swing.JComboBox<String> parfum_field;
    // End of variables declaration//GEN-END:variables
}
