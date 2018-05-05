package models.dtos;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "matchid",
        "buildsinfo"
})
public class ReplayBuildsInfo implements Serializable
{

    @JsonProperty("matchid")
    private String matchid;
    @JsonProperty("buildsinfo")
    private List<Buildsinfo> buildsinfo = null;

    @JsonProperty("matchid")
    public String getMatchid() {
        return matchid;
    }

    @JsonProperty("matchid")
    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    @JsonProperty("buildsinfo")
    public List<Buildsinfo> getBuildsinfo() {
        return buildsinfo;
    }

    @JsonProperty("buildsinfo")
    public void setBuildsinfo(List<Buildsinfo> buildsinfo) {
        this.buildsinfo = buildsinfo;
    }

}