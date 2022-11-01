module com.example.double_six_game_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.double_six_game_gui to javafx.fxml;
    exports com.example.double_six_game_gui;
}