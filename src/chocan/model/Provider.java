package chocan.model;

/**
 * A ChocAn provider (dietitian, internist, exercise expert, ...).
 * Author: Long Huynh
 *
 * Same constructor and getters as the minimal model used by the accounting
 * module, plus setters so the Operator can update a record. The provider
 * number never changes once it is assigned.
 */
public class Provider {
    private final String number;   // 9 digits
    private String name;           // max 25 chars
    private String address;        // max 25 chars
    private String city;           // max 14 chars
    private String state;          // 2 letters
    private String zip;            // 5 digits

    public Provider(String number, String name, String address, String city, String state, String zip) {
        this.number = number;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getNumber()  { return number; }
    public String getName()    { return name; }
    public String getAddress() { return address; }
    public String getCity()    { return city; }
    public String getState()   { return state; }
    public String getZip()     { return zip; }

    public void setName(String name)       { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city)       { this.city = city; }
    public void setState(String state)     { this.state = state; }
    public void setZip(String zip)         { this.zip = zip; }

    @Override
    public String toString() {
        return number + " | " + name + " | " + address + ", " + city + ", " + state + " " + zip;
    }
}
