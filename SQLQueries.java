package cap_stone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.util.ArrayList;


/**
 *
 * Class that contains static methods that execute certain SQL queries.
 *
 * Please make sure that you create an SSH tunnel to the database before using any method.
 * Use the following command:
 * ssh -L 10000:cisdb.cis.uab.edu:5432 -N blazerid@lan.cis.uab.edu
 * 
 * For the Connection Strings, be sure to replace <password> with the proper password.
 *
 */
public class SQLQueries {


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Get the total wattage being used at the current moment
     */
    public static int selectTotalWattage() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result1 = connection.createStatement().executeQuery("SELECT ((SELECT SUM(wattage) FROM public.electronics) + " +
                "(SELECT (SUM(wattage)) FROM public.switchedelectronics WHERE ison = true)) " +
                "AS result;");
        ResultSet result2 = connection.createStatement().executeQuery("select * from public.lights where ison = true;");
        int i = 0;
        while (result2.next())
        {
            i++;
        }
        connection.close();
        result1.next();
        return result1.getInt(1) + (i * 60);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Essential queries related to the lights
     */
    public static ResultSet selectAllLights() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightMasterTopRight() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 1;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightMasterBottomRight() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 2;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightMasterMiddle() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 3;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightLivingCenter() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 4;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightKidsBottomRight() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 5;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightKidsBottomLeft() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 6;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightKidsMiddle() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 7;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightClosetLiving() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 8;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightBottomBathroom() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 9;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightLivingTopLeft() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 10;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightKitchen() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 11;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightTopBathRoom() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 12;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightClosetOffice() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 13;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightOfficeTop() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 14;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightOfficeBottom() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 15;");
        connection.close();
        return result;
    }

    public static ResultSet selectLightGarage() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.lights where id = 16;");
        connection.close();
        return result;
    }

    public static ResultSet switchLightMasterTopRight() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 1; SELECT * FROM public.lights");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightMasterBottomRight() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 2;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightMasterMiddle() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 3;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightLivingCenter() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 4;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightKidsBottomRight() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 5;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightKidsBottomLeft() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 6;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightKidsMiddle() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 7;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightClosetLiving() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 8;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightBottomBathroom() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 9;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightLivingTopLeft() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 10;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightKitchen() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 11;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightTopBathRoom() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 12;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightClosetOffice() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 13;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightOfficeTop() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 14;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightOfficeBottom() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 15;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchLightGarage() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.lights SET ison = NOT ison WHERE id = 16;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Essential queries related to the TVs
     */
    public static ResultSet selectLargerTV() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.switchedelectronics where id = 9;");
        connection.close();
        return result;
    }

    public static ResultSet selectSmallerTV() throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
        ResultSet result = connection.createStatement().executeQuery("select * from public.switchedelectronics where id = 10;");
        connection.close();
        return result;
    }

    public static ResultSet switchLargerTV() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.switchedelectronics SET ison = NOT ison WHERE id = 9;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }

    public static ResultSet switchSmallerTV() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "<password>");
            ResultSet result = connection.createStatement().executeQuery("UPDATE public.switchedelectronics SET ison = NOT ison WHERE id = 10;");
            connection.close();
            return result;
        }
        catch(Exception e) {
            return null;
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Return an ArrayList of the power usage for the last 4 weeks
     */
    public static ArrayList<Integer> get4WeekPowerUsage() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
            ResultSet result = connection.createStatement().executeQuery("select * from public.fourweekevaluation;");
            connection.close();
            ArrayList<Integer> list = new ArrayList<Integer>();
            while(result.next()) {
                int val = result.getInt("powerusage");
                list.add(val);
            }
            return list;
        }
        catch(Exception e) {
            return null;
        }
    }

    /*
        Return an ArrayList of the water usage for the last 4 weeks
     */
    public static ArrayList<Integer> get4WeekWaterUsage() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
            ResultSet result = connection.createStatement().executeQuery("select * from public.fourweekevaluation;");
            connection.close();
            ArrayList<Integer> list = new ArrayList<Integer>();
            while(result.next()) {
                int val = result.getInt("waterusage");
                list.add(val);
            }
            return list;
        }
        catch(Exception e) {
            return null;
        }
    }

    /*
        Return an ArrayList of the power usage for the last year
     */
    public static ArrayList<Integer> getYearlyPowerUsage() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
            ResultSet result = connection.createStatement().executeQuery("select * from public.yearlyevaluation;");
            connection.close();
            ArrayList<Integer> list = new ArrayList<Integer>();
            while(result.next()) {
                int val = result.getInt("powerusage");
                list.add(val);
            }
            return list;
        }
        catch(Exception e) {
            return null;
        }
    }

    /*
        Return an ArrayList of the water usage for the last year
     */
    public static ArrayList<Integer> getYearlyWaterUsage() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:10000/tiana18", "tiana18web", "TrafalgarLaw18");
            ResultSet result = connection.createStatement().executeQuery("select * from public.yearlyevaluation;");
            connection.close();
            ArrayList<Integer> list = new ArrayList<Integer>();
            while(result.next()) {
                int val = result.getInt("waterusage");
                list.add(val);
            }
            return list;
        }
        catch(Exception e) {
            return null;
        }
    }    

}
