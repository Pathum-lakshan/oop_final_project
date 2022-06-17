package Module;

public class Computer {
    private String COID;
    private String Status;

    public Computer() {
    }

    public Computer(String COID, String status) {
        this.COID = COID;
        Status = status;
    }

    public String getCOID() {
        return COID;
    }

    public void setCOID(String COID) {
        this.COID = COID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "COID='" + COID + '\'' +
                ", Status='" + Status + '\'' +
                '}';
    }
}
