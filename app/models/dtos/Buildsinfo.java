package models.dtos;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "localizedName",
        "item"
})
public class Buildsinfo implements Serializable
{

    @JsonProperty("localizedName")
    private String localizedName;
    @JsonProperty("item")
    private List<Item> item = null;

    @JsonProperty("localizedName")
    public String getLocalizedName() {
        return localizedName;
    }

    @JsonProperty("localizedName")
    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    @JsonProperty("item")
    public List<Item> getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(List<Item> item) {
        this.item = item;
    }

}