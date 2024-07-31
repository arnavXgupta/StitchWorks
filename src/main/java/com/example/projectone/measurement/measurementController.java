package com.example.projectone.measurement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class measurementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dod;

    @FXML
    private ComboBox<String> getDress;

    @FXML
    private TextField getPrice;

    @FXML
    private ComboBox<String> getQuantity;

    @FXML
    private ComboBox<String> getWorkers;

    @FXML
    private TextField getbill;

    @FXML
    private TextField getmobile;

    @FXML
    private ImageView imgprev;

    @FXML
    private TextArea measureDetail;

    @FXML
    private TextField orderID;

    @FXML
    void doClose(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void doExist(ActionEvent event) {
        try{
            stmt=con.prepareStatement("select measurementDetail from measurements where orderID=?");
            stmt.setString(1, orderID.getText());
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                String mDetail=rs.getString("measurementDetail");

                measureDetail.setText(mDetail);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void doNew(ActionEvent event) {
        getmobile.setText("");
        getbill.setText("");
        getPrice.setText("");
        measureDetail.setText("");
        orderID.setText("");
        getQuantity.getEditor().setText("");
        dod.getEditor().setText("");
        getDress.getEditor().setText("");
    }

    PreparedStatement stmt;
    @FXML
    void doSave(ActionEvent event) {
//        orderid, mobile, dress, pic, dodel, qty, bill, measurementDetail, worker,doorder, workstatus

        try {
            // Prepare the SQL insert statement, omitting the orderid column
            stmt = con.prepareStatement("INSERT INTO measurements (mobile, dress, pic, dodel, qty, bill, measurementDetail, worker, doorder, workstatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, current_date, 1)", Statement.RETURN_GENERATED_KEYS);

            // Set the values for the placeholders
            stmt.setString(1, getmobile.getText());
            stmt.setString(2, getDress.getValue());
            stmt.setString(3, filePath);

            LocalDate local = dod.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(local);
            stmt.setDate(4, sqlDate);

            stmt.setInt(5, Integer.parseInt(getQuantity.getSelectionModel().getSelectedItem()));
            stmt.setFloat(6, Float.parseFloat(getbill.getText()));
            stmt.setString(7, measureDetail.getText());
            stmt.setString(8, getWorkers.getSelectionModel().getSelectedItem());

            // Execute the update
            stmt.executeUpdate();

            // Retrieve the generated orderid
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedOrderId = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void doSearch(ActionEvent event) {
//        orderid, mobile, dress, pic, dodel, qty, bill, measurementDetail, worker, doorder
        try{
            stmt=con.prepareStatement("select * from measurements where orderID=?");
            stmt.setString(1, orderID.getText());
            ResultSet record=stmt.executeQuery();
            while(record.next()){
                String mb=record.getString("mobile");
                String dr=record.getString("dress");
                String picp=record.getString("pic");
                Date ddel=record.getDate("dodel");
                int qtyy=Integer.parseInt(record.getString("qty"));
                int billl=Integer.parseInt(record.getString("bill"));
                String meas=record.getString("measurementDetail");
                String work=record.getString("worker");

                getDress.getEditor().setText(dr);
                getmobile.setText(mb);

                filePath=picp;
                imgprev.setImage(new Image(new FileInputStream(filePath)));

                dod.setValue(ddel.toLocalDate());

                getQuantity.getEditor().setText(String.valueOf(qtyy));
                getbill.setText(String.valueOf(billl));
                measureDetail.setText(meas);
                getWorkers.getEditor().setText(work);
            }
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @FXML
    void doUpdate(ActionEvent event) {
        //        orderid, mobile, dress, pic, dodel, qty, bill, measurementDetail, worker, doorder

        try {
            stmt = con.prepareStatement("update profile set mobile=?, dress=?, pic=?, dodel=?, qty=?, bill=?, measurementDetail=?, worker=? where orderID=?");

            stmt.setString(1, getmobile.getText());
            stmt.setString(2, getDress.getValue());
            stmt.setString(3, filePath);
//            stmt.setFloat(3, Float.parseFloat(bal.getText()));

            LocalDate local=dod.getValue();
            java.sql.Date sqlDate=java.sql.Date.valueOf(local);
            stmt.setDate(4, sqlDate);

            stmt.setInt(5, Integer.parseInt(getQuantity.getSelectionModel().getSelectedItem()));

            stmt.setFloat(6, Float.parseFloat(getbill.getText()));
            stmt.setString(7, measureDetail.getText());

            stmt.setString(8, getWorkers.getSelectionModel().getSelectedItem());

            stmt.setString(9, orderID.getText());

            stmt.executeUpdate();
            showMyMsg("Record Updated successfully");
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    String filePath="nopic.jpg";

    @FXML
    void doUpload(ActionEvent event) {

        FileChooser chooser=new FileChooser();

        chooser.setTitle("Select Profile Pic: ");

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("*.*", "*.*")
        );
        File file=chooser.showOpenDialog(null);
        filePath=file.getAbsolutePath();

        try{
            imgprev.setImage(new Image(new FileInputStream(file)));
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }

    }

    @FXML
    void dobill(KeyEvent event) {
        try {
            if (getQuantity.getSelectionModel().getSelectedIndex() > 0) {
                int qt = Integer.parseInt(getQuantity.getSelectionModel().getSelectedItem());
                float pr = Float.parseFloat(getPrice.getText());
                float bill = qt * pr;
                getbill.setText(Float.toString(bill));
            } else {
                getbill.setText("0");
            }
        } catch (NumberFormatException e) {
            getbill.setText("Invalid input");
        }
    }

    Connection con=null;
    @FXML
    void initialize() {
        con= mySqlConnectionClass.doConnect();
        if(con==null)
            System.out.println("connection not established");
        else
            System.out.println("Connection done");

        String dress[]={"Select", "Pant", "Kurta", "Coord Set", "Shirt", "Palazzo"};
        getDress.getItems().addAll(Arrays.asList((dress)));
        getDress.getSelectionModel().select(0);

        String qty[]={"Select Quantity", "1", "2", "3", "4", "5"};
        getQuantity.getItems().addAll(Arrays.asList(qty));
        getQuantity.getSelectionModel().select(0);


//        add worker names in list from worker database
        String work[]={"Select Worker"};
        getWorkers.getItems().addAll(Arrays.asList(work));
        getWorkers.getSelectionModel().select(0);
        try {
            stmt=con.prepareStatement("Select wname from workers");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String name = rs.getString("wname");
                getWorkers.getItems().add(name);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        String selectedDress=getDress.getSelectionModel().getSelectedItem();
        if(selectedDress != null){
            try {
                stmt=con.prepareStatement("Select wname from workers where splz like ?");
                stmt.setString(1, "%" + selectedDress + "%");
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    String name = rs.getString("wname");
                    getWorkers.getItems().add(name);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    void showMyMsg(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Header");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}