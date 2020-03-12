package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class PatronTableModel
{
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty email;
    private final SimpleStringProperty name;
    private final SimpleStringProperty patronID;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty status;
    private final SimpleStringProperty zip;

    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> patronData)
    {
        address =  new SimpleStringProperty(patronData.elementAt(0));
        city =  new SimpleStringProperty(patronData.elementAt(1));
        dateOfBirth =  new SimpleStringProperty(patronData.elementAt(2));
        email =  new SimpleStringProperty(patronData.elementAt(3));
        name =  new SimpleStringProperty(patronData.elementAt(4));
        patronID =  new SimpleStringProperty(patronData.elementAt(5));
        stateCode =  new SimpleStringProperty(patronData.elementAt(6));
        status =  new SimpleStringProperty(patronData.elementAt(7));
        zip =  new SimpleStringProperty(patronData.elementAt(8));


    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public SimpleStringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPatronID() {
        return patronID.get();
    }

    public SimpleStringProperty patronIDProperty() {
        return patronID;
    }

    public void setPatronID(String patronID) {
        this.patronID.set(patronID);
    }

    public String getStateCode() {
        return stateCode.get();
    }

    public SimpleStringProperty stateCodeProperty() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode.set(stateCode);
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

    public String getZip() {
        return zip.get();
    }

    public SimpleStringProperty zipProperty() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }
}