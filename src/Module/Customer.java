package Module;

public class Customer {
    private String CUID;
    private String Name;
    private String NIC;
    private String Email;
    private String Phone;
    private String USID;

    public Customer() {
    }

    public Customer(String CUID, String name, String NIC, String email, String phone, String USID) {
        this.CUID = CUID;
        Name = name;
        this.NIC = NIC;
        Email = email;
        Phone = phone;
        this.USID = USID;
    }

    public String getCUID() {
        return CUID;
    }

    public void setCUID(String CUID) {
        this.CUID = CUID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUSID() {
        return USID;
    }

    public void setUSID(String USID) {
        this.USID = USID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CUID='" + CUID + '\'' +
                ", Name='" + Name + '\'' +
                ", NIC='" + NIC + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", USID='" + USID + '\'' +
                '}';
    }
}
