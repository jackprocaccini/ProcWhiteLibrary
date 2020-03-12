package model;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

import java.util.Hashtable;
import java.util.Properties;

public class Librarian implements IView, IModel {

    private ModelRegistry myRegistry;

    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String transactionErrorMessage = "";

    public Librarian(){
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        myRegistry = new ModelRegistry("Librarian");

        setDependencies();
        createAndShowLibrarianView();
    }

    private void setDependencies(){
        Properties dependencies = new Properties();
        dependencies.setProperty("NewBook", "TransactionError");
        dependencies.setProperty("NewPatron", "TransactionError");
        dependencies.setProperty("SearchBooks", "TransactionError");
        dependencies.setProperty("SearchPatrons", "TransactionError");
        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key){
        if(key.equals("TransactionError")){
            return transactionErrorMessage;
        } else {
            return "";
        }
    }

    public void stateChangeRequest(String key, Object value){
        if(key.equals("NewBook")){
            createNewBook();
        }else if(key.equals("NewPatron")){
            createNewPatron();
        } else if (key.equals("SearchBooks")) {
            createAndShowSearchView("Books");
        } else if(key.equals("DoBookSearch")){
            searchBooks((String)value);
        } else if(key.equals("SearchPatrons")){
            createAndShowSearchView("Patrons");
        } else if(key.equals("DoPatronSearch")){
            searchPatrons((String)value);
        }else if(key.equals("Done")){
            createAndShowLibrarianView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    public void createAndShowLibrarianView(){
        Scene currentScene = myViews.get("LibrarianView");

        if(currentScene == null){
            View newView = ViewFactory.createView("LibrarianView", this);
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);
    }

    public void swapToView(Scene newScene){
        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();
        WindowPosition.placeCenter(myStage);
    }

    @Override
    public void subscribe(String key, IView subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    @Override
    public void unSubscribe(String key, IView subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    @Override
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    private void createNewBook(){
        System.out.println("Create new book called");
        Book book = new Book();
        book.subscribe("LibrarianView", this);
        book.createAndShowBookView();
    }

    private void createNewPatron(){
        System.out.println("Create new patron called");
        Patron patron = new Patron();
        patron.subscribe("LibrarianView", this);
        patron.createAndShowPatronView();
    }

    private void searchBooks(String title){
        BookCollection books = new BookCollection();

        books.findBooksWithTitleLike(title);
        System.out.println(books);
        createAndShowCollectionView("Book", books);
    }

    public void createAndShowSearchView(String type){
        Scene currentScene = myViews.get("Search" + type + "View");

        if(currentScene == null){
            View newView = ViewFactory.createView("Search" + type + "View", this);
            currentScene = new Scene(newView);
            myViews.put(type + "SearchView", currentScene);
        }

        swapToView(currentScene);
    }

    public void createAndShowCollectionView(String type, Object collection){
        Scene currentScene = myViews.get(type + "CollectionView");

        if(currentScene == null){
            View newView = ViewFactory.createView(type + "CollectionView", (IModel)collection);
            currentScene = new Scene(newView);
            myViews.put(type + "CollectionView", currentScene);
        }

        swapToView(currentScene);
    }

    private void searchPatrons(String zip){
        PatronCollection patrons = new PatronCollection();

        patrons.findPatronsAtZipcode(zip);
        createAndShowCollectionView("Patron", patrons);
    }
}
