/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Scanner;

/**
 *
 * @author Rynare
 */
public class NewClass {

    public static void main(String[] args) {
        int data = 70;
        int data_page = 20;
        int jumlah_page = data % data_page == 0 ? data / data_page : Math.round(data / data_page + 1);
        int current_page = 0;
        Scanner input = new Scanner(System.in);
        int first_number_page = data_page * current_page - data_page + 1;
        int last_number_page = data_page * current_page;
        
        do {
            current_page = input.nextInt();
            String result = "";
            if (current_page > 0) {
                for (int index_data = first_number_page; index_data <= last_number_page && index_data <= data; index_data++) {
                    result += index_data + " ";
                }
                System.out.println(result);
            }
        } while (current_page > 0 && current_page < jumlah_page);
    }
}
