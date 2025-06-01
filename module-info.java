module Yutnori {
    requires java.desktop;

    requires javafx.controls;
    requires javafx.fxml;   
    requires javafx.graphics;  

    opens ui.javafx to javafx.graphics;

    opens model to javafx.base;

    exports app; 
}