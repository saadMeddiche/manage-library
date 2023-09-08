package models;

public class Status {
    public Integer id;
    public String name;

    public Status(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Status() {
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

    public Status id(Integer id) {
        setId(id);
        return this;
    }

    public Status name(String name) {
        setName(name);
        return this;
    }

}
