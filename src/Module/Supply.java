package Module;

public class Supply {
    private String ID;
    private String Name;
    private String Type;
    private String Qty;
    private String Cost;
    private String Date;

    public Supply() {
    }

    public Supply(String ID, String name, String type, String qty, String cost, String date) {
        this.ID = ID;
        Name = name;
        Type = type;
        Qty = qty;
        Cost = cost;
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Supply{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Type='" + Type + '\'' +
                ", Qty='" + Qty + '\'' +
                ", Cost='" + Cost + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
