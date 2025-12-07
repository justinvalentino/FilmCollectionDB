module org.example.filmcollectiondb {
    requires javafx.controls;
    requires javafx.fxml;


    // TO CONNECT WITH SQL
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.example.filmcollectiondb to javafx.fxml;
    exports org.example.filmcollectiondb;
}