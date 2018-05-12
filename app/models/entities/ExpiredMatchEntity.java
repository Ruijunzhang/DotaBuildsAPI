package models.entities;

import javax.persistence.Entity;

@Entity
public class ExpiredMatchEntity extends BaseModel {

    public ExpiredMatchEntity(Long matchId){
        super(matchId);
    }
}
