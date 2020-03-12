package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SearchPatronsView extends View{
    private Label zipLabel;

    private TextField zipTF;

    private Button submitButton;
    private Button cancelButton;

    private MessageView statusLog;

    public SearchPatronsView(IModel librarian){
        super(librarian, "SearchPatronsView");

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createBody());
        statusLog = new MessageView("     ");

        container.getChildren().add(createFooter());
        container.getChildren().add(statusLog);
        getChildren().add(container);
    }

    private Node createFooter(){
        HBox buttonBox = new HBox(10);
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            search();
        });

        cancelButton = new Button("Done");
        cancelButton.setOnAction(e -> {
            myModel.stateChangeRequest("Done", "");
        });
        buttonBox.getChildren().addAll(submitButton, cancelButton);
        return buttonBox;
    }

    private Node createBody(){
        HBox mainBox = new HBox(10);
        VBox labelsBox = new VBox(10);
        VBox tfBox = new VBox();

        zipLabel = new Label("Zip Code");
        labelsBox.getChildren().addAll(zipLabel);
        zipTF = new TextField();


        tfBox.getChildren().addAll(zipTF);

        mainBox.getChildren().addAll(labelsBox, tfBox);
        return mainBox;
    }

    private Node createTitle(){
        Text titleText = new Text("Procaccini-White Library: Search Patrons");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    @Override
    public void updateState(String key, Object value) {

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

    private void search(){
        if(zipTF.getText().equals("") || zipTF.getText() == null){
            displayErrorMessage("Search Criteria must not be null");
        }else{
            myModel.stateChangeRequest("DoPatronSearch", zipTF.getText());
        }
    }
}
