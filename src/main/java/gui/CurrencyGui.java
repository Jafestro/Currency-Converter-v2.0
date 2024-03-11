package gui;

import controller.CurrencyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CurrencyGui extends Application {

    private CurrencyController controller;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/converterGui.fxml"));

        VBox parent = loader.load();
        controller = loader.getController();

        controller.initializeData();

        Scene scene = new Scene(parent);
        scene.getStylesheets().add("style.css");

        stage.setTitle("Currency Converter");
        stage.getIcons().add(new Image("dollar.png"));
        stage.setScene(scene);
        stage.show();
    }

//    public void showAddCurrencyWindow() {
//        try {
//            // Create a new stage
//            Stage stage2 = new Stage();
//
//            // Load the FXML file for the new window
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addCurrency.fxml"));
//            loader.setController(controller);
//            HBox root = loader.load();
//
//            // Set the scene and show the stage
//            Scene scene = new Scene(root);
//
//            stage2.setScene(scene);
//            stage2.setTitle("Add Currency");
//            stage2.getIcons().add(new Image("dollar.png"));
//
//            stage2.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void getHowTo() {
//        try {
//            // Create a new stage
//            Stage stageHowTo = new Stage();
//
//            // Load the FXML file for the new window
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/howToUse.fxml"));
//            BorderPane root = loader.load();
//
//            Image image = new Image("howToUse.png");
//            ImageView imageView = new ImageView(image);
//            root.setCenter(imageView);
//
//            // Set the scene and show the stage
//            Scene scene = new Scene(root, 1000, 700);
//
//            stageHowTo.setScene(scene);
//            stageHowTo.setTitle("How to Use");
//            stageHowTo.getIcons().add(new Image("dollar.png"));
//
//
//
//            stageHowTo.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void init() {
        controller = new CurrencyController(this);
    }
}
