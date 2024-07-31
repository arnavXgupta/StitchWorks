package com.example.projectone.orderDelPanel;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class orderDelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Integer> getBill;

    @FXML
    private ListView<String> getDress;

    @FXML
    private TextField getMob;

    @FXML
    private ListView<Integer> getOrderID;

    @FXML
    private ListView<Integer> getStatus;

    @FXML
    private TextField getTotal;

    @FXML
    void doAll(ActionEvent event) {
        try{
            stmt=con.prepareStatement("update measurements set workstatus=3 where ( workstatus=1 or workstatus=2 ) and mobile=? ");
            stmt.setString(1,getMob.getText());
            stmt.executeUpdate();
            System.out.println("Record updated");
            showMyMsg("Record updated, Product Delivered!");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void doFind(ActionEvent event) {

        getStatus.getItems().clear();
        getBill.getItems().clear();
        getDress.getItems().clear();
        getOrderID.getItems().clear();

        try{
            stmt=con.prepareStatement("select workstatus, orderid, bill, dress from measurements where mobile=?");
            stmt.setString(1,getMob.getText());
            ResultSet rs=stmt.executeQuery();
            while (rs.next()) {
                Integer ws=rs.getInt("workstatus");
                Integer oid=rs.getInt("orderid");
                Integer bl=rs.getInt("bill");
                String dr=rs.getString("dress");

                getStatus.getItems().add(ws);
                getOrderID.getItems().add(oid);
                getBill.getItems().add(bl);
                getDress.getItems().add(dr);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

//    void calBill(){
//        try {
//            stmt=con.prepareStatement("Select bill from measurements where mobile=?");
//            stmt.setString(1,getMob.getText());
//            ResultSet rs = stmt.executeQuery();
//            while(rs.next()) {
//                Integer name = rs.getInt("bill");
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    Connection con=null;
    PreparedStatement stmt;
    @FXML
    void initialize() {
        con= mySqlConnectionClass.doConnect();
        if(con==null)
            System.out.println("connection not established");
        else
            System.out.println("Connection done");
    }

    void showMyMsg(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
