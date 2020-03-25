/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author dollar
 */
public class Task1 {

    /**
     * @param args the command line arguments
     */
    public static ArrayList fileReader(String path) throws IOException {
        ArrayList<Integer> list = new ArrayList();
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNextInt()) {
                list.add(sc.nextInt());
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return list;
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ArrayList<Integer> list = fileReader(args[0]);
        Collections.sort(list);
        int middle = (int) Math.round(list.size() / 2.0);
        int perc = (int) Math.round(90.0 / 100.0 * list.size());
//        int sum = list.stream().mapToInt(Integer::intValue).sum();
        int sum = 0;
        for (int i = middle; i < perc; i++) {
            sum += list.get(i);
        }
        System.out.println(sum);
    }

}
