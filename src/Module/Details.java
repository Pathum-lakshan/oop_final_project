package Module;

public class Details {
    private String SupID;
    private String IteID;

    public Details() {
    }

    public Details(String supID, String iteID) {
        SupID = supID;
        IteID = iteID;
    }

    public String getSupID() {
        return SupID;
    }

    public void setSupID(String supID) {
        SupID = supID;
    }

    public String getIteID() {
        return IteID;
    }

    public void setIteID(String iteID) {
        IteID = iteID;
    }

    @Override
    public String toString() {
        return "Details{" +
                "SupID='" + SupID + '\'' +
                ", IteID='" + IteID + '\'' +
                '}';
    }
}
