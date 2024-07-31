package com.example.projectone.getProdReady;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class prodReadyController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<LocalDate> getDOD;

    @FXML
    private ListView<String> getDress;

    @FXML
    private ListView<String> getOrderID;

    @FXML
    private ComboBox<String> getWorker;

    @FXML
    void doStatusUpdate(ActionEvent event) {
        try{
            stmt=con.prepareStatement("update measurements set workstatus=2 where workstatus=1 AND worker=?");
            stmt.setString(1, getWorker.getValue());
            stmt.executeUpdate();
            details();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void doRemove(MouseEvent event) {
        if(event.getClickCount()==2){
            try{
                stmt=con.prepareStatement("update measurements set workstatus=2 where workstatus=1 AND orderid=?");
                stmt.setString(1,getOrderID.getSelectionModel().getSelectedItem());
                stmt.executeUpdate();
                details();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    void details(){
        String selectedWorker=getWorker.getSelectionModel().getSelectedItem();
        getDOD.getItems().clear();
        getOrderID.getItems().clear();
        getDress.getItems().clear();

        try{
            stmt=con.prepareStatement("select orderid, dress , dodel from measurements where workstatus=1 and worker=?");
            stmt.setString(1, selectedWorker);
//            stmt=con.prepareStatement("select orderid, dress , dodel from measurements where workstatus=1");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String orderid = rs.getString("orderid");
                String dress = rs.getString("dress");
                LocalDate local = rs.getDate("dodel").toLocalDate();

                getDOD.getItems().add(local);
                getOrderID.getItems().add(orderid);
                getDress.getItems().add(dress);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void getDetails(ActionEvent event) {

//        add work assigned to worker in the 3 lists
        if(getWorker.getSelectionModel().getSelectedIndex()!=0){
            details();
        }
    }

    PreparedStatement stmt;
    Connection con=null;
    @FXML
    void initialize() {
        con=mySqlConnectionClass.doConnect();
        if(con==null)
            System.out.println("connection not established");
        else
            System.out.println("Connection done");

//        add worker names in list from worker database
        String work[]={"Select Worker"};
        getWorker.getItems().addAll(Arrays.asList(work));
        getWorker.getSelectionModel().select(0);
        try {
            stmt=con.prepareStatement("Select wname from workers");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String name = rs.getString("wname");
                getWorker.getItems().add(name);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
