package models;

public class Hero {

    public String name;
    public Integer id;
    public String localizedName;

    public Hero(String name, Integer id, String localizedName) {
        this.name = name;
        this.id = id;
        this.localizedName = localizedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }
}
