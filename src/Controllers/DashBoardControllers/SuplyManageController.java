package Controllers.DashBoardControllers;

import Module.Details;
import Module.Item;
import Module.Supply;
import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

public class SuplyManageController {
    public AnchorPane WorkingContex;
    public JFXTextField txtsupplyitemname;
    public JFXTextField txtSupplyItemQty;
    public JFXTextField txtSupplyCost;
    public JFXButton btnAddDeltupSupply;
    public JFXComboBox cmbSupplyItemType;
    public JFXComboBox cmbSupplyID;
    public Label lbltodaySupply;
    public Label lblmonthlysupply;
    public Label lblNumberOfItems;
    public TableView tblSupply;
    public TableColumn colSupplyID;
    public TableColumn colSupplyName;
    public TableColumn colSupplyType;
    public TableColumn colSupplyQty;
    public TableColumn colSupplyCost;
    public TableColumn colSupplyDate;
    public TableView tblItem;
    public TableColumn ColItemID;
    public TableColumn ColItemName;
    public TableColumn ColItemType;
    public TableColumn ColItemQty;
    public TableView tblDetails;
    public TableColumn colDetailSupluId;
    public TableColumn colDetailsItemId;

    String name;
    String type;
    String qty;
    String cost;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadAllComboBox();
        cmbSupplyID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                suplySearch((String) newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        loadlbl();

        loadAllTable();

        btnAddDeltupSupply.setDisable(true);

        txtsupplyitemname.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Za-z0-9_:=?+-.,!@#$%^&*();\\/|<>\"' ]{3,30}$",newValue,txtsupplyitemname,btnAddDeltupSupply);

            Util.checkNulTxt(btnAddDeltupSupply,txtsupplyitemname,txtSupplyCost,txtSupplyItemQty);
        });

        txtSupplyCost.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9.]+$",newValue,txtSupplyCost,btnAddDeltupSupply);

            Util.checkNulTxt(btnAddDeltupSupply,txtsupplyitemname,txtSupplyCost,txtSupplyItemQty);
        });

        txtSupplyItemQty.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[0-9]+$",newValue,txtSupplyItemQty,btnAddDeltupSupply);

            Util.checkNulTxt(btnAddDeltupSupply,txtsupplyitemname,txtSupplyCost,txtSupplyItemQty);
        });



    }

    private void loadAllTable() throws SQLException, ClassNotFoundException {
        loadSupply();
        loadItem();
        loadDetails();
    }

    private void loadDetails() throws SQLException, ClassNotFoundException {
        ObservableList<Details> details = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM suply_detail");

        while (resultSet.next()){
            details.add(new Details(resultSet.getString(1),resultSet.getString(2)));
        }
        tblDetails.setItems(details);

        colDetailSupluId.setCellValueFactory(new PropertyValueFactory<>("SupID"));
        colDetailsItemId.setCellValueFactory(new PropertyValueFactory<>("IteID"));



    }

    private void loadItem() throws SQLException, ClassNotFoundException {

        ObservableList<Item> items = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM items");

        while (resultSet.next()){
            items.add(new Item(
                    resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)
            ));
        }
        tblItem.setItems(items);

        ColItemID.setCellValueFactory(new PropertyValueFactory<>("IID"));
        ColItemName.setCellValueFactory(new PropertyValueFactory<>("IName"));
        ColItemType.setCellValueFactory(new PropertyValueFactory<>("IType"));
        ColItemQty.setCellValueFactory(new PropertyValueFactory<>("IQty"));

    }

    private void loadSupply() throws SQLException, ClassNotFoundException {
        ObservableList<Supply> supply = FXCollections.observableArrayList();

        ResultSet supplies = CrudUtil.execute("SELECT * FROM suply");

        while (supplies.next()){
            supply.add(new Supply(
                    supplies.getString(1),supplies.getString(2),supplies.getString(3),supplies.getString(4),supplies.getString(5),supplies.getString(6)
            ));
        }
        tblSupply.setItems(supply);

       colSupplyID.setCellValueFactory(new PropertyValueFactory<>("ID"));
       colSupplyName.setCellValueFactory(new PropertyValueFactory<>("Name"));
       colSupplyType.setCellValueFactory(new PropertyValueFactory<>("Type"));
       colSupplyQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
       colSupplyCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
       colSupplyDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

    }

    private void loadlbl() throws SQLException, ClassNotFoundException {
        loadAllTable();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime now = LocalDateTime.now();

        String Sdate = dtf.format(now);

        ResultSet monthly = CrudUtil.execute("SELECT COUNT(Suply_ID) FROM Suply WHERE Suply_Date_Time LIKE '"+Sdate+"%'");

        while (monthly.next()){
            lblmonthlysupply.setText(monthly.getString(1));
        }

        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime no = LocalDateTime.now();

        String date = dt.format(no);

        ResultSet daily = CrudUtil.execute("SELECT COUNT(Suply_ID) FROM Suply WHERE Suply_Date_Time LIKE '"+date+"%'");

        while (daily.next()){
            lbltodaySupply.setText(daily.getString(1));
        }

        ResultSet item = CrudUtil.execute("SELECT COUNT(Item_ID) FROM Items");

        while (item.next()){
            lblNumberOfItems.setText(item.getString(1));
        }



    }

    private void suplySearch(String ID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM suply WHERE Suply_ID=?",ID);
        while (resultSet.next()){
            txtsupplyitemname.setText(resultSet.getString(2));
            cmbSupplyItemType.setValue(resultSet.getString(3));
            txtSupplyItemQty.setText(resultSet.getString(4));
            txtSupplyCost.setText(resultSet.getString(5));
            name=resultSet.getString(2);
            type=resultSet.getString(3);
            qty= resultSet.getString(4);
            cost= resultSet.getString(5);

        }
    }

    private void loadAllComboBox() throws SQLException, ClassNotFoundException {
        loadlbl();
        cmbSupplyItemType.getItems().add("Ram");
        cmbSupplyItemType.getItems().add("Hdd");
        cmbSupplyItemType.getItems().add("Sata Sdd");
        cmbSupplyItemType.getItems().add("Mother Board");
        cmbSupplyItemType.getItems().add("Processor");
        cmbSupplyItemType.getItems().add("Cooling Fan");
        cmbSupplyItemType.getItems().add("Casing");
        cmbSupplyItemType.getItems().add("KeyBoard");
        cmbSupplyItemType.getItems().add("Mouse");
        cmbSupplyItemType.getItems().add("MousePad");
        cmbSupplyItemType.getItems().add("Head Set");
        cmbSupplyItemType.getItems().add("NVME");
        cmbSupplyItemType.getItems().add("VGA");
        cmbSupplyItemType.getItems().add("Power Supply");
        cmbSupplyItemType.getItems().add("Cables");
        cmbSupplyItemType.getItems().add("Monitors");
        cmbSupplyItemType.getItems().add("Gaming Steering Wheel");
        cmbSupplyItemType.getItems().add("UPS");


        ObservableList SupplyID= FXCollections.observableArrayList();
        ResultSet suply = CrudUtil.execute("SELECT Suply_ID FROM suply");
        String v="  ";
        SupplyID.add(v);

        while (suply.next()){
            SupplyID.add(
                    suply.getString(1)
            );
        }
        cmbSupplyID.setItems(SupplyID);
    }

    public void txtSupplyItemNameOnKeyRelease(KeyEvent keyEvent) {
       if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
        }else if (txtsupplyitemname.getText().equals(name)){

            btnAddDeltupSupply.setText("Delete Suply");

        }else {
            btnAddDeltupSupply.setText("Update Suply");
        }
    }

    public void txtSupplyItemQtyOnAction(KeyEvent keyEvent) {
        if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
        }else if (txtSupplyItemQty.getText().equals(qty)){

            btnAddDeltupSupply.setText("Delete Suply");

        }else {
            btnAddDeltupSupply.setText("Update Suply");
        }
    }

    public void txtSupplyCostOnAction(KeyEvent keyEvent) {
        if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
        }else if (txtSupplyCost.getText().equals(cost)){

            btnAddDeltupSupply.setText("Delete Suply");

        }else {
            btnAddDeltupSupply.setText("Update Suply");
        }
    }

    public void btnAddDeltupSupplyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, JRException {

        if (btnAddDeltupSupply.getText().equals("Add Suply")){

                if (cmbSupplyItemType.getValue()==null){
                    Util.notifycate("First Select Type","ERROR");
                }else {


                            String Sid = Util.iDIncrement("Suply","Suply_ID");
                            String Sname = txtsupplyitemname.getText();
                            String Stype = (String) cmbSupplyItemType.getValue();
                            int Sqty = Integer.parseInt(txtSupplyItemQty.getText());
                            double Scost = Double.parseDouble(txtSupplyCost.getText());
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();





                            String Sdate = dtf.format(now);

                            if (CrudUtil.execute("INSERT INTO suply VALUES (?,?,?,?,?,?)",Sid,Sname,Stype,Sqty,Scost,Sdate)){
                                Util.notifycateconfrm(Sid+"   "+Sname+"   ADDED Successful","ADDED");







                                ResultSet IID = CrudUtil.execute("SELECT * FROM Items WHERE Item_Name=?",Sname);

                                String ItemId = null;
                                int ItemQty = 0;
                                String ItemName=" ";
                                String ItemType=null;

                                while (IID.next()){
                                    ItemId = IID.getString(1);
                                    ItemName= IID.getString(2);
                                    ItemType=IID.getString(3);
                                    ItemQty=IID.getInt(4);
                                }


                                ItemQty=ItemQty+Sqty;



                                if (Sname.equals(ItemName)){
                                    CrudUtil.execute("UPDATE Items SET Item_qty=? WHERE Item_ID=?",ItemQty,ItemId);
                                    CrudUtil.execute("INSERT INTO Suply_Detail VALUES (?,?)",Sid,ItemId);
                                }
                                else {
                                    String nextItemId=Util.iDIncrement("items","Item_ID");

                                    CrudUtil.execute("Insert INTO Items VALUES (?,?,?,?)",nextItemId,Sname,Stype,Sqty);
                                    CrudUtil.execute("INSERT INTO Suply_Detail VALUES (?,?)",Sid,nextItemId);

                                }
                                loadAllComboBox();

                                cmbSupplyID.setValue(null);
                                txtsupplyitemname.clear();
                                cmbSupplyItemType.setValue(null);
                                txtSupplyItemQty.clear();
                                txtSupplyCost.clear();




                                }

                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"YOU NEED REPORT?", ButtonType.YES,ButtonType.NO);



                            Optional<ButtonType> buttonType = alert.showAndWait();
                            if (buttonType.get().equals(ButtonType.YES)) {

                                HashMap<String, Object> supply = new HashMap<>();

                                supply.put("ID", Sid);
                                supply.put("Name", Sname);
                                supply.put("Type", Stype);
                                String qty = String.valueOf(Sqty);
                                supply.put("Qty", qty);
                                String cost = String.valueOf(Scost);
                                supply.put("Cost", cost);


                                JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/View/report/Supply.jasper"));

                                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, supply, new JREmptyDataSource(1));

                                JasperViewer.viewReport(jasperPrint, false);



                            }


                }






        }
        else if (btnAddDeltupSupply.getText().equals("Delete Suply")){
            if (CrudUtil.execute("DELETE FROM Suply WHERE Suply_ID=?",cmbSupplyID.getValue())){
                Util.notifycateconfrm(cmbSupplyID.getValue()+"   "+txtsupplyitemname.getText()+" DELETED successful","DELETED");
                loadAllComboBox();
                cmbSupplyID.setValue(null);
                txtsupplyitemname.clear();
                cmbSupplyItemType.setValue(null);
                txtSupplyItemQty.clear();
                txtSupplyCost.clear();
            }
        }
        else {
            String Sname = txtsupplyitemname.getText();
            String Stype = (String) cmbSupplyItemType.getValue();
            int Sqty = Integer.parseInt(txtSupplyItemQty.getText());
            double Scost = Double.parseDouble(txtSupplyCost.getText());

            if(CrudUtil.execute("UPDATE Suply SET  Suply_Name=?,Suply_Type=?,Suply_qty=?,Suply_Cost=? WHERE Suply_ID=?",Sname,Stype,Sqty,Scost,cmbSupplyID.getValue())){
                Util.notifycateconfrm(cmbSupplyID.getValue()+"     UPDATED Successful","UPDATED");
                cmbSupplyID.setValue(null);
                txtsupplyitemname.clear();
                cmbSupplyItemType.setValue(null);
                txtSupplyItemQty.clear();
                txtSupplyCost.clear();
            }
        }


    }

    public void SuplyitemTypeOnKeyRelease(KeyEvent keyEvent) {
        if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
        }else if (cmbSupplyItemType.getValue().equals(type)){

            btnAddDeltupSupply.setText("Delete Suply");

        }else {
            btnAddDeltupSupply.setText("Update Suply");
        }
    }

    public void supplyIDOnKeyRelese(KeyEvent keyEvent) {
        if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
            txtsupplyitemname.clear();
            cmbSupplyItemType.setValue(null);
            txtSupplyItemQty.clear();
            txtSupplyCost.clear();
        }else{
            btnAddDeltupSupply.setText("Delete Suply");
        }
    }

    public void cmbSupplyIDOnAction(ActionEvent actionEvent) {
        if (cmbSupplyID.getValue()=="  "||cmbSupplyID.getValue()==null){
            btnAddDeltupSupply.setText("Add Suply");
            txtsupplyitemname.clear();
            cmbSupplyItemType.setValue(null);
            txtSupplyItemQty.clear();
            txtSupplyCost.clear();
        }else{
            btnAddDeltupSupply.setText("Delete Suply");
        }
    }
}
