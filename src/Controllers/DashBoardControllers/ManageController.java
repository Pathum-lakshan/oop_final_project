package Controllers.DashBoardControllers;

import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ManageController {
    public AnchorPane WorkingContex;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerNIC;
    public JFXTextField txtCustomerEmailAddress;
    public JFXTextField txtCustomerPhone;
    public JFXButton btnDelUpCustomer;
    public JFXTextField txtPackageType;
    public JFXTextField txtPackagePrice;
    public JFXButton btnDeletePackage;
    public JFXComboBox cmbPackageID;
    public JFXComboBox cmbComputerID;
    public JFXTextField txtItemName;
    public JFXTextField txtItemQtY;
    public JFXButton btnAddItem;
    public JFXComboBox cmbItemID;
    public JFXComboBox cmbItemType;
    public JFXTextField txtRepairAbout;
    public JFXButton btnaddDeleteUpRepair;
    public JFXComboBox cmbRItem;
    public JFXComboBox cmbRComputer;
    public JFXTextField txtTimeToRepair;

    String Cname;
    String CNIC;
    String CEmail;
    String Cphone;

    String PType;
    String PPrice;

    String IName;
    String IType;
    String IQty;

    public void initialize() throws SQLException, ClassNotFoundException {

        loadAllComboBox();

        cmbPackageID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            try {
                packageSearch((String) newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                customerSerach((String) newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbItemID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                itemSearch((String) newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        btnDelUpCustomer.setDisable(true);
        btnAddItem.setDisable(true);
        btnDeletePackage.setDisable(true);
        txtCustomerName.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Z][a-z]*[ ][A-Z][a-z]*$",newValue,txtCustomerName,btnDelUpCustomer);

            Util.checkNulTxt(btnDelUpCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });
        txtCustomerNIC.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([0-9]{9}[V]|[0-9]{12})$",newValue,txtCustomerNIC,btnDelUpCustomer);

            Util.checkNulTxt(btnDelUpCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });
        txtCustomerEmailAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$",newValue,txtCustomerEmailAddress,btnDelUpCustomer);

            Util.checkNulTxt(btnDelUpCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });
        txtCustomerPhone.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^(\\+|0)(94|[1-9]{2,3})(-| |)([0-9]{7}|[0-9]{2} [0-9]{7})$",newValue,txtCustomerPhone,btnDelUpCustomer);

            Util.checkNulTxt(btnDelUpCustomer,txtCustomerEmailAddress,txtCustomerPhone,txtCustomerNIC,txtCustomerName);
        });
        txtPackageType.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([0-9][h]|[1][0-9][h]|[2][0-4][h])( |)[A-Za-z]*$",newValue,txtPackageType,btnDeletePackage);
            Util.checkNulTxt(btnDeletePackage,txtPackagePrice,txtPackageType);
        });
        txtPackagePrice.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9.]+$",newValue,txtPackagePrice,btnDeletePackage);
            Util.checkNulTxt(btnDeletePackage,txtPackagePrice,txtPackageType);
        });
        txtItemName.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Za-z0-9_:=?+-.,!@#$%^&*();\\/|<>\"' ]{3,30}$",newValue,txtItemName,btnAddItem);
            Util.checkNulTxt(btnAddItem,txtItemName,txtItemQtY);
        });
        txtItemQtY.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9]+$",newValue,txtItemQtY,btnAddItem);
            Util.checkNulTxt(btnAddItem,txtItemName,txtItemQtY);
        });
        btnaddDeleteUpRepair.setDisable(true);
        txtRepairAbout.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-z ]+$",newValue,txtRepairAbout,btnaddDeleteUpRepair);
            Util.checkNulTxt(btnaddDeleteUpRepair,txtRepairAbout,txtTimeToRepair);
        });
        txtTimeToRepair.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9]*[.][0-5][0-9][h]$",newValue,txtTimeToRepair,btnaddDeleteUpRepair);
            Util.checkNulTxt(btnaddDeleteUpRepair,txtRepairAbout,txtTimeToRepair);
        });

    }

    private void itemSearch(String ID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Items WHERE Item_ID = ?",ID);
        while (resultSet.next()){
            txtItemName.setText(resultSet.getString(2));
            cmbItemType.setValue(resultSet.getString(3));
            txtItemQtY.setText(resultSet.getString(4));

            IName=resultSet.getString(2);

            IType= resultSet.getString(3);

            IQty=resultSet.getString(4);

            btn();
        }
    }

    private void customerSerach(String ID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE Customer_ID=?",ID);

        while (resultSet.next()){
            txtCustomerName.setText(resultSet.getString(2));
            txtCustomerNIC.setText(resultSet.getString(3));
            txtCustomerEmailAddress.setText(resultSet.getString(4));
            txtCustomerPhone.setText(resultSet.getString(5));
            Cname=resultSet.getString(2);
            CNIC=resultSet.getString(3);
            CEmail=resultSet.getString(4);
            Cphone=resultSet.getString(5);
        }
    }

    private void packageSearch(String ID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Package WHERE Package_ID =?",ID);

        while (resultSet.next()){
            txtPackagePrice.setText(resultSet.getString(2));
            txtPackageType.setText(resultSet.getString(3));
            PPrice=resultSet.getString(2);
            PType=resultSet.getString(3);
        }

    }

    private void loadAllComboBox() throws SQLException, ClassNotFoundException {
        ObservableList CustomerID= FXCollections.observableArrayList();
        ResultSet customer= CrudUtil.execute("SELECT Customer_ID FROM Customer");
        while (customer.next()){
            CustomerID.add(
                    customer.getString(1)
            );
        }
        cmbCustomerID.setItems(CustomerID);

        ObservableList packageID= FXCollections.observableArrayList();
        ResultSet packages = CrudUtil.execute("SELECT Package_ID FROM Package");
        while (packages.next()){
            packageID.add(
                    packages.getString(1)
            );
        }
        cmbPackageID.setItems(packageID);

        ObservableList ComputerID= FXCollections.observableArrayList();
        ResultSet computer = CrudUtil.execute("SELECT Computer_ID FROM Computer");
        while (computer.next()){
            ComputerID.add(
              computer.getString(1)
            );
        }
        cmbComputerID.setItems(ComputerID);

        ObservableList RComputerID= FXCollections.observableArrayList();

        ResultSet RCID = CrudUtil.execute("SELECT Computer_ID FROM Computer WHERE Computer.Status!='Repairing'");

        while (RCID.next()){
            RComputerID.add(
                    RCID.getString(1)
            );
        }

        cmbRComputer.setItems(RComputerID);




        ObservableList itemId= FXCollections.observableArrayList();
        ObservableList itemIdName= FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT Item_ID,Item_Name FROM Items");
        while (resultSet.next()){
            itemId.add(
                    resultSet.getString(1)
            );
        }
        cmbItemID.setItems(itemId);


        ResultSet itemidnametype=CrudUtil.execute("SELECT Item_ID,Item_Name,Item_Type FROM items WHERE Item_qty>0");

        while (itemidnametype.next()){
            String x = itemidnametype.getString(1)+"  /  "+itemidnametype.getString(2)+"  /  "+itemidnametype.getString(3);
            itemIdName.add(
                    x
            );
        }
cmbRItem.setItems(itemIdName);

        cmbItemType.getItems().add("Ram");
        cmbItemType.getItems().add("Hdd");
        cmbItemType.getItems().add("Sata Sdd");
        cmbItemType.getItems().add("Mother Board");
        cmbItemType.getItems().add("Processor");
        cmbItemType.getItems().add("Cooling Fan");
        cmbItemType.getItems().add("Casing");
        cmbItemType.getItems().add("KeyBoard");
        cmbItemType.getItems().add("Mouse");
        cmbItemType.getItems().add("MousePad");
        cmbItemType.getItems().add("Head Set");
        cmbItemType.getItems().add("NVME");
        cmbItemType.getItems().add("VGA");
        cmbItemType.getItems().add("Power Supply");
        cmbItemType.getItems().add("Cables");
        cmbItemType.getItems().add("Monitors");
        cmbItemType.getItems().add("Gaming Steering Wheel");
        cmbItemType.getItems().add("UPS");



    }

    public void upDltCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnDelUpCustomer.getText().equals("Update Customer")){

                                    String id= (String) cmbCustomerID.getValue();
                                    String name=txtCustomerName.getText();
                                    String nic=txtCustomerNIC.getText();
                                    String Email=txtCustomerEmailAddress.getText();
                                    String phone=txtCustomerPhone.getText();

                                    if (CrudUtil.execute("UPDATE Customer SET Customer_Name=?,Customer_NIC=?,Customer_Email=?,Customer_Phone=? WHERE Customer_ID=?",name,nic,Email,phone,id)){
                                        Util.notifycateconfrm(id+name+"  UPDATED Successful","UPDATED");

                                        cmbCustomerID.setValue(null);
                                        txtCustomerName.clear();
                                        txtCustomerNIC.clear();
                                        txtCustomerEmailAddress.clear();
                                        txtCustomerPhone.clear();
                                        loadAllComboBox();
                                    }

        }else {
            if (CrudUtil.execute("DELETE FROM Customer WHERE Customer_ID=?",cmbCustomerID.getValue())){
                Util.notifycateconfrm(Cname+" DELETED successful", "DELETED!");
                cmbCustomerID.setValue(null);
                txtCustomerName.clear();
                txtCustomerNIC.clear();
                txtCustomerEmailAddress.clear();
                txtCustomerPhone.clear();
                loadAllComboBox();
            }



        }
    }

    public void CnameOnKeyRelease(KeyEvent keyEvent) {
        if (txtCustomerName.getText().equals(Cname)){
            btnDelUpCustomer.setText("Delete Customer");
        }else{
            btnDelUpCustomer.setText("Update Customer");
        }
    }

    public void CNICOnKeyRelease(KeyEvent keyEvent) {
        if (txtCustomerNIC.getText().equals(CNIC)){
            btnDelUpCustomer.setText("Delete Customer");
        }else {
            btnDelUpCustomer.setText("Update Customer");
        }
    }

    public void CEmailOnKeyRelease(KeyEvent keyEvent) {
        if (txtCustomerEmailAddress.getText().equals(CEmail)){
            btnDelUpCustomer.setText("Delete Customer");
        }else {
            btnDelUpCustomer.setText("Update Customer");
        }
    }

    public void CPhoneOnKeyRelease(KeyEvent keyEvent) {
        if (txtCustomerPhone.getText().equals(Cphone)){
            btnDelUpCustomer.setText("Delete Customer");
        }else {
            btnDelUpCustomer.setText("Update Customer");
        }
    }

    public void pTypeOnKeyRelease(KeyEvent keyEvent) {

        if (txtPackageType.getText().equals(PType)){
            btnDeletePackage.setText("Delete Package");
        }else {
            btnDeletePackage.setText("Update Package");
        }
    }

    public void cPriceOnKeyRelease(KeyEvent keyEvent) {

        if (txtPackagePrice.getText().equals(PPrice)){
            btnDeletePackage.setText("Delete Package");
        }else {
            btnDeletePackage.setText("Update Package");
        }
    }

    public void deletePackageOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnDeletePackage.getText().equals("Delete Package")){
        if (CrudUtil.execute("DELETE FROM Package WHERE Package_ID=?",cmbPackageID.getValue())){
            Util.notifycateconfrm(cmbPackageID.getValue()+"   "+PType +"DELETED Successful","DELETED");
            txtPackageType.clear();
            txtPackagePrice.clear();
            cmbPackageID.setValue(null);
            loadAllComboBox();
        }
        }else{


                    String id = (String) cmbPackageID.getValue();
                    String type = txtPackageType.getText();
                    String price = txtPackagePrice.getText();

                    if (CrudUtil.execute("UPDATE Package SET Package_Type=?,Package_Price=? WHERE Package_ID=?",type,price,id)){

                        Util.notifycateconfrm(id+"  "+type +"   UPDATED Successful","UPDATED");

                        txtPackageType.clear();
                        txtPackagePrice.clear();
                        cmbPackageID.setValue(null);
                        loadAllComboBox();
                    }


        }
    }

    public void txtItemNameOnKeyRelease(KeyEvent keyEvent) {

        if (IName!=null){
            if (txtItemName.getText().equals(IName)){
                btnAddItem.setText("Delete Item");
            }else {
                btnAddItem.setText("Update Item");
            }
        }

    }

    public void txtitemQtyOnKeyRelease(KeyEvent keyEvent) {
        if (IQty!=null){
            if (txtItemQtY.getText().equals(IQty)){
                btnAddItem.setText("Delete Item");
            }else {
                btnAddItem.setText("Update Item");
            }
        }

    }

    public void addItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (btnAddItem.getText().equals("Delete Item")){
            if (CrudUtil.execute("DELETE FROM Items WHERE Item_ID=?",cmbItemID.getValue())){
                Util.notifycateconfrm(IName+"   DELETED  Successful","DELETED");
                cmbItemType.setValue(null);
                cmbItemID.setValue(null);
                txtItemName.clear();
                txtItemQtY.clear();
                loadAllComboBox();
            }
        }else {

                   String id = (String) cmbItemID.getValue();
                    String name = txtItemName.getText();
                    String type = (String) cmbItemType.getValue();
                    String qty = txtItemQtY.getText();

                    if (CrudUtil.execute("UPDATE Items SET Item_Name=?,Item_Type=?,Item_qty=? WHERE Item_ID=?",name,type,qty,id)){
                        Util.notifycateconfrm(id+"    "+name+"     UPDATED Successful","UPDATED");
                        cmbItemType.setValue(null);
                        cmbItemID.setValue(null);
                        txtItemName.clear();
                        txtItemQtY.clear();
                        loadAllComboBox();
                    }
        }

    }

    public void aboutRepairOnKeyRelease(KeyEvent keyEvent) {
    }

    public void deleteComputerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (cmbComputerID.getValue()==null){
            Util.notifycate("Select Computer","ERROR");
        }else {
            if (CrudUtil.execute("DELETE FROM Computer WHERE Computer_ID=?",cmbComputerID.getValue())){
                Util.notifycateconfrm(cmbComputerID.getValue()+"    DELETED Successful","DELETED");
                loadAllComboBox();
            }
        }

        cmbComputerID.setValue(null);
    }

    public void itemOnKeyRelease(KeyEvent keyEvent) {
    btn();
    }

    private void btn() {
        if (IType!=null){
            if (cmbItemType.getValue().equals(IType)){
                btnAddItem.setText("Delete Item");
            }else {
                btnAddItem.setText("Update Item");
            }
        }
    }

    public void addRepairOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {
        if (cmbRItem.getValue()!=null){
            if (cmbRComputer.getValue()!=null){

                String idNameType = (String) cmbRItem.getValue();
                        int index = idNameType.indexOf(" ");
                        String ID = idNameType.substring(0,index);
                        ResultSet resultSet = CrudUtil.execute("SELECT  Item_qty FROM items WHERE Item_ID=?",ID);
                        int qty = 0;
                        String x = null;
                        while (resultSet.next()){
                            x=resultSet.getString("Item_qty");
                        }
                        qty= Integer.parseInt(x);

                        int upQty = qty-1;
                        if (CrudUtil.execute("UPDATE Items SET Item_qty=? WHERE Item_ID=?",upQty,ID)){

                        }

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();

                        String date_time =dtf.format(now);

                        String about =txtRepairAbout.getText()+"  "+date_time;
                        if (CrudUtil.execute("insert into repair_details values (?,?,?)",ID,cmbRComputer.getValue(),about)){

                            CrudUtil.execute("UPDATE Computer SET Status = 'Repairing' WHERE Computer_ID=?",cmbRComputer.getValue());


                            String abt = txtRepairAbout.getText();

                            String cid = (String) cmbRComputer.getValue();

                            String timetorepair= txtTimeToRepair.getText();

                            String j= idNameType;

                            int ind = j.indexOf("/");

                            int length=j.length();

                            String k = j.substring(ind+3,length);

                            int in = k.indexOf("/");

                            String iid  = k.substring(0,in);

                            HashMap<String, Object> repair = new HashMap<>();

                            repair.put("About",abt);
                            repair.put("ComputerID",cid);
                            repair.put("ItemName",iid);
                            repair.put("reparedTime",timetorepair);

                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"YOU NEED REPORT?", ButtonType.YES,ButtonType.NO);



                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.get().equals(ButtonType.YES)) {
                                JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/View/report/repair.jasper"));

                                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, repair, new JREmptyDataSource(1));

                                JasperViewer.viewReport(jasperPrint, false);
                            }




                            String rtime=txtTimeToRepair.getText();

                            int tindex = rtime.indexOf("h");

                            double timerpr = Double.parseDouble(rtime.substring(0,tindex));

                            long milli = (long) (timerpr*60*60*1000);

                            Util.unReserve(milli, (String) cmbRComputer.getValue());
                            loadAllComboBox();
                            Util.notifycateconfrm("Add To Repaired Successful", "REPAIRED");

                            loadAllComboBox();

                            cmbRItem.setValue(null);
                            cmbRComputer.setValue(null);
                            txtRepairAbout.clear();
                            txtTimeToRepair.clear();

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
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    stop.set(false);

                                    //
                                }

                            });
                            thread.start();
                        }




            }else {
                Util.notifycate("First Select Computer","ERROR");
            }
        }else {
            Util.notifycate("First Select Item","ERROR");
        }
    }
}
