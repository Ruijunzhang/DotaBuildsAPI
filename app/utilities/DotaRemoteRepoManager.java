package utilities;

public class DotaRemoteRepoManager {

    public String getMatchApiRemoteUrl(String matchId) {
        return "https://api.opendota.com/api/matches/" + matchId;
    }

    public String GetHerosApiRemoteUrl() {
        return "https://api.opendota.com/api/heroes";
    }

    public String getUserRecentMatchsById(String userId) {
        return "https://api.opendota.com/api/players/" + userId + "/recentMatches";
    }

    public String getMatchReplayUrl(String matchId){
        return "https://api.opendota.com/api/replays?match_id=" + matchId;
    }
}
