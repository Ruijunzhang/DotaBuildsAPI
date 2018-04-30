package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "match_id",
        "cluster",
        "replay_salt"
})
public class AccessibleReplayInfo implements Serializable
{

    @JsonProperty("match_id")
    private Long matchId;
    @JsonProperty("cluster")
    private Integer cluster;
    @JsonProperty("replay_salt")
    private Integer replaySalt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 9087321364405386802L;

    @JsonProperty("match_id")
    public Long getMatchId() {
        return matchId;
    }

    @JsonProperty("match_id")
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @JsonProperty("cluster")
    public Integer getCluster() {
        return cluster;
    }

    @JsonProperty("cluster")
    public void setCluster(Integer cluster) {
        this.cluster = cluster;
    }

    @JsonProperty("replay_salt")
    public Integer getReplaySalt() {
        return replaySalt;
    }

    @JsonProperty("replay_salt")
    public void setReplaySalt(Integer replaySalt) {
        this.replaySalt = replaySalt;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toDownloadUrl(){
       return  " http://replay" + cluster.toString() + ".valve.net/570/" + matchId.toString() + "_" + replaySalt.toString() + ".dem.bz2";
    }

    public String toDownloadFileName(){
        return matchId.toString() + ".dem.bz2";
    }

}
