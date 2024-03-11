package controller;

import dao.CurrencyDao;
import entity.CurrencyEntity;
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
    public void addCurrency() throws SQLException {
        if (formalName.getText().equals("") || abbreviation.getText().equals("") || rateToUSD.getText().equals("")){
            error2.setText("Error: All fields must be filled");
            return;
        }
        if (rateToUSD.getText().matches("^\\d+(\\.\\d+)?$")){
            error2.setText("");
            if (DAO.checkIfAbbreviationExists(abbreviation.getText()) == 1){
                error2.setText("Error: Abbreviation already exists");
                return;
            }
            DAO.persist(new CurrencyEntity(abbreviation.getText(), formalName.getText(), Double.parseDouble(rateToUSD.getText())));
            formalName.setText("");
            abbreviation.setText("");
            rateToUSD.setText("");
            initializeData();
        } else {
            error2.setText("Error: Rate must be a number");
        }

    }
    @FXML
    public void showAddCurrencyWindow(){
       view = new CurrencyGui();
       view.showAddCurrencyWindow();
    }

    @FXML
    public void getHowTo(){
        view = new CurrencyGui();
        view.getHowTo();
    }

    @FXML
    public void initializeData(){
        choiceBox1.getItems().clear();
        choiceBox1.getItems().addAll(DAO.getRates());
        choiceBox1.setValue("EUR");
        choiceBox2.getItems().clear();
        choiceBox2.getItems().addAll(DAO.getRates());
        choiceBox2.setValue("USD");
    }

    //public
    public static void main(String[] args) {
        CurrencyGui.launch(CurrencyGui.class);
    }
}
