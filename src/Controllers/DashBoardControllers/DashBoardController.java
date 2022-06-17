package Controllers.DashBoardControllers;

import Utill.CrudUtil;
import Utill.Util;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DashBoardController {

    public AnchorPane DashBoardContex;
    public AnchorPane WorkingContex;
    public Label lblnumberofUsedCmptr;


    public Label lblTimeDate;
    public Label lblToDayCustomer;
    public Label lbltodayincome;
    public Label lblcountpackages;

    public void initialize() throws IOException, SQLException, ClassNotFoundException {

        timeDate();
        Util.navigate(WorkingContex,"DashBoard/Reserve.fxml");

                    setViewLabel();



    }

    public  void timeDate(){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();
            lblTimeDate.setText(currentDate.getYear()+ "-" +currentDate.getMonthValue() + "-" +currentDate.getDayOfMonth()+" "+currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setViewLabel() throws SQLException, ClassNotFoundException {
        ResultSet useCmptr = CrudUtil.execute("SELECT COUNT(Status) FROM Computer WHERE Status!='can Use' && Status!='Repairing'");
        while (useCmptr.next()){
            lblnumberofUsedCmptr.setText("0"+useCmptr.getString(1));
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

       String date_time =dtf.format(now);

        ResultSet resultSet=CrudUtil.execute("SELECT COUNT(Customer_ID) FROM reserve where date_time Like '"+date_time+"%'");

        while (resultSet.next()){
                lblToDayCustomer.setText("0"+resultSet.getString(1));
        }

        ArrayList IDS =new ArrayList();

        ResultSet CID =CrudUtil.execute("SELECT * FROM reserve where date_time Like '"+date_time+"%'");

        while (CID.next()){
            IDS.add(CID.getString(2));
        }

        double  income = 0;

        for (int i = 0; i < IDS.size() ; i++) {
            ResultSet incm = CrudUtil.execute("SELECT  income FROM Package_Details WHERE Customer_ID=?",IDS.get(i));

            while (incm.next()){
                income=income+incm.getDouble(1);
            }

        }


        lbltodayincome.setText(String.valueOf(income));


        ResultSet PCount = CrudUtil.execute("SELECT COUNT(Package_ID) FROM Package");

        while (PCount.next()){
            lblcountpackages.setText("0"+PCount.getString(1));
        }

    }

    public void closeMouseOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) DashBoardContex.getScene().getWindow();
        stage.close();
        Util.endone=false;
    }
    public void logOutMouseClickOnAction(MouseEvent mouseEvent) throws IOException {
        Stage stag=(Stage) DashBoardContex.getScene().getWindow();
        stag.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../View/Sign/Sign.fxml"))));
        stag.centerOnScreen();
        Util.endone=false;
    }

    public void homeOnMouseClick(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/Reserve.fxml");
    }

    public void reserveOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/Reserve.fxml");

    }

    public void manageOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/Manage.fxml");
    }

    public void suplyManageOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/SuplyManage.fxml");
    }

    public void viewReserveDetailsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/ViewDetails.fxml");
    }

    public void incomeExpensesOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        setViewLabel();
        Util.navigate(WorkingContex,"DashBoard/IncomeExpens.fxml");
    }
}
