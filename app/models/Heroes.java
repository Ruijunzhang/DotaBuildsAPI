package models;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "localized_name",
        "primary_attr",
        "attack_type",
        "roles",
        "legs"
})
public class Heroes implements Serializable
{

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("localized_name")
    private String localizedName;
    @JsonProperty("primary_attr")
    private String primaryAttr;
    @JsonProperty("attack_type")
    private String attackType;
    @JsonProperty("roles")
    private List<String> roles = null;
    @JsonProperty("legs")
    private Integer legs;
    private final static long serialVersionUID = 638578601456039808L;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("localized_name")
    public String getLocalizedName() {
        return localizedName;
    }

    @JsonProperty("localized_name")
    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    @JsonProperty("primary_attr")
    public String getPrimaryAttr() {
        return primaryAttr;
    }

    @JsonProperty("primary_attr")
    public void setPrimaryAttr(String primaryAttr) {
        this.primaryAttr = primaryAttr;
    }

    @JsonProperty("attack_type")
    public String getAttackType() {
        return attackType;
    }

    @JsonProperty("attack_type")
    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @JsonProperty("legs")
    public Integer getLegs() {
        return legs;
    }

    @JsonProperty("legs")
    public void setLegs(Integer legs) {
        this.legs = legs;
    }

}
