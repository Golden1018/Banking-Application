package Assignment6Model;

/**
 *
 * @author kmehta
 */
public class CustomerAddress {

    int streetnum, zip, id;
    String streetname, city, state;

    public CustomerAddress(int custID, int num, String street, String aCity, String st, int zp) {
        this.id = custID;
        streetnum = num;
        streetname = street;
        city = aCity;
        state = st;
        zip = zp;
    }

    public CustomerAddress() {}

    public int getStreetNum() { return streetnum; }
    public String getStreetName() { return streetname; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public int getZip() { return zip; }
    public int getID() { return id; }

    public void setStreetNum(int num) { this.streetnum = num; }
    public void setStreet(String stName) { this.streetname = stName; }
    public void setCity(String ct) { this.city = ct; }
    public void setState(String st) { this.state = st; }
    public void setZip(int aZip) { this.zip = aZip; }
    public void setID(int custID) { this.id = custID; }

    @Override
    public String toString() {
        return streetnum + " " + streetname + ", " + city + ", " + state + " " + zip;
    }

}
