package Module;

public class Package {
    private String PAID;
    private String PackagePrice;
    private String PackageType;

    public Package() {
    }

    public Package(String PAID, String packagePrice, String packageType) {
        this.PAID = PAID;
        PackagePrice = packagePrice;
        PackageType = packageType;
    }

    public String getPAID() {
        return PAID;
    }

    public void setPAID(String PAID) {
        this.PAID = PAID;
    }

    public String getPackagePrice() {
        return PackagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        PackagePrice = packagePrice;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    @Override
    public String toString() {
        return "Package{" +
                "PAID='" + PAID + '\'' +
                ", PackagePrice='" + PackagePrice + '\'' +
                ", PackageType='" + PackageType + '\'' +
                '}';
    }
}
