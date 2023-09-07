package models;

public class Client {

    public Client(Integer id, String name, String cin, String phone) {
        this.id = id;
        this.name = name;
        this.cin = cin;
        this.phone = phone;
    }

    public Client() {

    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCin() {
        return this.cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private Integer id;
    private String name;
    private String cin;
    private String phone;

}
