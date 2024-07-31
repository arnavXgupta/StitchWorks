package com.example.projectone.admin;

import com.example.projectone.SendEmail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminController {
    String p="123";
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button btn;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField pwd;
    @FXML
    private Label lbl;

    @FXML
    void dologin(ActionEvent event) throws IOException {

        if(pwd.getText().equals(p))
        {
            Parent root=FXMLLoader.load(getClass().getResource("/com/example/projectone/dash/dashboardView.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();

            Scene scene1=(Scene)lbl.getScene();
            scene1.getWindow().hide();

//            SendEmail s=new SendEmail();
//            s.SendEmail("arnav090404@gmail.com", "Login Successful", "You have successfully logged into our portal");
        }
        else
            lbl.setText("Incorrect Password..");

    }



    @FXML
    void initialize() {
    }

}
