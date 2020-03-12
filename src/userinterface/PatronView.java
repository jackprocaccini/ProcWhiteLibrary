package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Librarian;

import java.util.Properties;

public class PatronView extends View{
    private Label addressLabel;
    private Label emailLabel;
    private Label cityLabel;
    private Label dateOfBirthLabel;
    private Label nameLabel;
    private Label stateCodeLabel;
    private Label statusLabel;
    private Label zipLabel;

    private TextField nameTF;
    private TextField stateCodeTF;
    private TextField emailTF;
    private TextField cityTF;
    private TextField dateOfBirthTF;
    private TextField addressTF;
    private TextField zipTF;

    private ComboBox<String> statusCB;

    private Button submitButton;
    private Button cancelButton;

    private MessageView statusLog;

    public PatronView(IModel patron){
        super(patron, "PatronView");

        System.out.println("Creating patron view");
        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createBody());
        statusLog = new MessageView("     ");

        container.getChildren().add(createFooter());
        container.getChildren().add(statusLog);
        getChildren().add(container);
        System.out.println("Patron view successfully created");
    }

    private Node createFooter(){
        HBox buttonBox = new HBox(10);
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            verifyInputs();
        });

        cancelButton = new Button("Done");
        cancelButton.setOnAction(e -> {
            Librarian l = new Librarian();
            l.createAndShowLibrarianView();
        });
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        return buttonBox;
    }

    private Node createBody(){
        HBox mainBox = new HBox(10);
        VBox labelsBox = new VBox(10);
        VBox tfBox = new VBox();

        addressLabel = new Label("Address");
        cityLabel = new Label("City");
        dateOfBirthLabel = new Label("DOB (YYYY-DD-MM)");
        emailLabel = new Label("Email");
        zipLabel = new Label("Zipcode");
        nameLabel = new Label("Name");
        stateCodeLabel = new Label("State Code");
        statusLabel = new Label("Status");
        labelsBox.getChildren().addAll(nameLabel, emailLabel, dateOfBirthLabel, stateCodeLabel, cityLabel, zipLabel, addressLabel, statusLabel);

        nameTF = new TextField();
        emailTF = new TextField();
        dateOfBirthTF = new TextField();
        stateCodeTF = new TextField();
        cityTF = new TextField();
        zipTF = new TextField();
        addressTF = new TextField();
        statusCB = new ComboBox<String>();


        statusCB = new ComboBox<String>();
        statusCB.getItems().addAll("Active", " Inactive");
        tfBox.getChildren().addAll(nameTF, emailTF, dateOfBirthTF, stateCodeTF, cityTF, zipTF, addressTF, statusCB);

        mainBox.getChildren().addAll(labelsBox, tfBox);
        return mainBox;
    }

    private Node createTitle(){
        Text titleText = new Text("Procaccini-White Library: Insert Patron");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    @Override
    public void updateState(String key, Object value) {

    }

    private void verifyInputs(){
        String name = nameTF.getText();
        String email = emailTF.getText();
        String dob = dateOfBirthTF.getText();
        String stateCode = stateCodeTF.getText();
        String city = cityTF.getText();
        String zip = zipTF.getText();
        String address = addressTF.getText();
        String status = statusCB.getValue();
        boolean isError = false;

        if(name == null || address == null ||city == null || stateCode == null ||
                zip == null || email == null || dob == null || name.equals("") || address.equals("")
        || city.equals("") || stateCode.equals("") || zip.equals("") || email.equals("") || dob.equals("")){
            displayErrorMessage("Name, Address, City, State Code, Zip, Email, DOB must not be null");
            isError = true;
        }

        if(dob.compareTo("1919-01-01") == -1 || dob.compareTo("2002-01-01") == 1){
            displayErrorMessage("DOB must be between 1919-01-01 and 2002-01-01");
            isError = true;
        }

        if (!isError) {
            Properties props = new Properties();
            props.setProperty("name", name);
            props.setProperty("address", address);
            props.setProperty("city", city);
            props.setProperty("stateCode", stateCode);
            props.setProperty("zip", zip);
            props.setProperty("email", email);
            props.setProperty("dateOfBirth", dob);
            props.setProperty("status", status);


            myModel.stateChangeRequest("ProcessPatron", props);
            displayMessage("Patron added successfully");
        }

    }

    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    private void displayMessage(String message) {
        statusLog.displayMessage(message);
    }
}
