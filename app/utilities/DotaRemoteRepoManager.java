package utilities;

public class DotaRemoteRepoManager {

    public String GetMatchApiRemoteUrl(String matchId)
    {
        return "https://api.opendota.com/api/matches/" + matchId;
    }

    public String GetHerosApiRemoteUrl()
    {
        return "https://api.opendota.com/api/heroes";
    }

    public String GetUserRecentMatchsById(String userId)
    {
        return "https://api.opendota.com/api/players/" + userId + "/recentMatches";
    }
}
