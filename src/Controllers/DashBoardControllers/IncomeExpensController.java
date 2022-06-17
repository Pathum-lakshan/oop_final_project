package Controllers.DashBoardControllers;

import Utill.CrudUtil;
import Utill.Util;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IncomeExpensController {
    public AnchorPane WorkingContex;
    public LineChart chartIncome;
    public LineChart chartexpens;

    public BarChart annualIncome;
    public BarChart annualExpems;

    public void initialize () throws SQLException, ClassNotFoundException {
        loadAllChart();
    }

    private void loadAllChart() throws SQLException, ClassNotFoundException {
        loadIncome();
        loadExpens();
        loadAnnualExpens();
        loadAnnualIncome();
    }

    private void loadAnnualExpens() throws SQLException, ClassNotFoundException {
        XYChart.Series yearlyExpens = new XYChart.Series();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();

        String date_time =dtf.format(now);

        int year = Integer.parseInt(date_time);

        int last5Year= year-4;

        for (int i = 0; i < 5; i++) {

            int x = last5Year + i;

            String y= String.valueOf(x);

            yearlyExpens.getData().add(new XYChart.Data<>(y,Util.expense(y)));

        }

        yearlyExpens.setName("Yearly Expens");

        annualExpems.getData().add(yearlyExpens);

    }

    private void loadAnnualIncome() throws SQLException, ClassNotFoundException {
        XYChart.Series yearlyIncome = new XYChart.Series();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();

        String date_time =dtf.format(now);

        int year = Integer.parseInt(date_time);

        int last5Year= year-4;

        for (int i = 0; i < 5; i++) {
            int x=last5Year+i;



            ResultSet resultSet = CrudUtil.execute("SELECT Customer_ID FROM reserve where date_time Like '"+x+"%'");

            double income = 0;

            while (resultSet.next()){

                income=income+Util.yearlyincome(resultSet.getString(1));

            }

            String y= String.valueOf(x);



            yearlyIncome.getData().add(new XYChart.Data<>(y,income));




        }



        yearlyIncome.setName("Yearly Income");

        annualIncome.getData().add(yearlyIncome);
    }

    private void loadExpens() throws SQLException, ClassNotFoundException {
        XYChart.Series expens = new XYChart.Series();

        String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();

        String date_time =dtf.format(now);

        for (int i = 0; i < month.length; i++) {

            if (i<10){

                expens.getData().add(new XYChart.Data<>(month[i], Util.expense(date_time+"-0"+(i+1))));

            }else {
                expens.getData().add(new XYChart.Data<>(month[i],Util.expense(date_time+"-"+(i+1))));

            }

        }

        expens.setName("Monthly Expense");

        chartexpens.getData().add(expens);

    }

    private void loadIncome() throws SQLException, ClassNotFoundException {

        XYChart.Series income = new XYChart.Series();


        String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();

        String date_time =dtf.format(now);

        for (int i = 0; i < month.length; i++) {

            if (i<10){

                income.getData().add(new XYChart.Data<>(month[i], Util.CuIds(date_time+"-0"+(i+1))));
            }else {

                income.getData().add(new XYChart.Data<>(month[i],Util.CuIds(date_time+"-"+(i+1))));
            }

        }

        income.setName("Monthly Income");

        chartIncome.getData().add(income);
      
    }
}
