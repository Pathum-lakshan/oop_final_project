package Controllers.SignInController;

import Utill.CrudUtil;
import Utill.Util;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class SignController {
    public AnchorPane signInContex;
    public Label lblWelcome;

    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        Util.navigate(signInContex, "Sign/SignIn.fxml");

        long millis=5000;

        Thread thread = new Thread(()->{
        while (Util.end) {
            
            try {
             Thread.sleep(millis);

             } catch (Exception e) {
                 System.out.println(e);
             }

    //
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(6),lblWelcome);
            fadeOut.setFromValue(0.68);
            fadeOut.setDelay(Duration.seconds(6));
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeOut.setOnFinished(event -> {

                lblWelcome.setVisible(true);
            });

            fadeOut.play();
    //
              }

          });
              thread.start();





    }
}
