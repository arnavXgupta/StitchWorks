package com.example.projectone.measureExp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import com.example.projectone.measureExp.measureBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class expController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField getMob;

    @FXML
    private ComboBox<String> getOrderStatus;

    @FXML
    private TableView<measureBean> getRecords;

    @FXML
    private ComboBox<String> getWorker;

    void getEveryRecord(){
        TableColumn<measureBean, String> id=new TableColumn<measureBean, String>("Order ID");
        id.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        id.setMinWidth(50);

        TableColumn<measureBean, String> mble=new TableColumn<measureBean, String>("Mobile No.");
        mble.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mble.setMinWidth(100);

        TableColumn<measureBean, String> drs=new TableColumn<measureBean, String>("Dress");
        drs.setCellValueFactory(new PropertyValueFactory<>("dress"));
        drs.setMinWidth(100);

        TableColumn<measureBean, String> ddel=new TableColumn<measureBean, String>("Date of Delivery");
        ddel.setCellValueFactory(new PropertyValueFactory<>("dodel"));
        ddel.setMinWidth(100);

        TableColumn<measureBean, String> wrkr=new TableColumn<measureBean, String>("Worker Assigned");
        wrkr.setCellValueFactory(new PropertyValueFactory<>("worker"));
        wrkr.setMinWidth(100);

        TableColumn<measureBean, String> sts=new TableColumn<measureBean, String>("Work Status");
        sts.setCellValueFactory(new PropertyValueFactory<>("workstatus"));
        sts.setMinWidth(100);

        getRecords.getColumns().addAll(id, mble, drs, ddel, wrkr, sts);
    }

    ObservableList<measureBean> showAll(){
        ObservableList<measureBean> ary= FXCollections.observableArrayList();
        try{
            stmt=con.prepareStatement("select orderid, mobile, dress, dodel, worker, workstatus from measurements");
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Integer oid=rs.getInt("orderid");;
                String mobile=rs.getString("mobile");
                String dress=rs.getString("dress");
                Date dodel=rs.getDate("dodel");
                String worker=rs.getString("worker");
                Integer workstatus=rs.getInt("workstatus");

                ary.add(new measureBean(oid,mobile,dress,dodel,worker,workstatus));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return ary;
    }

    @FXML
    void doExport(ActionEvent event) {
        try{
            writeExcel();
            System.out.println("exported");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeExcel() throws Exception{
        Writer writer=null;
        try{
            File file=new File("Users.csv");
            writer=new BufferedWriter(new FileWriter(file));
            String text="Order ID,Mobile NO.,Dress,Delivery Date,Worker Assigned,Order Status \n";
            writer.write(text);
            for(measureBean p: showAll())
            {
                text=p.getOrderid()+","+p.getMobile()+","+p.getDress()+","+p.getDodel()+","+p.getWorker()+","+p.getWorkstatus()+"\n";
                writer.write(text);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            writer.flush();
            writer.close();
        }
    }

    ObservableList<measureBean> fetchMob(){
        ObservableList<measureBean> aryy= FXCollections.observableArrayList();
        try{
            stmt=con.prepareStatement("select orderid, mobile, dress, dodel, worker, workstatus from measurements where mobile=?");
            stmt.setString(1,getMob.getText());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Integer oid=rs.getInt("orderid");;
                String mobile=rs.getString("mobile");
                String dress=rs.getString("dress");
                Date dodel=rs.getDate("dodel");
                String worker=rs.getString("worker");
                Integer workstatus=rs.getInt("workstatus");

                aryy.add(new measureBean(oid,mobile,dress,dodel,worker,workstatus));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return aryy;
    }

    //Fetch via Mobile No.
    @FXML
    void doFetch(ActionEvent event) {

        getRecords.getColumns().clear();
        getRecords.getItems().clear();

        getEveryRecord();
        getRecords.setItems(fetchMob());
    }

    @FXML
    void doShowAll(ActionEvent event) {
//        oid,mobile,dress,dodel,worker,workstatus

        getRecords.getColumns().clear();
        getRecords.getItems().clear();

        getEveryRecord();
        getRecords.setItems(showAll());
    }

    @FXML
    void getViaOrderStatus(ActionEvent event) {
        getRecords.getColumns().clear();
        getRecords.getItems().clear();

        getEveryRecord();
        getRecords.setItems(viaOrderStatus());
    }

    ObservableList<measureBean> viaOrderStatus(){
        ObservableList<measureBean> ary= FXCollections.observableArrayList();
        try{
            stmt=con.prepareStatement("select orderid, mobile, dress, dodel, worker, workstatus from measurements where workstatus=?");
            stmt.setString(1, getOrderStatus.getSelectionModel().getSelectedItem());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Integer oid=rs.getInt("orderid");;
                String mobile=rs.getString("mobile");
                String dress=rs.getString("dress");
                Date dodel=rs.getDate("dodel");
                String worker=rs.getString("worker");
                Integer workstatus=rs.getInt("workstatus");

                ary.add(new measureBean(oid,mobile,dress,dodel,worker,workstatus));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return ary;
    }

    @FXML
    void getViaWorker(ActionEvent event) {
        getRecords.getColumns().clear();
        getRecords.getItems().clear();

        getEveryRecord();
        getRecords.setItems(viaWorker());
    }

    ObservableList<measureBean> viaWorker(){
        ObservableList<measureBean> ary= FXCollections.observableArrayList();
        try{
            stmt=con.prepareStatement("select orderid, mobile, dress, dodel, worker, workstatus from measurements where worker=?");
            stmt.setString(1, getWorker.getSelectionModel().getSelectedItem());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Integer oid=rs.getInt("orderid");;
                String mobile=rs.getString("mobile");
                String dress=rs.getString("dress");
                Date dodel=rs.getDate("dodel");
                String worker=rs.getString("worker");
                Integer workstatus=rs.getInt("workstatus");

                ary.add(new measureBean(oid,mobile,dress,dodel,worker,workstatus));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return ary;
    }

    PreparedStatement stmt;
    Connection con=null;
    @FXML
    void initialize() {
        con= mySqlConnectionClass.doConnect();
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

        String status[]={"Select order status", "1", "2", "3"};
        getOrderStatus.getItems().addAll(Arrays.asList(status));
        getOrderStatus.getSelectionModel().select(0);
    }
}
