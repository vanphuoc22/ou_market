module com.tdkhoa.oumarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tdkhoa.oumarket to javafx.fxml;
    exports com.tdkhoa.oumarket;
    exports pojo;
    exports services;
    exports utils;
}
