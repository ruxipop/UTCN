package dataLayer;

import businessLayer.BaseProduct;
import businessLayer.MenuItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextStream {


    public static Date parseDate(String data) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(data);


        return date;
    }

    public static List<MenuItem> parseTextFile(String pathname) {
        List<MenuItem> dataList = null;
        try {
            Stream<String> stream = Files.lines(Paths.get(pathname));
            dataList = stream.map(TextStream::parseDataProduct).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private static MenuItem parseDataProduct(String data) {
        String[] parts = data.split(",");
        float c, f, p, s, pri, r;
        String title = parts[0].trim();
        String calories = parts[2].trim();
        String rating = parts[1].trim();
        String protein = parts[3].trim();
        String fat = parts[4].trim();
        String sodium = parts[5].trim();
        String price = parts[6].trim();
        c = Float.parseFloat(calories);
        f = Float.parseFloat(fat);
        p = Float.parseFloat(protein);
        s = Float.parseFloat(sodium);
        pri = Float.parseFloat(price);
        r = Float.parseFloat(rating);
        return new BaseProduct(title, r, c, p, f, s, pri);
    }


    public static List<String> parseTextEmployee(String pathname) {
        List<String> dataList = null;
        try {
            Stream<String> stream = Files.lines(Paths.get(pathname));
            dataList = stream.map(TextStream::parseDataEmployee).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;


    }

    private static String parseDataEmployee(String data) {
        String[] parts = data.split(",");
        String id = parts[0].trim();
        String password = parts[1].trim();
        String name = parts[2].trim();
        return id + " " + password + " " + name;
    }

    public static List<String> parseTextAngajat(String pathname) {
        List<String> dataList = null;
        try {
            Stream<String> stream = Files.lines(Paths.get(pathname));
            dataList = stream.map(TextStream::parseDataAngajat).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;


    }

    private static String parseDataAngajat(String data) {
        String[] parts = data.split(",");
        String id = parts[0].trim();
        String password = parts[1].trim();
        return id + " " + password;
    }

    public static boolean reprezentareDate(String t) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            format.parse(t);
            return true;
        } catch (ParseException e) {

            return false;
        }

    }

}
