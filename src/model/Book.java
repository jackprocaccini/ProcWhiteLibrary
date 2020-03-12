package model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Book extends EntityBase implements IView {

    private static final String myTableName = "Book";
    private String updateStatusMessage = "";

    private Properties dependencies;

    public Book(String bookID) throws InvalidPrimaryKeyException{
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (bookID = " + bookID + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if(allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            if(size != 1) {
                throw new InvalidPrimaryKeyException("Multiple books matching id : "
                        + bookID + " found.");
            } else {
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();

                while(allKeys.hasMoreElements()) {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if(nextValue != null) {
                        persistentState.setProperty(nextKey,  nextValue);
                    }
                }
            }
        } else {
            throw new InvalidPrimaryKeyException("No account matching id : "
                    + bookID + " found.");
        }
    }

    public Book(Properties props) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements())
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Book(){
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    private void setDependencies() {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        } else {
            return persistentState.getProperty(key);
        }
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        if(key.equals("ProcessBook")){
            processBook(value);
        }else{
            myRegistry.updateSubscribers(key, this);
        }
    }

    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public void update() {
        updateStateInDatabase();
    }

    private void updateStateInDatabase() {
        try {
            if(persistentState.getProperty("bookID") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("bookID", persistentState.getProperty("bookID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for book number : " + persistentState.getProperty("bookID") + " updated successfully in database!";
            } else {
                Integer bookId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookID", "" + bookId);
                updateStatusMessage = "Book data for new book : "
                        + persistentState.getProperty("bookID")
                        + "installed successfully in database!";
            }

        } catch(SQLException ex) {
            updateStatusMessage = "Error in installing book data in database!";
        }
    }

    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("bookID");
        String bNum = (String)b.getState("bookID");

        return aNum.compareTo(bNum);
    }

    public String toString() {
        return "ID = " + persistentState.getProperty("bookID") + "; Title = " + persistentState.getProperty("title") +
                "; Author = " + persistentState.getProperty("author") + "; Publication Year = " + persistentState.getProperty("pubYear") +
                "; Status = " + persistentState.getProperty("status");
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public Vector<String> getEntryListView(){
        Vector<String> entryList = new Vector();

        entryList.add(persistentState.getProperty("author"));
        entryList.add(persistentState.getProperty("bookID"));
        entryList.add(persistentState.getProperty("pubYear"));
        entryList.add(persistentState.getProperty("status"));
        entryList.add(persistentState.getProperty("title"));

        return entryList;
    }

    public void createAndShowBookView(){
        Scene currentScene = myViews.get("BookView");

        if(currentScene == null){
            View view = ViewFactory.createView("BookView", this);
           // if (view == null) System.out.println("Null book view");
            currentScene = new Scene(view);
            myViews.put("BookView", currentScene);
        }

        System.out.println("About to swap to book view");

        //if (currentScene == null) System.out.println("No Book view scene exists");

        //swapToView(currentScene);


        myStage.setScene(currentScene);
        myStage.sizeToScene();

        //Place in center
        WindowPosition.placeCenter(myStage);

        System.out.println("Finished swapping to book view");
    }

    private void processBook(Object props){
        this.persistentState = (Properties)props;
        System.out.println(this);
        updateStateInDatabase();
    }
}