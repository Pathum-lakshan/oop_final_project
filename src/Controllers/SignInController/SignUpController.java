package Controllers.SignInController;

import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;


public class SignUpController {
    public AnchorPane signInContex;
    public JFXTextField txtusername;
    public JFXTextField txtPassword;
    public JFXPasswordField txtpasswordfield;
    public ImageView vision;
    public ImageView visioff;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXButton btnSignUp;


    public void initialize(){
        visioff.setVisible(false);
        txtPassword.setVisible(false);
        btnSignUp.setDisable(true);

        txtusername.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Z][a-z]*[ ][A-Z][a-z]*$",(String)newValue,txtusername,btnSignUp);

            Util.checkNulTxt(btnSignUp,txtusername,txtContact,txtEmail);
            Util.checkNulPswrd(btnSignUp,txtpasswordfield);

        });

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$",(String)newValue,txtEmail,btnSignUp);

            Util.checkNulTxt(btnSignUp,txtusername,txtContact,txtEmail);
            Util.checkNulPswrd(btnSignUp,txtpasswordfield);


        });

        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^(\\+|0)(94|[1-9]{2,3})(-| |)([0-9]{7}|[0-9]{2} [0-9]{7})$",(String)newValue,txtContact,btnSignUp);

            Util.checkNulTxt(btnSignUp,txtusername,txtContact,txtEmail);
            Util.checkNulPswrd(btnSignUp,txtpasswordfield);


        });

        txtpasswordfield.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validatePassword("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$",(String)newValue,txtpasswordfield,btnSignUp);

            Util.checkNulTxt(btnSignUp,txtusername,txtContact,txtEmail);
            Util.checkNulPswrd(btnSignUp,txtpasswordfield);


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

    public void signUpOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        String id= Util.iDIncrement("user","User_ID");
        String name = txtusername.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String password = txtpasswordfield.getText();
        if (CrudUtil.execute("INSERT INTO User VALUES (?,?,?,?,?)",id,name,email,contact,password)){
            Util.navigate(signInContex, "Sign/SignIn.fxml");
            Util.notifycateconfrm("SignUp Successful","SIGNUP");
        }

    }

    public void fullNameOnKeyRelease(KeyEvent keyEvent) {
    }
}
