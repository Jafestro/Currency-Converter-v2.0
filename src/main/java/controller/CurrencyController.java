package controller;

import dao.CurrencyDao;
import gui.CurrencyGui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class CurrencyController {
    private CurrencyGui view;
    private final CurrencyDao DAO = new CurrencyDao();
    @FXML
    private ChoiceBox<String> choiceBox1;
    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private TextField inputField;
    @FXML
    private TextField outputField;
    @FXML
    private Button convertButton;
    @FXML
    private Button howToUseButton;
    @FXML
    private Label error;

    public CurrencyController(CurrencyGui view) {
        this.view = view;
    }
    public CurrencyController(){
        this.view = null;
    }

    @FXML
    public void convert() throws SQLException {
        if (inputField.getText().matches("^\\d+(\\.\\d+)?$") && !inputField.getText().equals("") ) {
            System.out.println(inputField.getText());
            inputField.getStyleClass().add("valid-input");
            inputField.getStyleClass().remove("invalid-input");
            double amount = Double.parseDouble(inputField.getText());
            double rate1 = DAO.getRateByAbbreviation(choiceBox1.getValue());
            double rate2 = DAO.getRateByAbbreviation(choiceBox2.getValue());
            if (rate2 == 0 || rate1 == 0){
                error.setText("Error: No data found for " + choiceBox2.getValue());
                return;
            } else if ( rate2 == -1 || rate1 == -1) {
                error.setText("Error: Connection failed");
                return;
            }
            String result = String.format("%.3f",((amount / rate2) * rate1));
            outputField.setText(result);
        } else {
            System.out.println("Invalid input");
            inputField.getStyleClass().add("invalid-input");
            inputField.getStyleClass().remove("valid-input");
        }
    }

    @FXML
    public void getHowTo(){
        view = new CurrencyGui();
        view.getHowTo();
    }

    public void initializeData(){
        choiceBox1.getItems().addAll("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD", "NOK");
        choiceBox1.setValue("EUR");
        choiceBox2.getItems().addAll("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD", "NOK");
        choiceBox2.setValue("USD");
    }

    //public
    public static void main(String[] args) {
        CurrencyGui.launch(CurrencyGui.class);
    }
}
