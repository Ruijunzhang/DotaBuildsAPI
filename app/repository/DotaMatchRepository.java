package repository;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;

import io.ebean.text.json.EJson;
import models.builders.ExpiredMatchEntityBuilder;
import models.builders.MatchEntityBuilder;
import models.dtos.AccessibleReplayInfo;
import models.dtos.ReplayBuildsInfo;
import models.entities.ExpiredMatchEntity;
import models.entities.MatchEntity;
import replay.analyzer.Analyzer;
import utilities.DotaRemoteRepoManager;
import play.libs.Json;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


public class DotaMatchRepository {

    private final DotaRemoteRepoManager dotaRemoteRepoManager;
    private final DotaRemoteRepository dotaRemoteRepository;
    private final DotaLocalRepository dotaLocalRepository;

    @Inject
    public DotaMatchRepository(DotaRemoteRepoManager dotaRemoteRepoManager,DotaLocalRepository dotaLocalRepository,
                               DotaRemoteRepository dotaRemoteRepository){
        this.dotaRemoteRepository = dotaRemoteRepository;
        this.dotaRemoteRepoManager = dotaRemoteRepoManager;
        this.dotaLocalRepository = dotaLocalRepository;
    }

    public CompletionStage<JsonNode> getMatchDetails(String matchId){
        return dotaRemoteRepository.getMatchDetails(dotaRemoteRepoManager.getMatchApiRemoteUrl(matchId));
    }

    public CompletionStage<JsonNode> getRecentMatches(String userId){
        return dotaRemoteRepository.getRecentMatches(dotaRemoteRepoManager.getUserRecentMatchsById(userId));
    }

    public CompletionStage<JsonNode> getDotaBuildsInfo(String matchId) throws IOException{

        if (dotaLocalRepository.containsReplay(Long.parseLong(matchId))) { ;

            return dotaLocalRepository.getMatchEntityByMatchId(Long.parseLong(matchId)).thenApply(match ->
                  Json.toJson(match.buildsInfo)
            );

        }else if(dotaLocalRepository.containsExpiredReplay(Long.parseLong(matchId))){

            return CompletableFuture.supplyAsync(() -> Json.toJson(new ReplayBuildsInfo()));

        } else {

            List<File> replays = getMatchesReplay(matchId);

            if(replays.get(0).length()/(1024*1024) > 10){

                JsonNode buildsInfoJson = Json.toJson(new Analyzer().getReplayBuildsInfoList(dotaLocalRepository.getReplay(matchId)));

                MatchEntity matchEntity = new MatchEntityBuilder().matchId(Long.parseLong(matchId))
                        .replayFilePath(dotaLocalRepository.getReplay(matchId))
                        .buildsInfo(EJson.parseObject(buildsInfoJson.toString())).buildMatchEntity();

                dotaLocalRepository.saveMatchEntity(matchEntity);

                return CompletableFuture.completedFuture(buildsInfoJson);
            }else {

                ExpiredMatchEntity expiredMatchEntity = new ExpiredMatchEntityBuilder().matchId(Long.parseLong(matchId)).buildExpiredMatchEntity();

                dotaLocalRepository.saveExpiredMatchEntity(expiredMatchEntity);

                return CompletableFuture.supplyAsync(() -> Json.toJson(new ReplayBuildsInfo()));
            }
        }
    }

    public List<File> getMatchesReplay(String matchId) throws IOException{

        List<? super AccessibleReplayInfo> downloadUrls = dotaRemoteRepository.getMatchesReplayDownloadInfo(
                dotaRemoteRepoManager.getMatchReplayUrl(matchId)
        ).toCompletableFuture().join();

        return dotaRemoteRepository.getUncompressedMatchReplay(downloadUrls);
    }
}
