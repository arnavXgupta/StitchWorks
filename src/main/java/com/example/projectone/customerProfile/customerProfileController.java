package com.example.projectone.customerProfile;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.regex.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class customerProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private DatePicker dob;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField mob;

    @FXML
    private TextField name;

    @FXML
    void doClear(ActionEvent event) {
        name.clear();
        mob.clear();
        address.clear();
        city.clear();
        dob.setValue(null);
        gender.getItems().clear();
    }

    @FXML
    void doEdit(ActionEvent event) {

        try{
            stmt=con.prepareStatement("update customerenroll set cname=?, address=?, city=?, gender=?, dob=? where mobile=?");
            stmt.setString(1, name.getText());
            stmt.setString(2, address.getText());
            stmt.setString(3, city.getText());
            stmt.setString(4, gender.getSelectionModel().getSelectedItem());

            LocalDate local=dob.getValue();
            java.sql.Date sqlDate=java.sql.Date.valueOf(local);
            stmt.setDate(5, sqlDate);

            stmt.setString(6, mob.getText());

            stmt.executeUpdate();
            showMyMsg("Record Updated");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    PreparedStatement stmt;

    @FXML
    void doEnroll(ActionEvent event) {

        //    mobile, cname, address, city, gender, dob, doenroll
        try{
            stmt=con.prepareStatement("Insert into customerenroll values(?,?,?,?,?,?,current_date)");
            stmt.setString(1, name.getText());
            stmt.setString(2, mob.getText());
            stmt.setString(3, address.getText());
            stmt.setString(4, city.getText());
            stmt.setString(5, gender.getSelectionModel().getSelectedItem());

            LocalDate local=dob.getValue();
            java.sql.Date sqlDate=java.sql.Date.valueOf(local);
            stmt.setDate(6, sqlDate);

            //mobile number to check
            String str = mob.getText();
            //method calling
            if (isValidMobileNo(str)) {
                stmt.executeUpdate();
                showMyMsg("Record Inserted");
            }
            else
                showMyMsg("invalid mobile no.");

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void doFetch(ActionEvent event) {
        //    mobile, cname, address, city, gender, dob, doenroll
        try{
            stmt=con.prepareStatement("select * from customerenroll where mobile=?");
            stmt.setString(1, mob.getText());
            ResultSet record=stmt.executeQuery();
            while(record.next()){
                String namee=record.getString("cname");
                String addr=record.getString("address");
                String cityy=record.getString("city");
                String genderr=record.getString("gender");
                Date dtob=record.getDate("dob");

                System.out.println(namee+" "+addr+" "+cityy+" "+genderr+" "+dtob);

                name.setText(namee);
                address.setText(addr);
                city.setText(cityy);
                gender.getEditor().setText(genderr);
                dob.setValue(dtob.toLocalDate());
            }
        }
        catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //function to check if the mobile number is valid or not
    public static boolean isValidMobileNo(String str)
    {
            //(0/91): number starts with (0/91)
            //[7-9]: starting of the number may contain a digit between 0 to 9
            //[0-9]: then contains digits 0 to 9
            Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
            //the matcher() method creates a matcher that will match the given input against this pattern
            Matcher match = ptrn.matcher(str);
            //returns a boolean value
            return (match.find() && match.group().equals(str));
    }

    Connection con;

    @FXML
    void initialize() {
        con= mySqlConnectionClass.doConnect();
        if(con==null)
            System.out.println("connection not established");
        else
            System.out.println("Connection done");

        String gen[]={"Select", "Male", "Female", "Prefer not to say"};
        gender.getItems().addAll(Arrays.asList(gen));
        gender.getSelectionModel().select(0);
    }

    void showMyMsg(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
