package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConverterGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("converterGui.fxml"));
        VBox parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("Currency Converter");
        stage.getIcons().add(new Image("dollar.png"));
        stage.setScene(scene);
        stage.show();
    }
}
