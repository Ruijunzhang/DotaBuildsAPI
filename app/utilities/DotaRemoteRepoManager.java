package utilities;

import models.dtos.AccessibleReplayInfo;

public class DotaRemoteRepoManager {

    public String getMatchApiRemoteUrl(String matchId) {

        return "https://api.opendota.com/api/matches/" + matchId;
    }

    public String getHeroesApiRemoteUrl() {

        return "https://api.opendota.com/api/heroes";
    }

    public String getUserRecentMatchsById(String userId) {

        return "https://api.opendota.com/api/players/" + userId + "/recentMatches";
    }

    public String getMatchReplayUrl(String matchId){
        return "https://api.opendota.com/api/replays?match_id=" + matchId;
    }

    private String getReplayDownloadUrl(AccessibleReplayInfo accessibleReplayInfo){

        return " http://replay" + accessibleReplayInfo.getCluster() + ".valve.net/570/"
                + accessibleReplayInfo.getMatchId() + "_" + accessibleReplayInfo.getReplaySalt()+ ".dem.bz2";
    }
}
