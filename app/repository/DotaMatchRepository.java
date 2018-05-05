package repository;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;

import models.AccessibleReplayInfo;
import models.dtos.ReplayBuildsInfo;
import replay.analyzer.Analyzer;
import utilities.DotaRemoteRepoManager;
import play.libs.Json;
import java.io.File;
import java.io.IOException;
import java.util.List;
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

        if (dotaLocalRepository.containsReplay(matchId)) {
            return CompletableFuture.completedFuture(Json.toJson(new Analyzer().getReplayBuildsInfoList(dotaLocalRepository.getReplay(matchId))));
        }else{
            return CompletableFuture.supplyAsync(() -> Json.toJson(new ReplayBuildsInfo()));
        }
    }

    public List<File> getMatchesReplay(String matchId) throws IOException{

        List<? super AccessibleReplayInfo> downloadUrls = dotaRemoteRepository.getMatchesReplayDownloadInfo(
                dotaRemoteRepoManager.getMatchReplayUrl(matchId)
        ).toCompletableFuture().join();

        return dotaRemoteRepository.getUncompressedMatchReplay(downloadUrls);
    }
}
