package controller;

import dao.CurrencyDao;
import dao.TransactionDao;
import entity.CurrencyEntity;
import entity.TransactionEntity;
import gui.CurrencyGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CurrencyController {
    private CurrencyGui view;
    private final CurrencyDao cDAO = new CurrencyDao();
    private final TransactionDao tDAO = new TransactionDao();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> choiceBox1;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private TextField rateToUSD;
    @FXML
    private TextField formalName;
    @FXML
    private TextField abbreviation;
    @FXML
    private TextField inputField;
    @FXML
    private TextField outputField;
    @FXML
    private Button convertButton;
    @FXML
    private Button howToUseButton;
    @FXML
    private Button addCurrencyButton;
    @FXML
    private Button addCButton;
    @FXML
    private Label error;
    @FXML
    private Label error2;

    public CurrencyController(CurrencyGui view) {
        this.view = view;
    }

    public CurrencyController() {
        this.view = null;
    }

    public void switchToMainScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/converterGui.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        CurrencyController controller = loader.getController();
        controller.initializeData();
        scene.getStylesheets().add("style.css");
        stage.setTitle("Currency Converter");
        stage.getIcons().add(new Image("dollar.png"));
        stage.setScene(scene);
        stage.show();
    }
    public void switchToHowToScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/howToUse.fxml"));
        BorderPane root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add("style.css");
        stage.setTitle("How to use");
        stage.getIcons().add(new Image("dollar.png"));
        Image image = new Image("howToUse.png");
        ImageView imageView = new ImageView(image);
        root.setCenter(imageView);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAddCurrencyScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/addCurrency.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Add Currency");
        stage.getIcons().add(new Image("dollar.png"));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void convert() throws SQLException {
        if (inputField.getText().matches("^\\d+(\\.\\d+)?$") && !inputField.getText().equals("")) {
            System.out.println(inputField.getText());
            inputField.getStyleClass().add("valid-input");
            inputField.getStyleClass().remove("invalid-input");
            double amount = Double.parseDouble(inputField.getText());
            double rate1 = cDAO.getRateByAbbreviation(choiceBox1.getValue());
            double rate2 = cDAO.getRateByAbbreviation(choiceBox2.getValue());
            if (rate2 == 0 || rate1 == 0) {
                error.setText("Error: No data found for " + choiceBox2.getValue());
                return;
            } else if (rate2 == -1 || rate1 == -1) {
                error.setText("Error: Connection failed");
                return;
            }
            TransactionEntity transaction = new TransactionEntity
                    (cDAO.getCurrencyByAbbreviation(choiceBox1.getValue()),
                    cDAO.getCurrencyByAbbreviation(choiceBox2.getValue()));
            tDAO.persist(transaction);
            String result = String.format("%.3f", ((amount / rate2) * rate1));
            outputField.setText(result);
        } else {
            System.out.println("Invalid input");
            inputField.getStyleClass().add("invalid-input");
            inputField.getStyleClass().remove("valid-input");
        }
    }

    @FXML
    public void addCurrency() throws SQLException {
        if (formalName.getText().equals("") || abbreviation.getText().equals("") || rateToUSD.getText().equals("")) {
            error2.setText("Error: All fields must be filled");
            return;
        }
        if (rateToUSD.getText().matches("^\\d+(\\.\\d+)?$")) {
            error2.setText("");
            if (cDAO.checkIfAbbreviationExists(abbreviation.getText()) == 1) {
                error2.setText("Error: Abbreviation already exists");
                return;
            }
            cDAO.persist(new CurrencyEntity(abbreviation.getText(), formalName.getText(), Double.parseDouble(rateToUSD.getText())));
            formalName.setText("");
            abbreviation.setText("");
            rateToUSD.setText("");
        } else {
            error2.setText("Error: Rate must be a number");
        }

    }

    @FXML
    public void initializeData() {
        choiceBox1.getItems().clear();
        if (cDAO.getRates().isEmpty()) {
            error.setText("Error: Database is empty populate it with currencies first");
            return;
        }
        if (cDAO.getRates().get(0).equals("-1")) {
            error.setText("Error: Connection failed");
            return;
        }
        choiceBox1.getItems().addAll(cDAO.getRates());
        choiceBox1.setValue("EUR");
        choiceBox2.getItems().clear();
        choiceBox2.getItems().addAll(cDAO.getRates());
        choiceBox2.setValue("USD");
    }

    //public
    public static void main(String[] args) {
        CurrencyGui.launch(CurrencyGui.class);
    }
}
