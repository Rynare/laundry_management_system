/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.Dialog;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Rynare
 */
public class dialogControl {

    public static int showQuestionOption( String title, String msg) {
        int option = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return option;
    }

    public static int showWarningOption( String title, String msg) {
        int option = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return option;
    }

    public static void showWarningMessage( String msg) {
        JOptionPane.showMessageDialog(null, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    public static void showSuccessMessage( String msg) {
        JOptionPane.showMessageDialog(null, msg, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void SQLFailedShow( SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal MEMUAT DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedUpdate( SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal MEMPERBARUI DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedDelete( SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal MENGHAPUS DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedInsert( SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal MENAMBAHKAN DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }
    
    public static int showQuestionOption(JFrame frame, String title, String msg) {
        int option = JOptionPane.showConfirmDialog(frame, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return option;
    }

    public static int showWarningOption(JFrame frame, String title, String msg) {
        int option = JOptionPane.showConfirmDialog(frame, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return option;
    }

    public static void showWarningMessage(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    public static void showSuccessMessage(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void SQLFailedShow(JFrame frame, SQLException e) {
        JOptionPane.showMessageDialog(frame, "Gagal MEMUAT DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedUpdate(JFrame frame, SQLException e) {
        JOptionPane.showMessageDialog(frame, "Gagal MEMPERBARUI DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedDelete(JFrame frame, SQLException e) {
        JOptionPane.showMessageDialog(frame, "Gagal MENGHAPUS DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedInsert(JFrame frame, SQLException e) {
        JOptionPane.showMessageDialog(frame, "Gagal MENAMBAHKAN DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }
    
    public static int showQuestionOption(Dialog parent, String title, String msg) {
        int option = JOptionPane.showConfirmDialog(parent, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return option;
    }

    public static int showWarningOption(Dialog parent, String title, String msg) {
        int option = JOptionPane.showConfirmDialog(parent, msg, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return option;
    }

    public static void showWarningMessage(Dialog parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

    public static void showSuccessMessage(Dialog parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void SQLFailedShow(Dialog parent, SQLException e) {
        JOptionPane.showMessageDialog(parent, "Gagal MEMUAT DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedUpdate(Dialog parent, SQLException e) {
        JOptionPane.showMessageDialog(parent, "Gagal MEMPERBARUI DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedDelete(Dialog parent, SQLException e) {
        JOptionPane.showMessageDialog(parent, "Gagal MENGHAPUS DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    public static void SQLFailedInsert(Dialog parent, SQLException e) {
        JOptionPane.showMessageDialog(parent, "Gagal MENAMBAHKAN DATA", "SQL Error!!!", JOptionPane.ERROR_MESSAGE);
    }
}
