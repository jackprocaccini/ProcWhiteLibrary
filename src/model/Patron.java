package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import javafx.scene.Scene;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Patron extends EntityBase{

    private static final String myTableName = "Patron";
    private String updateStatusMessage = "";

    private Properties dependencies;

    public Patron(String patronID) throws InvalidPrimaryKeyException{
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (patronID = " + patronID + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        if(allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            if(size != 1) {
                throw new InvalidPrimaryKeyException("Multiple patrons matching id : "
                        + patronID + " found.");
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
                    + patronID + " found.");
        }
    }

    public Patron(Properties props) {
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

    public Patron(){
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
        if(key.equals("ProcessPatron")){
            processPatron(value);
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
            if(persistentState.getProperty("patronID") != null) {
                Properties whereClause = new Properties();
                whereClause.setProperty("patronID", persistentState.getProperty("patronID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Patron data for patron number : " + persistentState.getProperty("patronID") + " updated successfully in database!";
            } else {
                Integer patronID =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronID", "" + patronID);
                updateStatusMessage = "Patron data for new patron : "
                        + persistentState.getProperty("patronID")
                        + "installed successfully in database!";
            }

        } catch(SQLException ex) {
            updateStatusMessage = "Error in installing patron data in database!";
        }
    }

    public static int compare(Patron a, Patron b)
    {
        String aNum = (String)a.getState("patronID");
        String bNum = (String)b.getState("patronID");

        return aNum.compareTo(bNum);
    }

    public String toString() {
        return "ID = " + persistentState.getProperty("patronID") + "; Name = " + persistentState.getProperty("name") +
                "; Address = " + persistentState.getProperty("address") + "; City = " + persistentState.getProperty("city") +
                "; State Code = " + persistentState.getProperty("stateCode") + "; Zip = " + persistentState.getProperty("zip") +
                "; Email = " + persistentState.getProperty("email") + "; D.O.B. = " + persistentState.getProperty("dateOfBirth") +
                "; Status = " + persistentState.getProperty("status");
    }

    public Vector<String> getEntryListView(){
        Vector<String> entryList = new Vector();

        entryList.add(persistentState.getProperty("address"));
        entryList.add(persistentState.getProperty("city"));
        entryList.add(persistentState.getProperty("dateOfBirth"));
        entryList.add(persistentState.getProperty("email"));
        entryList.add(persistentState.getProperty("name"));
        entryList.add(persistentState.getProperty("patronID"));
        entryList.add(persistentState.getProperty("stateCode"));
        entryList.add(persistentState.getProperty("status"));
        entryList.add(persistentState.getProperty("zip"));

        return entryList;
    }

    public void createAndShowPatronView(){
        Scene currentScene = myViews.get("PatronView");

        if(currentScene == null){
            View view = ViewFactory.createView("PatronView", this);
            // if (view == null) System.out.println("Null book view");
            currentScene = new Scene(view);
            myViews.put("PatronView", currentScene);
        }

        System.out.println("About to swap to patron view");

        //if (currentScene == null) System.out.println("No Book view scene exists");

        //swapToView(currentScene);


        myStage.setScene(currentScene);
        myStage.sizeToScene();

        //Place in center
        WindowPosition.placeCenter(myStage);

        System.out.println("Finished swapping to patron view");
    }

    private void processPatron(Object props){
        this.persistentState = (Properties) props;
        updateStateInDatabase();
    }
}