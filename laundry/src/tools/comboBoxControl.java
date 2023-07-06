/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

;

import javax.swing.JComboBox;

/**
 *
 * @author Rynare
 */


public class comboBoxControl {

    public static String getComboBoxValue(JComboBox comboBox) {
        Object selectedValue = comboBox.getSelectedItem();
        String value = selectedValue.toString();
        return value;
    }
}
