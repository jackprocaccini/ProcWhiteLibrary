package model;

import java.util.Properties;
import java.util.Vector;

import impresario.IView;

public class PatronCollection extends EntityBase implements IView {
    private static final String tableName = "Patron";

    private Vector<Patron> patrons;

    public PatronCollection() {
        super(tableName);
        patrons = new Vector<Patron>();
    }

    public void findPatronsOlderThanDate(String year) {
        String query = "SELECT * FROM " + tableName + " WHERE (dateOfBirth < '" + year + "')";
        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
            Patron p = new Patron((Properties)(allDataRetrieved.get(i)));
            addPatron(p);
        }

    }

    public void findPatronsYoungerThanDate(String year) {
        String query = "SELECT * FROM " + tableName + " WHERE (dateOfBirth > '" + year + "')";
        System.out.println(query);
        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
            Patron p = new Patron((Properties)(allDataRetrieved.get(i)));
            addPatron(p);
        }

    }

    public void findPatronsAtZipcode(String zip) {
        String query = "SELECT * FROM " + tableName + " WHERE (zip=" + zip + ")";
//		Vector allDataRetrieved = getSelectQueryResult(query);
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
            Patron p = new Patron(allDataRetrieved.get(i));
            addPatron(p);
        }
    }

    public void findPatronsWithNameLike(String name){
        String query = "SELECT * FROM " + tableName + " WHERE name LIKE '%" + name + "%'";

        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++){
            addPatron(new Patron((Properties)(allDataRetrieved.get(i))));
        }
    }

    private void addPatron(Patron a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        patrons.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Patron a)
    {
        //users.add(u);
        int low=0;
        int high = patrons.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Patron midSession = patrons.elementAt(middle);

            int result = Patron.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }

    @Override
    public Object getState(String key)
    {
        if (key.equals("Patrons"))
            return patrons;
        else
        if (key.equals("PatronList"))
            return this;
        return null;
    }

    @Override
    public void stateChangeRequest(String key, Object value)
    {

        myRegistry.updateSubscribers(key, this);
    }

    @Override
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public String toString() {
        String s = "";

        for(Patron b : patrons) {
            s += b.toString() + "\n";
        }

        return s;
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }


}