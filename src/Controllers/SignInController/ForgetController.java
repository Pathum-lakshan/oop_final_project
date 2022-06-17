package Controllers.SignInController;

import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgetController {
    public AnchorPane signInContex;
    public JFXTextField txtusername;
    public JFXTextField txtPassword;
    public JFXPasswordField txtpasswordfield;
    public ImageView vision;
    public ImageView visioff;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public Rectangle rectUpPass;
    public JFXButton btnForget;

    public void initialize(){
        visioff.setVisible(false);
        txtPassword.setVisible(false);
        btnForget.setDisable(true);

        txtusername.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Z][a-z]*[ ][A-Z][a-z]*$",(String)newValue,txtusername,btnForget);


            Util.checkNulTxt(btnForget,txtusername,txtContact,txtEmail);

        });

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$",(String)newValue,txtEmail,btnForget);


            Util.checkNulTxt(btnForget,txtusername,txtContact,txtEmail);

        });

        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^(\\+|0)(94|[1-9]{2,3})(-| |)([0-9]{7}|[0-9]{2} [0-9]{7})$$",(String)newValue,txtContact,btnForget);


            Util.checkNulTxt(btnForget,txtusername,txtContact,txtEmail);

        });

    }

    public void SignInOnAction(ActionEvent actionEvent) throws IOException {
        Util.navigate(signInContex, "Sign/SignIn.fxml");
    }

    public void entermouseOnaction(MouseEvent mouseEvent) {
        visioff.setVisible(true);
        txtpasswordfield.setVisible(false);
        txtPassword.setVisible(true);
        txtPassword.setText(txtpasswordfield.getText());
    }

    public void exitemouseonaction(MouseEvent mouseEvent) {
        visioff.setVisible(false);
        txtpasswordfield.setVisible(true);
        txtPassword.setVisible(false);
    }

    public void ForgetOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {


        if (btnForget.getText().equals("Try")){

                        String name = txtusername.getText();
                        String email = null;
                        String contact = null;

                        ResultSet forget = CrudUtil.execute("SELECT * FROM user WHERE User_Name=?",name);

                        while (forget.next()){

                            email  =  forget.getString(3);
                            contact  = forget.getString(4);
                        }

                        if (txtEmail.getText().equals(email)){
                            if (txtContact.getText().equals(contact)){
                                rectUpPass.toBack();

                                btnForget.setText("Forget");
                                txtpasswordfield.textProperty().addListener((observable, oldValue, newValue) -> {
                                    Util.validatePassword("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$",(String)newValue,txtpasswordfield,btnForget);


                                    Util.checkNulPswrd(btnForget,txtpasswordfield);



                                });


                            }else {
                                Util.notifycate("Not Match Contact ","ERROR");
                            }
                        }else {
                            Util.notifycate("Not Match Email ","ERROR");
                        }
            }else {


                String upId=txtpasswordfield.getText();
                    if (CrudUtil.execute("UPDATE user SET User_Password=? WHERE User_Name=?",upId,txtusername.getText())){
                         Util.navigate(signInContex, "Sign/SignIn.fxml");
                            Util.notifycateconfrm(txtusername.getText()+"   Forget password Successful" ,"FORGET");
}



        }






    }
}
