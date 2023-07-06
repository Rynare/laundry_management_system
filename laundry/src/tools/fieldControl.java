/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import javax.swing.JTextField;

/**
 *
 * @author Rynare
 */
public class fieldControl {

    public static String onlyNumber(JTextField textField) {
        String input = textField.getText();
        return input.replaceAll("[^0-9]", "");
    }
}
