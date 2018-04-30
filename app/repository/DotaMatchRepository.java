package repository;

import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;

import models.AccessibleReplayInfo;
import utilities.DotaRemoteRepoManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;


public class DotaMatchRepository {

    private final DotaRemoteRepoManager dotaRemoteRepoManager;
    private final DotaRemoteRepository dotaRemoteRepository;

    @Inject
    public DotaMatchRepository(DotaRemoteRepoManager dotaRemoteRepoManager,
                               DotaRemoteRepository dotaRemoteRepository){
        this.dotaRemoteRepository = dotaRemoteRepository;
        this.dotaRemoteRepoManager = dotaRemoteRepoManager;
    }

    public CompletionStage<JsonNode> getMatchDetails(String matchId){
        return dotaRemoteRepository.getMatchDetails(dotaRemoteRepoManager.getMatchApiRemoteUrl(matchId));
    }

    public CompletionStage<JsonNode> getRecentMatches(String userId){
        return dotaRemoteRepository.getRecentMatches(dotaRemoteRepoManager.getUserRecentMatchsById(userId));
    }

    public CompletionStage<File> getMatchesReplay(String matchId) throws IOException{

        List<? super AccessibleReplayInfo> downloadUrls = dotaRemoteRepository.getMatchesReplayDownloadInfo(dotaRemoteRepoManager.getMatchReplayUrl(matchId)).toCompletableFuture().join();

        return dotaRemoteRepository.getMatchReplay(downloadUrls).get(0);
    }
}
