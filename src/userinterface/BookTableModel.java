package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel
{
    private final SimpleStringProperty author;
    private final SimpleStringProperty bookID;
    private final SimpleStringProperty pubYear;
    private final SimpleStringProperty status;
    private final SimpleStringProperty title;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData)
    {
        author =  new SimpleStringProperty(bookData.elementAt(0));
        bookID =  new SimpleStringProperty(bookData.elementAt(1));
        pubYear =  new SimpleStringProperty(bookData.elementAt(2));
        status =  new SimpleStringProperty(bookData.elementAt(3));
        title =  new SimpleStringProperty(bookData.elementAt(4));

    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getBookID() {
        return bookID.get();
    }

    public SimpleStringProperty bookIDProperty() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID.set(bookID);
    }

    public String getPubYear() {
        return pubYear.get();
    }

    public SimpleStringProperty pubYearProperty() {
        return pubYear;
    }

    public void setPubYear(String pubYear) {
        this.pubYear.set(pubYear);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}