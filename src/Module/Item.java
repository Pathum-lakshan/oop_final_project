package Module;

public class Item {
    private String IID;
    private String IName;
    private String IType;
    private String IQty;

    public Item() {
    }



    public Item(String IID, String IName, String IType, String IQty) {
        this.IID = IID;
        this.IName = IName;
        this.IType = IType;
        this.IQty = IQty;
    }

    public String getIID() {
        return IID;
    }

    public void setIID(String IID) {
        this.IID = IID;
    }

    public String getIName() {
        return IName;
    }

    public void setIName(String IName) {
        this.IName = IName;
    }

    public String getIType() {
        return IType;
    }

    public void setIType(String IType) {
        this.IType = IType;
    }

    public String getIQty() {
        return IQty;
    }

    public void setIQty(String IQty) {
        this.IQty = IQty;
    }

    @Override
    public String toString() {
        return "Item{" +
                "IID='" + IID + '\'' +
                ", IName='" + IName + '\'' +
                ", IType='" + IType + '\'' +
                ", IQty='" + IQty + '\'' +
                '}';
    }
}
