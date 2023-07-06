/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;

/**
 *
 * @author Rynare
 */
public class browser {

    public static void openWhatsappWeb(String nomer_hp, String pesan) {
        Desktop browser = Desktop.getDesktop();
        // =====================================================================
        // Algoritma untuk merubah nomer hp dari 08...... menjadi +628.........
        // =====================================================================
        // 1. Hapus nol didepan
        String formattedInput = nomer_hp.replaceFirst("^0", "");
        // 2. Tambahkan +62 didepan nomor yang sudah hilang 0-nya
        String nomer_hp_final = "+62" + formattedInput;
        // =====================================================================
        // =====================================================================
        
        // =====================================================================
        // Algoritma untuk inputan yang mengandung enter(\n) menjadi valid link
        // =====================================================================
        String pesan_final = pesan.replaceAll("\n", "%0a");
        // =====================================================================
        // =====================================================================

        try {
            String link = "https://web.whatsapp.com/send?phone="
                    + nomer_hp_final + "&text="
                    + pesan_final;
            browser.browse(new URI(link));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Komputer anda tidak support", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }
}
