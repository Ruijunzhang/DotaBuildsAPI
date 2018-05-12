package models.builders;

import models.entities.ExpiredMatchEntity;

public class ExpiredMatchEntityBuilder {

    private Long _matchId;

    public ExpiredMatchEntityBuilder(){ }

    public ExpiredMatchEntityBuilder matchId(Long _matchId){
        this._matchId = _matchId;
        return this;
    }

    public ExpiredMatchEntity buildExpiredMatchEntity(){
        return new ExpiredMatchEntity(_matchId);
    }

}
