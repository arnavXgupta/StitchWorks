module com.example.projectone {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mail;

    opens com.example.projectone to javafx.fxml;
    exports com.example.projectone;

    exports com.example.projectone.customerProfile;
    opens com.example.projectone.customerProfile to javafx.fxml;

    exports com.example.projectone.workerEnroll;
    opens com.example.projectone.workerEnroll to javafx.fxml;

    exports com.example.projectone.measurement;
    opens com.example.projectone.measurement to javafx.fxml;

    exports com.example.projectone.getProdReady;
    opens com.example.projectone.getProdReady to javafx.fxml;

    exports com.example.projectone.measureExp;
    opens com.example.projectone.measureExp to javafx.fxml;

    exports com.example.projectone.workerShow;
    opens com.example.projectone.workerShow to javafx.fxml;

    exports com.example.projectone.orderDelPanel;
    opens com.example.projectone.orderDelPanel to javafx.fxml;

    exports com.example.projectone.dashboard;
    opens com.example.projectone.dashboard to javafx.fxml;

    exports com.example.projectone.admin;
    opens com.example.projectone.admin to javafx.fxml;
}