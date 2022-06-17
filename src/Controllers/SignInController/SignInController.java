package Controllers.SignInController;

import Utill.CrudUtil;
import Utill.Util;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInController {
    public AnchorPane signInContex;
    public JFXTextField txtusername;
    public JFXTextField txtPassword;
    public JFXPasswordField txtpasswordfield;
    public ImageView visioff;
    public ImageView vision;
    public JFXButton btnSignIn;

    public void initialize() throws IOException {



        visioff.setVisible(false);
        txtPassword.setVisible(false);

        btnSignIn.setDisable(true);

        txtusername.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validate("^[A-Z][a-z]*$",(String)newValue,txtusername,btnSignIn);

           Util.checkNulPswrd(btnSignIn,txtpasswordfield);
           Util.checkNulTxt(btnSignIn,txtusername);

        });
 txtpasswordfield.textProperty().addListener((observable, oldValue, newValue) -> {
            Util.validatePassword("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$",(String)newValue,txtpasswordfield,btnSignIn);


     Util.checkNulPswrd(btnSignIn,txtpasswordfield);
     Util.checkNulTxt(btnSignIn,txtusername);


 });

    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        Util.navigate(signInContex, "Sign/SignUp.fxml");
    }

    public void ForgetPassWordOnaction(ActionEvent actionEvent) throws IOException {
        Util.navigate(signInContex, "Sign/Forget.fxml");

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

    public void signInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {




        Util.end=false;

        String username = txtusername.getText();

        String password = null;

        ResultSet user = CrudUtil.execute("SELECT * FROM User WHERE User_Name LIKE '"+username+"%'");

        while (user.next()){
            Util.UID=user.getString(1);
            password = user.getString(5);
        }

        if (txtpasswordfield.getText().equals(password)){
            Util.end=false;

            Stage stage=(Stage) signInContex.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../View/DashBoard/DashBoard.fxml"))));
            stage.centerOnScreen();

            Util.notifycateconfrm(username+"   Sign In Successful","SIGN IN");
        }else {
            Util.notifycate("********** Password Is Not Match Your User Name","ERROR");
        }
    }

    public void userNameOnKeyRelease(KeyEvent keyEvent) {
        Util.validate("^[A-Z][a-z]*$",txtusername.getText(),txtusername,btnSignIn);

        if (txtpasswordfield.getText()==null || txtpasswordfield.getText().equals("")){
            btnSignIn.setDisable(true);
        }
    }

    public void passwordOnKeyRelease(KeyEvent keyEvent) {
        Util.validatePassword("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$",txtpasswordfield.getText(),txtpasswordfield,btnSignIn);

        if (txtusername.getText()==null  || txtusername.getText().equals("")){
            btnSignIn.setDisable(true);
        }


    }
}
