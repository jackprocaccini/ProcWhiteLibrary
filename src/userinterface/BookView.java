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

public class BookView extends View{
    private Label authorLabel;
    private Label titleLabel;
    private Label pubYearLabel;
    private Label statusLabel;

    private TextField authorTF;
    private TextField titleTF;
    private TextField pubYearTF;
    private ComboBox<String> statusCB;

    private Button submitButton;
    private Button cancelButton;

    private MessageView statusLog;

    public BookView(IModel book){
        super(book, "BookView");

        System.out.println("Creating book view");
        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createBody());
        statusLog = new MessageView("     ");

        container.getChildren().add(createFooter());
        container.getChildren().add(statusLog);
        getChildren().add(container);
        System.out.println("Book view successfully created");
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

        authorLabel = new Label("Book Author");
        pubYearLabel = new Label("Publication Year");
        statusLabel = new Label("Status");
        titleLabel = new Label("Book Title");
        labelsBox.getChildren().addAll(authorLabel, pubYearLabel, titleLabel, statusLabel);

        authorTF = new TextField();
        pubYearTF = new TextField();
        statusCB = new ComboBox<String>();
        statusCB.getItems().addAll("Active", " Inactive");
        titleTF = new TextField();
        tfBox.getChildren().addAll(authorTF, pubYearTF, titleTF, statusCB);

        mainBox.getChildren().addAll(labelsBox, tfBox);
        return mainBox;
    }

    private Node createTitle(){
        Text titleText = new Text("Procaccini-White Library: Insert Book");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    @Override
    public void updateState(String key, Object value) {

    }

    private void verifyInputs(){
        String author = authorTF.getText();
        String title = titleTF.getText();
        String year = pubYearTF.getText();
        String status = statusCB.getValue();
        boolean isError = false;

        if(author == null || title == null || author.equals("") || title.equals("")){
            displayErrorMessage("Author and Title fields must not be null");
            isError = true;
        }

        int yearInt = 0;
        try {
            yearInt = Integer.parseInt(year);
        } catch(Exception e){
            displayErrorMessage("Date must be an integer" + " " + year + " " + yearInt);
            isError = true;
        }

        if(yearInt < 1800 || yearInt > 2020){
            displayErrorMessage("Date must be an integer between 1800 and 2020, inclusive" + " " + year + " " + yearInt);
            isError = true;
        }

        if(status.equals("") || status == null){
            displayErrorMessage("Please select a status");
            isError = true;
        }
        if(!isError) {
            Properties props = new Properties();
            System.out.println(author + " " + year + " " + status + " " + title);
            props.setProperty("author", author);
            props.setProperty("pubYear", year);
            props.setProperty("status", status);
            props.setProperty("title", title);

            myModel.stateChangeRequest("ProcessBook", props);
            displayMessage("Book added successfully");
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
