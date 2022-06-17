package Utill;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;



public class Util {


    public static String UID;

    public static boolean end=true;
    public static boolean endone=true;

    public static int computerUsageCount(String id) throws SQLException, ClassNotFoundException {
        int count = 0;

        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(Computer_ID) FROM Reserve WHERE  Computer_ID=?",id);

        while (resultSet.next()){
            count=resultSet.getInt(1);
        }

        return count;
    }


    public static int packageUsageCount(String id) throws SQLException, ClassNotFoundException {
        int count=0;

        ResultSet resultSet=CrudUtil.execute("SELECT  COUNT(Package_ID) FROM Package_Details WHERE Package_ID=?",id);

        while (resultSet.next()){
            count=resultSet.getInt(1);
        }


        return count;
    }


    public static double expense(String month) throws SQLException, ClassNotFoundException {

        double totalExpense=0;

        ResultSet resultSet=CrudUtil.execute("SELECT Suply_Cost FROM Suply WHERE Suply_Date_Time like '"+month+"%'");

        while (resultSet.next()){
            totalExpense=totalExpense+ resultSet.getDouble(1);
        }





        return totalExpense;
    }

        public static double yearlyincome(String id) throws SQLException, ClassNotFoundException {
        double income=0;

        ResultSet resultSet =CrudUtil.execute("SELECT  income FROM Package_Details WHERE Customer_ID=?",id);

        while (resultSet.next()){
            income=income+resultSet.getDouble(1);
        }

        return income;
    }

    public static double CuIds(String month) throws SQLException, ClassNotFoundException {
       ArrayList CUID = new ArrayList<>();

       ResultSet resultSet=CrudUtil.execute("SELECT Customer_ID FROM reserve where date_time Like '"+month+"%'");

       while (resultSet.next()){
           CUID.add(resultSet.getString(1));


       }

       double totalIncome= 0 ;
       for (int i = 0; i < CUID.size() ; i++) {
            ResultSet incm = CrudUtil.execute("SELECT  income FROM Package_Details WHERE Customer_ID=?",CUID.get(i));

            while (incm.next()){
                totalIncome=totalIncome+incm.getDouble(1);
            }

        }

       return totalIncome;
    }


    public static void unReserve(long milli, String id)  throws SQLException, ClassNotFoundException {

                AtomicBoolean stop= new AtomicBoolean(true);
                long millis=5000;

        Thread thread = new Thread(()->{
            while (stop.get()){
                try {
                    Thread.sleep(millis);

                }catch (Exception e){
                    System.out.println(e);
                }

              //

                try {
                    CrudUtil.execute("UPDATE Computer SET Status='can Use' WHERE Computer_ID=?",id);
                    stop.set(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                //
            }

        });
        thread.start();


    }





    public static void navigate(AnchorPane anchorPane, String location) throws IOException {

        anchorPane.getChildren().clear();
        Parent parent = FXMLLoader.load(Util.class.getResource("../View/"+location));
        anchorPane.getChildren().add(parent);

    }

    public static void notifycate(String text,String title){
        Notifications notifications=Notifications.create();

        notifications.darkStyle();
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(4));
        notifications.showError();
    }
    public static void notifycateconfrm(String text,String title){
        Notifications notifications=Notifications.create();



        notifications.darkStyle();
        notifications.text(text);
        notifications.title(title);
        notifications.hideAfter(Duration.seconds(4));
        notifications.showInformation();
    }


    public static String iDIncrement(String table,String column) throws SQLException, ClassNotFoundException {

        String incrementID=null;

        ResultSet maxId = CrudUtil.execute("SELECT CONCAT(MAX(0+SUBSTRING("+column+",3))) FROM "+table+"");

        String id=null;

        while (maxId.next()){
            id=maxId.getString(1);
        }

        if (id!=null){
            int nextID = Integer.parseInt(id);
            nextID++;
            String cptl =table.substring(1,2);
            char v=cptl.charAt(0);
            char second = Character.toUpperCase(v);
            String first =table.substring(0,1);
            incrementID = first+second+nextID;

        }else {
            String cptl =table.substring(1,2);
            char v=cptl.charAt(0);
            char second = Character.toUpperCase(v);
            String first =table.substring(0,1);
            incrementID = first+second+"1";


        }



        return incrementID;
        }

    public static void validate(String pattern, String newValue, JFXTextField textField, JFXButton button) {

        if (newValue.matches(pattern)){
            button.setDisable(false);
            textField.setFocusColor(Color.GREEN);
        }else {
            button.setDisable(true);
            textField.setFocusColor(Color.RED);
        }

    }
    public static void validatePassword(String pattern, String newValue, JFXPasswordField textField, JFXButton button) {

        if (newValue.matches(pattern)){
            button.setDisable(false);
            textField.setFocusColor(Color.GREEN);
        }else {
            button.setDisable(true);
            textField.setFocusColor(Color.RED);
        }

    }

    public static void checkNulTxt( JFXButton button,JFXTextField... textFields){
        for (TextField textField : textFields) {
            if (textField.getText()==null || textField.getText().equals("")){
                button.setDisable(true);


            }
        }
    }
    public static void checkNulPswrd( JFXButton button,JFXPasswordField... textFields){
        for (TextField textField : textFields) {
            if (textField.getText()==null || textField.getText().equals("")){
                button.setDisable(true);

            }
        }
    }
}




