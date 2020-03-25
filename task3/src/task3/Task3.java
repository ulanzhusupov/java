/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Work-2
 */
public class Task3 {

    public static int waterVolume = 0, // Объем воды
            boxVolume = 0, // Объем бочки
            errorCount = 0, // Кол-во ошибок
            operationCount = 0; // Кол-во операций

    public static int countPourTry = 0, // Кол-во попыток налить воду
            waterVolumePoured = 0, // Объем налитой воды
            waterVolumeNotPoured = 0; // Объем не налитой воды

    public static int countScoopTry = 0, // Кол-во попыток слить воду
            waterVolumeScooped = 0, // Объем сливанутой воды
            waterVolumeNotScooped = 0; // Объем несливанутой воды

    public static int waterVolumeOut = waterVolume;

    public static void operationInInterval(String[] arr, Date date, String from, String to) throws ParseException {
        Date dateFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(from);
        Date dateTo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(to);

        if ((date.compareTo(dateFrom) == 0 || date.compareTo(dateFrom) == 1) && (date.compareTo(dateTo) == 0 || date.compareTo(dateTo) < 0)) {
            operationCount++;
            if (arr[5].contains("pour")) {
                countPourTry++;
                int volume = Integer.parseInt(arr[arr.length - 1].replaceAll("\\D+", ""));
                if (waterVolumeOut + volume <= boxVolume) {
                    waterVolumeOut += volume;
                    waterVolumePoured += volume;
                } else {
                    errorCount++;
                    waterVolumeNotPoured += volume;
                }
            }

            if (arr[5].contains("scoop")) {
                countScoopTry++;
                int volume = Integer.parseInt(arr[arr.length - 1].replaceAll("\\D+", ""));
                if (waterVolumeOut - volume >= 0) {
                    waterVolumeOut -= volume;
                    waterVolumeScooped -= volume;
                } else {
                    errorCount++;
                    waterVolumeNotScooped += volume;
                }
            }
        }
    }

    public static void fileReader(String path, String from, String to) throws IOException, ParseException {

        try {
            Scanner sc = new Scanner(new File(path));
            int indexOfLine = 0;
            while (sc.hasNextLine()) {
                if (indexOfLine == 0) {
                    boxVolume = sc.nextInt(); // находимся на объему бочки
                    indexOfLine++;
                } else if (indexOfLine == 1) {
                    waterVolume = sc.nextInt(); // находимся на объеме воды в бочке
                    indexOfLine++; //уже неважный
                } else {
                    String[] arr = sc.nextLine().split(" ");
                    try {
                        Date currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(arr[0]);
                        operationInInterval(arr, currentDate, from, to);

                    } catch (ParseException e) {
                        System.out.println("Ошибка парсинга: " + e.getLocalizedMessage());
                    }
                }

            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }


    }

    public static void main(String[] args) throws IOException {
        try {
            // TODO code application logic here
            fileReader(args[0], args[1], args[2]);
        } catch (ParseException ex) {
            Logger.getLogger(Task3.class.getName()).log(Level.SEVERE, null, ex);
        }

        double errorPercent = (double) errorCount / operationCount * 100; // Процент ошибок

        List<String> rows = Arrays.asList(countPourTry + "", waterVolumePoured + "", waterVolumeNotPoured + "",
                countScoopTry + "", waterVolumeScooped + "", waterVolumeNotScooped + "",
                operationCount + "", errorPercent + "", errorCount + ""
        );

        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("Pour tried");
        csvWriter.append(" | ");
        csvWriter.append("Water vol poured");
        csvWriter.append(" | ");
        csvWriter.append("Water vol not poured");
        csvWriter.append(" | ");
        csvWriter.append("Scoop tried");
        csvWriter.append(" | ");
        csvWriter.append("Water vol scooped");
        csvWriter.append(" | ");
        csvWriter.append("Water vol not scooped");
        csvWriter.append(" | ");
        csvWriter.append("Operation count");
        csvWriter.append(" | ");
        csvWriter.append("Error percent");
        csvWriter.append(" | ");
        csvWriter.append("Error count");
        csvWriter.append("\n");

        csvWriter.append(String.join(" | ", rows));
        csvWriter.append("\n");

        csvWriter.flush();
        csvWriter.close();
    }

}
