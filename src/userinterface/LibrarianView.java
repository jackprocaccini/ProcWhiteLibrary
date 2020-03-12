package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LibrarianView extends View {
    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBooksButton;
    private Button searchPatronsButton;

    private MessageView statusLog;

    public LibrarianView(IModel librarian){
        super(librarian, "LibrarianView");
        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 10, 10, 10));
        container.getChildren().add(createTitle());
        container.getChildren().add(createButtons());
        container.getChildren().add(createStatusLog("     "));

        getChildren().add(container);

        myModel.subscribe("TransactionError", this);
    }

    private Node createTitle(){
        Text titleText = new Text("Procaccini-White Library");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);

        return titleText;
    }

    private Node createButtons(){
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        insertBookButton = new Button("Insert Book");
        insertPatronButton = new Button("Insert Patron");
        searchBooksButton = new Button("Search Books");
        searchPatronsButton = new Button("Search Patrons");

        insertBookButton.setOnAction(e -> {
            myModel.stateChangeRequest("NewBook", null);
        });

        insertPatronButton.setOnAction(e -> {
            myModel.stateChangeRequest("NewPatron", null);
        });

        searchBooksButton.setOnAction(e -> {
            myModel.stateChangeRequest("SearchBooks", null);
        });

        searchPatronsButton.setOnAction(e -> {
            myModel.stateChangeRequest("SearchPatrons", null);
        });

        buttonBox.getChildren().addAll(insertBookButton, insertPatronButton, searchBooksButton, searchPatronsButton);

        return buttonBox;
    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("TransactionError")){
            displayErrorMessage((String)value);
        }
    }

    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    private MessageView createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }
}
