package com.example.projectone.workerShow;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.example.projectone.mySqlConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showWorkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> getDress;

    @FXML
    private TableView<workerBean> showData;

//    wname, address, mobile, splz
    @FXML
    void showWorker(ActionEvent event) {
        TableColumn<workerBean, String> naam=new TableColumn<workerBean, String>("Name");
        naam.setCellValueFactory(new PropertyValueFactory<>("wname"));
        naam.setMinWidth(100);

        TableColumn<workerBean, String> adrs=new TableColumn<workerBean, String>("Address");
        adrs.setCellValueFactory(new PropertyValueFactory<>("address"));
        adrs.setMinWidth(150);

        TableColumn<workerBean, String> mble=new TableColumn<workerBean, String>("Mobile No.");
        mble.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        mble.setMinWidth(150);

        TableColumn<workerBean, String> special=new TableColumn<workerBean, String>("Specialization");
        special.setCellValueFactory(new PropertyValueFactory<>("splz"));
        special.setMinWidth(150);

        showData.getColumns().addAll(naam, adrs, mble, special);
        showData.setItems(showAll());
    }

    ObservableList<workerBean> getRecords(String input1)

    {
        ObservableList<workerBean> ary= FXCollections.observableArrayList();
        try
        {
            stmt=con.prepareStatement("select * from workers where splz like ?");
            stmt.setString(1,"%" + input1 +"%");
            ResultSet records= stmt.executeQuery();
            while (records.next())
            {
                String wn=records.getString("wname");//col name
                String add=records.getString("address");//col name
                String mb=records.getString("mobile");//col name
                String sp=records.getString("splz");//col name
                // String gd=records.getString("gender");//col name

                // Date dt=records.getDate("dob");//col name
                ary.add(new workerBean(wn,add,mb,sp));

                //System.out.println(mn+"  "+nm+"  "+ad+"  "+gd+"  "+ct+"  "+dt);
            }
        }
        catch (Exception exp)
        {
            exp.printStackTrace();
        }
//        System.out.println(ary.size());
        return  ary;
    }

    ObservableList<workerBean> showAll(){
        ObservableList<workerBean> ary= FXCollections.observableArrayList();
        try{
            stmt=con.prepareStatement("select * from workers");
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                String nm=rs.getString("wname");
                String ad=rs.getString("address");
                String mb=rs.getString("mobile");
                String sp=rs.getString("splz");

                ary.add(new workerBean(nm,ad,mb,sp));
//                System.out.println(nm+" "+ad+" "+mb+" "+sp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return ary;
    }

    @FXML
    void selectSplz(ActionEvent event) {

//                if(getDress.getSelectionModel().getSelectedItem()!=null){
                    TableColumn<workerBean, String> naam=new TableColumn<workerBean, String>("Name");
                    naam.setCellValueFactory(new PropertyValueFactory<>("wname"));
                    naam.setMinWidth(100);

                    TableColumn<workerBean, String> adrs=new TableColumn<workerBean, String>("Address");
                    adrs.setCellValueFactory(new PropertyValueFactory<>("address"));
                    adrs.setMinWidth(150);

                    TableColumn<workerBean, String> mble=new TableColumn<workerBean, String>("Mobile No.");
                    mble.setCellValueFactory(new PropertyValueFactory<>("mobile"));
                    mble.setMinWidth(120);

                    TableColumn<workerBean, String> special=new TableColumn<workerBean, String>("Specialization");
                    special.setCellValueFactory(new PropertyValueFactory<>("splz"));
                    special.setMinWidth(230);

                    showData.getColumns().addAll(naam, adrs, mble, special);
                    showData.setItems(getRecords(getDress.getValue()));
//                }
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

        String dress[]={"Select", "Pant", "Kurta", "Coord Set", "Shirt", "Palazzo"};
        getDress.getItems().addAll(Arrays.asList((dress)));
        getDress.getSelectionModel().select(0);
    }

}
