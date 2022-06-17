package Module;

public class Reserve {
    private String RCOID;
    private String RCUID;
    private String Date_Time;

    public Reserve() {
    }

    public Reserve(String RCOID, String RCUID, String date_Time) {
        this.RCOID = RCOID;
        this.RCUID = RCUID;
        Date_Time = date_Time;
    }

    public String getRCOID() {
        return RCOID;
    }

    public void setRCOID(String RCOID) {
        this.RCOID = RCOID;
    }

    public String getRCUID() {
        return RCUID;
    }

    public void setRCUID(String RCUID) {
        this.RCUID = RCUID;
    }

    public String getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(String date_Time) {
        Date_Time = date_Time;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "RCOID='" + RCOID + '\'' +
                ", RCUID='" + RCUID + '\'' +
                ", Date_Time='" + Date_Time + '\'' +
                '}';
    }
}
