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
import java.util.ArrayList;
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

    public static ArrayList getList(JSONObject triangle) {
        ArrayList<Integer> list = new ArrayList();
        for (Iterator iterator = triangle.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            
            JSONArray coords = (JSONArray) triangle.get(key);
            Iterator<Integer> points = coords.iterator();
            
            while (points.hasNext()) {
                list.add(((Number) points.next()).intValue());
            }
        }
        
        return list;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser jp = new JSONParser();

        try (FileReader reader = new FileReader("triangles.json")) {
            Object obj = jp.parse(reader);

            JSONObject figures = (JSONObject) obj;
            JSONObject triangle1 = (JSONObject) figures.get("triangle1");
            JSONObject triangle2 = (JSONObject) figures.get("triangle2");

            ArrayList<Long> t1 = getList(triangle1);

            ArrayList<Long> t2 = getList(triangle2);
            
            System.out.println(t1);
            System.out.println(t2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
