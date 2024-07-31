package com.example.projectone.workerEnroll;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class workerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField workerAdd;

    @FXML
    private ListView<String> workerKaam;

    @FXML
    private TextField workerMob;

    @FXML
    private TextField workerName;

    @FXML
    private TextField workerSpec;

    @FXML
    void doOpenNew(ActionEvent event) {
        workerAdd.setText("");
        workerMob.setText("");
        workerName.setText("");
        workerSpec.setText("");
    }

    PreparedStatement stmt;

    @FXML
    void doSave(ActionEvent event) {
        //         wname, address, mobile, splz
        try{
            stmt=con.prepareStatement("insert into workers values (?,?,?,?)");
            stmt.setString(1, workerName.getText());
            stmt.setString(2, workerAdd.getText());
            stmt.setString(3, workerMob.getText());
            stmt.setString(4, workerSpec.getText());

            stmt.executeUpdate();
            showMyMsg("Record Saved");
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }

    @FXML
    void onDoubleClick(MouseEvent event) {
        if(event.getClickCount()==2){
//            String km=getItems.add(workerKaam.getSelectionModel().getSelectedItem());
            workerSpec.setText(workerSpec.getText()+" "+workerKaam.getSelectionModel().getSelectedItems());
        }
    }

    Connection con;

    @FXML
    void initialize() {
        con= mySqlConnectionClass.doConnect();
        if(con==null)
            System.out.println("connection not established");
        else
            System.out.println("Connection done");

        String kaam[]={"Trouser", "Shirt", "Palazzo", "Coord Set", "Kurta"};
        workerKaam.getItems().addAll(kaam);
    }

    void showMyMsg(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
