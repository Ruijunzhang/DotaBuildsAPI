package models.entities;

import io.ebean.annotation.DbJson;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import java.util.Map;

@Entity
public class MatchEntity extends BaseModel{

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    public String replayFilePath;

    @DbJson
    public Map<String,Object> buildsInfo;
}
