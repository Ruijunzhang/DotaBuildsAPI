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
        "series_id",
        "series_type"
})
public class ReplayInfo extends AccessibleReplayInfo implements Serializable
{

    @JsonProperty("series_id")
    private Integer seriesId;
    @JsonProperty("series_type")
    private Integer seriesType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6845180861891008967L;

    @JsonProperty("series_id")
    public Integer getSeriesId() {
        return seriesId;
    }

    @JsonProperty("series_id")
    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    @JsonProperty("series_type")
    public Integer getSeriesType() {
        return seriesType;
    }

    @JsonProperty("series_type")
    public void setSeriesType(Integer seriesType) {
        this.seriesType = seriesType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
