package model;

import java.util.Properties;
import java.util.Vector;

import impresario.IView;

public class BookCollection extends EntityBase implements IView {
    private static final String tableName = "Book";

    private Vector<Book> books;

    public BookCollection() {
        super(tableName);
        books = new Vector<Book>();
    }

    public void findBooksOlderThanDate(String year) {
        String query = "SELECT * FROM " + tableName + " WHERE (pubYear < " + year + ")";
        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
            Book b = new Book((Properties)(allDataRetrieved.get(i)));
            addBook(b);
        }

    }

    public void findBooksNewerThanDate(String year) {
        String query = "SELECT * FROM " + tableName + " WHERE (pubYear > " + year + ")";
//		Vector allDataRetrieved = getSelectQueryResult(query);
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++) {
//			Book b = new Book((Properties)(allDataRetrieved.get(i)));
            Book b = new Book(allDataRetrieved.get(i));
            addBook(b);
        }
    }

    public void findBooksWithTitleLike(String title){
        String query = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + title + "%'";

        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++){
            addBook(new Book((Properties)(allDataRetrieved.get(i))));
        }
    }

    public void findBooksWithAuthorLike(String author){
        String query = "SELECT * FROM " + tableName + " WHERE title LIKE '%" + author + "%'";

        Vector allDataRetrieved = getSelectQueryResult(query);

        for(int i = 0; i < allDataRetrieved.size(); i++){
            addBook(new Book((Properties)(allDataRetrieved.get(i))));
        }
    }

    private void addBook(Book a)
    {
        //accounts.add(a);
        int index = findIndexToAdd(a);
        books.insertElementAt(a,index); // To build up a collection sorted on some key
    }

    private int findIndexToAdd(Book a)
    {
        //users.add(u);
        int low=0;
        int high = books.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = books.elementAt(middle);

            int result = Book.compare(a,midSession);

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
        if (key.equals("Books"))
            return books;
        else
        if (key.equals("BookList"))
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

        for(Book b : books) {
            s += b.toString() + "\n";
        }

        return s;
    }


    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }
}