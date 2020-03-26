/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.task2;

//import jdk.nashorn.internal.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Work-2
 */
public class Task2 {


    public static int[][] parsePointsOfTriangle(JSONObject triangle) {
        int[][] pointsOfTriangle = new int[3][3];
        int i = 0;
        for (Iterator iterator = triangle.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();

            JSONArray coords = (JSONArray) triangle.get(key);
            Iterator<Integer> points = coords.iterator();
            int j = 0;
            while (points.hasNext()) {
                pointsOfTriangle[i][j] = ((Number) points.next()).intValue();
                j++;
            }
            i++;
        }
        return pointsOfTriangle;
    }

    public static double[] getSides(int[][] arr) {
        int x1 = arr[0][0], y1 = arr[0][1], z1 = arr[0][2],
                x2 = arr[1][0], y2 = arr[1][1], z2 = arr[1][2],
                x3 = arr[2][0], y3 = arr[2][1], z3 = arr[2][2];

        double[] sides = new double[3];

        sides[0] = Math.round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2)));
        sides[1] = Math.round(Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2) + Math.pow(z3 - z2, 2)));
        sides[2] = Math.round(Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2) + Math.pow(z1 - z3, 2)));
        return sides;
    }

    public static int isTriangleSimilarByAAA(double[] first, double[] second) {
        Arrays.sort(first);
        Arrays.sort(second);

        // Check for AAA 
        if (first[0] == second[0]
                && first[1] == second[1]
                && first[2] == second[2]) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int isTriangleSimilarBySAS(double[] sides1, double[] sides2,
            double[] angle1, double[] angle2) {
        Arrays.sort(sides1);
        Arrays.sort(sides2);
        Arrays.sort(angle1);
        Arrays.sort(angle2);

        // Check for SAS 
        // angle b / w two smallest 
        // sides is largest. 
        if (sides1[0] / sides2[0] == sides1[1] / sides2[1]) {
            // since we take angle 
            // b / w the sides. 
            if (angle1[2] == angle2[2]) {
                return 1;
            }
        }
        if (sides1[1] / sides2[1] == sides1[2] / sides2[2]) {
            if (angle1[0] == angle2[0]) {
                return 1;
            }
        }
        if (sides1[2] / sides2[2] == sides1[0] / sides2[0]) {
            if (angle1[1] == angle2[1]) {
                return 1;
            }
        }
        return 0;
    }

    public static int isTriangleSimilarBySSS(double[] side1, double[] side2) {
        Arrays.sort(side1);
        Arrays.sort(side2);

        // Check for SSS 
        if (side1[0] / side2[0] == side1[1] / side2[1]
                && side1[1] / side2[1] == side1[2] / side2[2]
                && side1[2] / side2[2] == side1[0] / side2[0]) {
            return 1;
        }

        return 0;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws ParseException {
        JSONParser jp = new JSONParser();

        try (FileReader reader = new FileReader(args[0])) {
            Object obj = jp.parse(reader);

            JSONObject figures = (JSONObject) obj;
            JSONObject triangle1 = (JSONObject) figures.get("triangle1");
            JSONObject triangle2 = (JSONObject) figures.get("triangle2");

            // парсим JSONObject и возвращаем треугольник как массив массивов заданным
            int[][] pointsOfTriangle1 = parsePointsOfTriangle(triangle1);
            int[][] pointsOfTriangle2 = parsePointsOfTriangle(triangle2);

            double[] firstSides = getSides(pointsOfTriangle1);
            // ниже находим углы треугольника по заданным сторонам firstSides
            double[] firstAngles = new double[3];
            firstAngles[0] = Math.pow(firstSides[0], 2) + Math.pow(firstSides[2], 2) - Math.pow(firstSides[1], 2) / (2 * firstSides[0] * firstSides[2]);
            firstAngles[1] = Math.pow(firstSides[0], 2) + Math.pow(firstSides[1], 2) - Math.pow(firstSides[2], 2) / (2 * firstSides[0] * firstSides[1]);
            firstAngles[2] = Math.pow(firstSides[1], 2) + Math.pow(firstSides[2], 2) - Math.pow(firstSides[0], 2) / (2 * firstSides[2] * firstSides[1]);

            double[] secondSides = getSides(pointsOfTriangle2);
            // ниже находим углы треугольника по заданным сторонам secondSides
            double[] secondAngles = new double[3];
            secondAngles[0] = Math.floor(Math.pow(secondSides[0], 2) + Math.pow(secondSides[2], 2) - Math.pow(secondSides[1], 2) / (2 * secondSides[0] * secondSides[2]));
            secondAngles[1] = Math.floor(Math.pow(secondSides[0], 2) + Math.pow(secondSides[1], 2) - Math.pow(secondSides[2], 2) / (2 * secondSides[0] * secondSides[1]));
            secondAngles[2] = Math.floor(Math.pow(secondSides[1], 2) + Math.pow(secondSides[2], 2) - Math.pow(secondSides[0], 2) / (2 * secondSides[2] * secondSides[1]));

            int simAAA = isTriangleSimilarByAAA(firstAngles, secondAngles);
            int simSAS = isTriangleSimilarBySAS(firstSides, secondSides, firstAngles, secondAngles);
            int simSSS = isTriangleSimilarBySSS(firstSides, secondSides);
            
            if(simAAA == 1 || simSAS == 1 || simSSS == 1)
                System.out.println("Треугольники подобны");
            else
                System.out.println("Треугольники не подобны");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
