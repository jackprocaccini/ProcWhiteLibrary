package main;

import event.Event;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Librarian;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

public class Library extends Application {
    private Librarian librarian;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainStageContainer.setStage(stage, "Procaccini-White Library");
        Stage mainStage = MainStageContainer.getInstance();

        mainStage.setOnCloseRequest(e -> {
            System.exit(0);
        });

        try{
            librarian = new Librarian();
        } catch(Exception e){
            System.err.println("Could not create Librarian");
            new Event(Event.getLeafLevelClassName(this), "LibrarySystem.<init>", "Unable to create Librarian object", Event.ERROR);
            e.printStackTrace();
        }

        WindowPosition.placeCenter(mainStage);
        mainStage.show();
    }
}
