/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4;

/**
 *
 * @author Work-2
 */
public class Task4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args[0] != null && args[1] != null) {
            if(args[1].contains("*")) {
                System.out.println("OK");
            } else if(args[0].equals(args[1])) {
                System.out.println("OK");
            } else {
                System.out.println("KO");
            }
        } else {
            System.out.println("Дайте мне аргументов");
        }
    }
    
}
