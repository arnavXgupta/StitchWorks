package com.example.projectone.dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.projectone.mySqlConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class dashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void openCusEnroll(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/customerProf/customerProfView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Customer Profile");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openDeliveryPanel(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/orderDelivery/panelView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Order Delivery Panel");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openMeasurement(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/measurements/measurementView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Measurement Details");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openMeasurementExplorer(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/measurementExp/explorer.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Measurement Details Explorer");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openReadyProducts(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/getReadyProd/getReadyProdView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Product Status");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openWorkerConsole(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/worker/workerView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Worker Enrollrolement Console");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    void openWorkerList(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/projectone/workDatabase/workDatabaseView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage=new Stage();
            stage.setTitle("Worker Database");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            Logger logger=Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create a new window", e);
        }
    }

    @FXML
    private PieChart pieChart;

    @FXML
    void doRefresh(ActionEvent event) {

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

        int inProg = 0;
        int received=0;
        int delivered=0;

        try{
            stmt=con.prepareStatement("select count(workstatus) from measurements where workstatus=1");
            ResultSet rs=stmt.executeQuery();
            rs.next();
            inProg=rs.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try{
            stmt=con.prepareStatement("select count(workstatus) from measurements where workstatus=2");
            ResultSet rs=stmt.executeQuery();
            rs.next();
            received=rs.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        try{
            stmt=con.prepareStatement("select count(workstatus) from measurements where workstatus=3");
            ResultSet rs=stmt.executeQuery();
            rs.next();
            delivered=rs.getInt(1);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        ObservableList<PieChart.Data>data= FXCollections.observableArrayList(new PieChart.Data("In Progress", inProg), new PieChart.Data("Received", received), new PieChart.Data("Delivered", delivered));
        pieChart.setData(data);
    }
}
