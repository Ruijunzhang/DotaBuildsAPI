package repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import models.dtos.*;
import play.libs.Json;
import play.libs.ws.*;
import utilities.DataProcessor;
import utilities.DotaDataFactory;

import javax.inject.Inject;
import java.io.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class DotaRemoteRepository {

    private final WSClient httpClient;
    private final DotaDataFactory dotaDataFactory;
    private final DataProcessor dataProcessor;
    private final int defaultTimeout = 2000;

    @Inject
    public DotaRemoteRepository(WSClient ws, DotaDataFactory dotaDataFactory, DataProcessor dataProcessor) {
        this.httpClient = ws;
        this.dotaDataFactory = dotaDataFactory;
        this.dataProcessor = dataProcessor;
    }

    public CompletionStage<JsonNode> getMatchDetails(String remoteApiUrl){

        WSRequest matchDetailsRequest = httpClient.url(remoteApiUrl).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));
        return matchDetailsRequest.get().thenApply(WSResponse::asJson);
    }

    public CompletionStage<JsonNode> getRecentMatches(String remoteApiUrl){
        WSRequest matchDetailsRequest = httpClient.url(remoteApiUrl).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));

        return matchDetailsRequest.get().thenApply(json ->
                Json.toJson(getMatchList(Json.fromJson(json.asJson(), RecentMatches[].class)))
         );
    }

    public CompletionStage<List<? super AccessibleReplayInfo>> getMatchesReplayDownloadInfo(String matchId){

        WSRequest matchReplayDownloadInfoRequest = httpClient.url(matchId).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));

        return matchReplayDownloadInfoRequest.get().thenApply(json ->
                getReplaysDownLoadInfos(json.asJson())
        );
    }

    public List<File> getUncompressedMatchReplay(List<? super AccessibleReplayInfo> replayInfoList)throws IOException{
        return dataProcessor.unCompressingReplayAsync(getMatchReplay(replayInfoList));
    }

    private List<CompletionStage<File>> getMatchReplay(List<? super AccessibleReplayInfo> replayInfoList) throws IOException{

        List<CompletionStage<File>> replayList = new ArrayList<>();

        for(Object object : replayInfoList) {
            AccessibleReplayInfo replayInfo = (AccessibleReplayInfo) object;
            replayList.add(dataProcessor.downloadReplay(replayInfo));
        }
        return replayList;
    }

    private List<? super AccessibleReplayInfo> getReplaysDownLoadInfos(JsonNode jsonNode){

        List<? super AccessibleReplayInfo> replayInfos = new ArrayList<>();

        if(jsonNode.isArray()){
            ArrayNode jsonNodeArray = (ArrayNode)jsonNode;
            for (JsonNode node : jsonNodeArray)
            {
                if(node.size() == dotaDataFactory.getDownloadableApiResponseLength()){
                    AccessibleReplayInfo replayInfo = Json.fromJson(node, AccessibleReplayInfo.class);
                    replayInfos.add(replayInfo);
                }
                if(node.size() == dotaDataFactory.getDownloadApiResponseLength()){
                    ReplayInfo replayInfo = Json.fromJson(node, ReplayInfo.class);
                    replayInfos.add(replayInfo);
                }
            }
        }
        return replayInfos;
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
