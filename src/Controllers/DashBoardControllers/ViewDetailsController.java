package Controllers.DashBoardControllers;

import Module.*;
import Module.Package;
import Utill.CrudUtil;
import Utill.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewDetailsController {

    public AnchorPane WorkingContex;
    public TableView tblCustomer;
    public TableColumn colCustomerID;
    public TableColumn colCustomerName;
    public TableColumn colCustomerNIC;
    public TableColumn colCustomerEmail;
    public TableColumn colCustomerContact;
    public TableColumn colCustomerUserID;
    public TableView tblComputer;
    public TableColumn colComputerID;
    public TableColumn colStatus;
    public TableView tblPackage;
    public TableColumn colPackageID;
    public TableColumn colPackageType;
    public TableColumn ColPackagePriceone;
    public TableView tblReserve;
    public TableColumn colRCOID;
    public TableColumn colRCUID;
    public TableColumn colRDT;
    public TableView tblPackageDetails;
    public TableColumn colPDPAID;
    public TableColumn colPDCUID;
    public TableColumn colPDIncome;
    public LineChart incomeLineChart;
    public BarChart barChartview;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllTable();
    }

    private void loadAllTable() throws SQLException, ClassNotFoundException {
        loadCustomer();
        loadComputer();
        loadPackage();
        loadReserve();
        loadPackageDetails();
        loadBarChart();
    }

    private void loadBarChart() throws SQLException, ClassNotFoundException {
        XYChart.Series Computer = new XYChart.Series();
        XYChart.Series Package = new XYChart.Series();
        XYChart.Series ComputerUsage = new XYChart.Series();
        XYChart.Series PackageUsage = new XYChart.Series();

        ResultSet resultSet =CrudUtil.execute("SELECT COUNT(Computer_ID) FROM Computer");

        while (resultSet.next()){
            Computer.getData().add(new XYChart.Data<>("Computer",resultSet.getInt(1)));
        }

        Computer.setName("Computer");

        barChartview.getData().add(Computer);


        ResultSet packages = CrudUtil.execute("SELECT COUNT(Package_ID) FROM Package");

        while (packages.next()){
            Package.getData().add(new XYChart.Data<>("Package",packages.getInt(1)));
        }

        Package.setName("Package");

        barChartview.getData().add(Package);


        ResultSet ComputerIDS = CrudUtil.execute("SELECT * FROM computer");

        while (ComputerIDS.next()){
            String id=ComputerIDS.getString(1);

            ComputerUsage.getData().add(new XYChart.Data<>(id,Util.computerUsageCount(id)));


        }

        ComputerUsage.setName("Computer Usage");

        barChartview.getData().add(ComputerUsage);


        ResultSet PackageIDS = CrudUtil.execute("SELECT * FROM package");

        while (PackageIDS.next()){

            String id = PackageIDS.getString(1);

            PackageUsage.getData().add(new XYChart.Data<>(id,Util.packageUsageCount(id)));

        }

        PackageUsage.setName("Package Usage");

        barChartview.getData().add(PackageUsage);




    }


    private void loadPackageDetails() throws SQLException, ClassNotFoundException {
        ObservableList<PackageDetails> packageDetails = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM package_details");

        while (resultSet.next()){
            packageDetails.add(
                    new PackageDetails(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3))
            );
        }

        tblPackageDetails.setItems(packageDetails);

        colPDPAID.setCellValueFactory(new PropertyValueFactory<>("PDPAID"));
        colPDCUID.setCellValueFactory(new PropertyValueFactory<>("PDCUID"));
        colPDIncome.setCellValueFactory(new PropertyValueFactory<>("PDIncome"));

    }

    private void loadReserve() throws SQLException, ClassNotFoundException {
        ObservableList<Reserve> reserves =FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM reserve");

        while (resultSet.next()){
            reserves.add(
                    new Reserve(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3))
            );
        }

        tblReserve.setItems(reserves);

        colRCOID.setCellValueFactory(new PropertyValueFactory<>("RCOID"));
        colRCUID.setCellValueFactory(new PropertyValueFactory<>("RCUID"));
        colRDT.setCellValueFactory(new PropertyValueFactory<>("Date_Time"));


    }

    private void loadPackage() throws SQLException, ClassNotFoundException {
        ObservableList<Package> packages = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Package");

        while (resultSet.next()){
            packages.add(
                    new Package(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3) )
            );
        }

        tblPackage.setItems(packages);

        colPackageID.setCellValueFactory(new PropertyValueFactory<>("PAID"));
        ColPackagePriceone.setCellValueFactory(new PropertyValueFactory<>("PackagePrice"));
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("PackageType"));


    }

    private void loadComputer() throws SQLException, ClassNotFoundException {
        ObservableList<Computer> computers = FXCollections.observableArrayList();

        ResultSet  resultSet = CrudUtil.execute("SELECT * FROM computer");

        while (resultSet.next()){
            computers.add(
                    new Computer(resultSet.getString(1),resultSet.getString(2))
            );
        }

        tblComputer.setItems(computers);

        colComputerID.setCellValueFactory(new PropertyValueFactory<>("COID"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

    }

    private void loadCustomer() throws SQLException, ClassNotFoundException {

        ObservableList<Customer> customers = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");

        while (resultSet.next()){
            customers.add(new Customer(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5), resultSet.getString(6)
            ));
        }
        tblCustomer.setItems(customers);

        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CUID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colCustomerNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colCustomerUserID.setCellValueFactory(new PropertyValueFactory<>("USID"));


    }
}
