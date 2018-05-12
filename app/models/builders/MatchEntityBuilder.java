package models.builders;

import models.entities.MatchEntity;

import java.util.Map;

public class MatchEntityBuilder {

    private Long _matchId;

    private String _replayFilePath;

    private Map<String, Object> _buildsInfo;

    public MatchEntityBuilder() { }

    public MatchEntityBuilder matchId(Long _matchId){
        this._matchId = _matchId;
        return this;
    }

    public MatchEntityBuilder replayFilePath (String _replayFilePath){
        this._replayFilePath = _replayFilePath;
        return this;
    }

    public MatchEntityBuilder buildsInfo(Map<String, Object> _buildsInfo){
        this._buildsInfo = _buildsInfo;
        return this;
    }

    public MatchEntity buildMatchEntity(){
        return new MatchEntity(_matchId, _replayFilePath,_buildsInfo);
    }

}
