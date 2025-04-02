/**
 * Description: requires external packages needed for application
 */
module com.example.dndcms {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.dndcms to javafx.fxml;
    exports com.example.dndcms;
}