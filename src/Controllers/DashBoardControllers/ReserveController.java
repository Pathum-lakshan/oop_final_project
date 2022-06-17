package Controllers.DashBoardControllers;

import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReserveController {
    public JFXComboBox cmbComputerID;
    public JFXComboBox cmbCustomerID;
    public JFXComboBox cmbPackages;
    public JFXTextField txtCustomerPhone;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerEmailAddress;
    public JFXTextField txtNextComputerStatus;
    public JFXTextField TxtPackagePrice;
    public JFXTextField txtPackageType;
    public JFXComboBox cmbSelectPackagetypeAndID;
    public JFXComboBox cmbSelectCustomer;
    public JFXButton btnaddCustomer;
    public JFXButton btnAddComputer;
    public JFXButton btnAddPackage;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllComboBox();

        btnaddCustomer.setDisable(true);
        btnAddComputer.setDisable(true);
        btnAddPackage.setDisable(true);

        txtCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Z][a-z]*[ ][A-Z][a-z]*$",newValue,txtCustomerName,btnaddCustomer);

            Util.checkNulTxt(btnaddCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });

        txtCustomerNIC.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([0-9]{9}[V]|[0-9]{12})$",newValue,txtCustomerNIC,btnaddCustomer);

            Util.checkNulTxt(btnaddCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });


        txtCustomerEmailAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$",newValue,txtCustomerEmailAddress,btnaddCustomer);

            Util.checkNulTxt(btnaddCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });

        txtCustomerPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^(\\+|0)(94|[1-9]{2,3})(-| |)([0-9]{7}|[0-9]{2} [0-9]{7})$",newValue,txtCustomerPhone,btnaddCustomer);

            Util.checkNulTxt(btnaddCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });


        txtNextComputerStatus.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^(can Use|Repair|CO[0-9]{1,2})$",newValue,txtNextComputerStatus,btnAddComputer);
            Util.checkNulTxt(btnAddComputer,txtNextComputerStatus);
        });

        txtPackageType.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([0-9][h]|[1][0-9][h]|[2][0-4][h])( |)[A-Za-z]*$",newValue,txtPackageType,btnAddPackage);
            Util.checkNulTxt(btnAddPackage,TxtPackagePrice,txtPackageType);
        });

        TxtPackagePrice.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9.]+$",newValue,TxtPackagePrice,btnAddPackage);
            Util.checkNulTxt(btnAddPackage,TxtPackagePrice,txtPackageType);
        });




    }

    private void loadAllComboBox() throws SQLException, ClassNotFoundException {
        ObservableList packageID= FXCollections.observableArrayList();
        ResultSet resultSet= CrudUtil.execute("SELECT Package_ID FROM Package");
        while (resultSet.next()){
            packageID.add(
                    resultSet.getString(1)
            );
        }
        cmbPackages.setItems(packageID);


        ObservableList ComputerID= FXCollections.observableArrayList();
        ResultSet computer= CrudUtil.execute("SELECT Computer_ID FROM Computer WHERE Status='can Use'");
        while (computer.next()){
            ComputerID.add(
                    computer.getString(1)
            );
        }
        try {
            cmbComputerID.setItems(ComputerID);
        }catch (Exception e){
        }



        ObservableList CustomerID= FXCollections.observableArrayList();
        ResultSet customer= CrudUtil.execute("SELECT Customer_ID FROM Customer  WHERE Customer_ID NOT IN (SELECT Status FROM Computer)");
        while (customer.next()){
            CustomerID.add(
                    customer.getString(1)
            );
        }
        cmbCustomerID.setItems(CustomerID);



        ObservableList packageTypeAndID= FXCollections.observableArrayList();
        ResultSet typeAndID= CrudUtil.execute("SELECT * FROM Package");
        while (typeAndID.next()){
            String type =  typeAndID.getString(3);
            String ID = typeAndID.getString(1);
            String typeID = ID+"  /  "+type;
            packageTypeAndID.add(
                    typeID

            );
        }
        cmbSelectPackagetypeAndID.setItems(packageTypeAndID);


        ObservableList CustomerIDAndName= FXCollections.observableArrayList();
        ResultSet IDAndName= CrudUtil.execute("SELECT * FROM Customer");
        while (IDAndName.next()){
            String name =  IDAndName.getString(2);
            String ID = IDAndName.getString(1);
            String IDName = ID+"  /  "+name;
            CustomerIDAndName.add(
                    IDName

            );
        }
        cmbSelectCustomer.setItems(CustomerIDAndName);


    }

    public void reserveComputerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {


        if (cmbCustomerID.getValue()==null){
            Util.notifycate("Select Customer","ERROR");
        }else {
            if (cmbComputerID.getValue()==null){
                Util.notifycate("Select Computer","ERROR");
            }else {
                if (cmbPackages.getValue()==null){
                    Util.notifycate("Select Package","ERROR");
                }else {

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    String COID = (String) cmbComputerID.getValue();
                    String CUID = (String) cmbCustomerID.getValue();
                    String PID = (String) cmbPackages.getValue();

                    String Date = dtf.format(now);

                    CrudUtil.execute("UPDATE Computer SET Status=? WHERE Computer_ID=?",CUID,COID);

                    CrudUtil.execute("INSERT INTO reserve VALUES (?,?,?)",COID,CUID,Date);

                    ResultSet resultSet=CrudUtil.execute("SELECT * FROM package WHERE Package_ID=?",PID);
                    double income = 0;
                    String PType =null;
                    while (resultSet.next()){
                        income=resultSet.getDouble("Package_Price");
                        PType=resultSet.getString("Package_Type");
                    }
                  CrudUtil.execute("INSERT INTO Package_Details VALUES (?,?,?)",PID,CUID,income);
                        int index = PType.indexOf("h");
                        int hour = Integer.parseInt(PType.substring(0,index));
                        long milli = hour*60*60*1000;
                        Util.unReserve(milli,COID);
                    Util.notifycateconfrm("Reserved Computer   "+cmbComputerID.getValue()+"  ","RESERVED");

                    ResultSet CName= CrudUtil.execute("SELECT * FROM customer WHERE Customer_ID=?",CUID);

                    String CustomerName = null;

                    while (CName.next()){
                        CustomerName=CName.getString(2);
                    }

                    String ComputerID=COID;
                    String PackageName=PType;
                    String PackagePrice= String.valueOf(income);

                    HashMap<String, Object> reserve = new HashMap<>();

                    reserve.put("CustomerName",CustomerName);
                    reserve.put("ComputerID",ComputerID);
                    reserve.put("PackageName",PackageName);
                    reserve.put("PackagePrice",PackagePrice);

                    JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/View/report/jasperReserved.jasper"));

                    JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, reserve, new JREmptyDataSource(1));

                    JasperViewer.viewReport(jasperPrint, false);

                    initialize();
                    cmbCustomerID.setValue(null);
                    cmbComputerID.setValue(null);
                    cmbPackages.setValue(null);

                    AtomicBoolean stop= new AtomicBoolean(true);
                    long millis=6000;

                    Thread thread = new Thread(()->{
                        while (stop.get()){
                            try {
                                Thread.sleep(millis);

                            }catch (Exception e){
                                System.out.println(e);
                            }

                            //

                            try {
                                loadAllComboBox();
                            } catch (Exception e) {
                            }

                            stop.set(false);

                            //
                        }

                    });
                    thread.start();


                }
            }
        }
    }

   /* public void addCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       String ID= Util.iDIncrement("Customer","Customer_ID");
       String name= txtCustomerName.getText();
       String nic= txtCustomerNIC.getText();
       String email= txtCustomerEmailAddress.getText();
       String phone = txtCustomerPhone.getText();
       String UID =Util.UID;
            if (txtCustomerName.getText().matches("^[A-Z][a-z]*[ ][A-Z][a-z]*$")){
                if (txtCustomerNIC.getText().matches("^([0-9]{9}[V]|[0-9]{12})$")){
                   if (txtCustomerEmailAddress.getText().matches("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$")){
                       if (txtCustomerPhone.getText().matches("^(\\+|0)(94|[1-9]{2,3})(-| |)([0-9]{7}|[0-9]{2} [0-9]{7})$")){

                           if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?)",ID,name,nic,email,phone,UID)){
                               Util.notifycateconfrm(txtCustomerName.getText()+"     ADDED","Customer Added SuccessFul");
                               txtCustomerName.clear();
                               txtCustomerNIC.clear();
                               txtCustomerEmailAddress.clear();
                               txtCustomerPhone.clear();
                               initialize();
                           }
                       }else {
                           if (txtCustomerPhone.getText().equals("")){
                               Util.notifycate("First Fill Phone","ERROR");
                           }else {
                               Util.notifycate("NOT Valid   >'"+txtCustomerPhone.getText()+"'<   Please Enter Valid Phone","ERROR");
                           }
                       }
                   }else {
                       if (txtCustomerEmailAddress.getText().equals("")){
                           Util.notifycate("First Fill Email","ERROR");
                       }else {
                           Util.notifycate("NOT Valid   >'"+txtCustomerEmailAddress.getText()+"'<   Please Enter Valid Email","ERROR");
                       }
                   }
                }else {
                    if (txtCustomerNIC.getText().equals("")){
                        Util.notifycate("First Fill NIC","ERROR");
                    }else {
                        Util.notifycate("NOT Valid   >'"+txtCustomerNIC.getText()+"'<   Please Enter Valid NIC","ERROR");
                    }
                }
            }
            else
            {
                if (txtCustomerName.getText().equals("")){
                    Util.notifycate("First Fill Name","ERROR");
                }else {
                    Util.notifycate("NOT Valid   >'"+txtCustomerName.getText()+"'<   Please Enter Valid Name","ERROR");
                }
        }
    }
*/
    public void addComputer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

            String ID= Util.iDIncrement("Computer","Computer_ID");
            String status = txtNextComputerStatus.getText();
            if (CrudUtil.execute("INSERT INTO Computer VALUES (?,?)",ID,status)){
                Util.notifycateconfrm(ID+"     ADDED","Computer Added SuccessFul");
                txtNextComputerStatus.clear();
                loadAllComboBox();
            }


    }

    public void addpackageOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


                String ID= Util.iDIncrement("Package","Package_ID");
                double price = Double.parseDouble(TxtPackagePrice.getText());
                String type =txtPackageType.getText();


                if (CrudUtil.execute("INSERT INTO Package VALUES (?,?,?)",ID,price,type)){
                    Util.notifycateconfrm(ID+"     ADDED","Package Added SuccessFul");
                    TxtPackagePrice.clear();
                    txtPackageType.clear();
                    loadAllComboBox();
                }


    }

    public void removePackageOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (cmbSelectPackagetypeAndID.getValue()==null){
            Util.notifycate("Select First Package Type And ID","ERROR");
        }else {
            String packageTypeAndID = (String) cmbSelectPackagetypeAndID.getValue();
            int IDIndex=packageTypeAndID.indexOf(" ");
            String ID = packageTypeAndID.substring(0,IDIndex);

            if (CrudUtil.execute("DELETE FROM Package WHERE Package_ID=?",ID)){
                Util.notifycateconfrm(packageTypeAndID+"     REMOVED","Package REMOVED SuccessFul");
                cmbSelectPackagetypeAndID.setValue(null);
            }
        }

    }

    public void removeCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbSelectCustomer.getValue()==null){
            Util.notifycate("Select First Customer Name And ID","ERROR");
        }else {
            String CustomerNameAndID= (String) cmbSelectCustomer.getValue();
            int IDIndex=CustomerNameAndID.indexOf(" ");
            String ID = CustomerNameAndID.substring(0,IDIndex);
            if (CrudUtil.execute("DELETE FROM Customer WHERE Customer_ID=?",ID)){
                Util.notifycateconfrm(CustomerNameAndID+"     REMOVED","Customer REMOVED SuccessFul");
                cmbSelectCustomer.setValue(null);
            }
        }

     }


    public void addCustomersOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ID= Util.iDIncrement("Customer","Customer_ID");
        String name= txtCustomerName.getText();
        String nic= txtCustomerNIC.getText();
        String email= txtCustomerEmailAddress.getText();
        String phone = txtCustomerPhone.getText();
       // String UID =Util.UID;
        String UID ="US2";


        if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?,?)",ID,name,nic,email,phone,UID)){
            Util.notifycateconfrm(txtCustomerName.getText()+"     ADDED","Customer Added SuccessFul");
            txtCustomerName.clear();
            txtCustomerNIC.clear();
            txtCustomerEmailAddress.clear();
            txtCustomerPhone.clear();
            loadAllComboBox();
        }
    }
}
