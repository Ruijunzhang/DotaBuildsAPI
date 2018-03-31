package repository;

import com.fasterxml.jackson.databind.JsonNode;
import models.Hero;
import models.Match;
import models.RecentMatches;
import play.libs.Json;
import play.libs.ws.*;
import utilities.DotaDataFactory;

import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class DotaRemoteRepository {

    private final WSClient httpClient;
    private final DotaDataFactory dotaDataFactory;
    private final int defaultTimeout = 1000;

    @Inject
    public DotaRemoteRepository(WSClient ws, DotaDataFactory dotaDataFactory) {
        this.httpClient = ws;
        this.dotaDataFactory = dotaDataFactory;
    }

    public CompletionStage<JsonNode> getMatchDetails(String repoAddress){

        WSRequest matchDetailsRequest = httpClient.url(repoAddress).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));
        return matchDetailsRequest.get().thenApply(WSResponse::asJson);
    }

    public CompletionStage<JsonNode> getRecentMatches(String userId){
        WSRequest matchDetailsRequest = httpClient.url(userId).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));

        return matchDetailsRequest.get().thenApply(json ->
                Json.toJson(getMatchList(Json.fromJson(json.asJson(), RecentMatches[].class)))
         );
    }

    private List<Match> getMatchList(RecentMatches[] recentMatches){
        List<Match> matchList = new ArrayList<>();

        HashMap<Integer, Hero> heroHashMap = dotaDataFactory.getHeroMap();

        for (RecentMatches recentMatche: recentMatches ){
            Match match = new Match();
            match.setAssist(recentMatche.getAssists());
            match.setDeath(recentMatche.getDeaths());
            match.setGpm(recentMatche.getGoldPerMin());
            match.setKill(recentMatche.getKills());
            match.setMatchId(recentMatche.getMatchId());
            match.setWon(isWon(recentMatche));
            match.setMyHero(heroHashMap.get(recentMatche.getHeroId()));
            matchList.add(match);
        }
        return matchList;
    }

    private Boolean isWon(RecentMatches recentMatch){

        if (recentMatch.getRadiantWin())
        {
            return recentMatch.getPlayerSlot()< dotaDataFactory.getRadiantIndex();
        }
        else
        {
            return recentMatch.getPlayerSlot() > dotaDataFactory.getRadiantIndex();
        }
    }
}
