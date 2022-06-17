package Module;

public class PackageDetails {

    private String PDPAID;
    private String PDCUID;
    private String PDIncome;

    public PackageDetails() {
    }

    public PackageDetails(String PDPAID, String PDCUID, String PDIncome) {
        this.PDPAID = PDPAID;
        this.PDCUID = PDCUID;
        this.PDIncome = PDIncome;
    }

    public String getPDPAID() {
        return PDPAID;
    }

    public void setPDPAID(String PDPAID) {
        this.PDPAID = PDPAID;
    }

    public String getPDCUID() {
        return PDCUID;
    }

    public void setPDCUID(String PDCUID) {
        this.PDCUID = PDCUID;
    }

    public String getPDIncome() {
        return PDIncome;
    }

    public void setPDIncome(String PDIncome) {
        this.PDIncome = PDIncome;
    }

    @Override
    public String toString() {
        return "PackageDetails{" +
                "PDPAID='" + PDPAID + '\'' +
                ", PDCUID='" + PDCUID + '\'' +
                ", PDIncome='" + PDIncome + '\'' +
                '}';
    }
}
