package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repository.DotaMatchRepository;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;


public class HomeController extends Controller {

    private final HttpExecutionContext httpExecutionContext;
    private final DotaMatchRepository dotaMatchRepository;

    @Inject
    public HomeController(DotaMatchRepository dotaMatchRepository,
                          HttpExecutionContext httpExecutionContext) {
        this.dotaMatchRepository = dotaMatchRepository;
        this.httpExecutionContext = httpExecutionContext;
    }


    public CompletionStage<Result> getMatchDetails(String matchId) {

        return dotaMatchRepository.getMatchDetails(matchId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

    public CompletionStage<Result> getRecentMatches(String userId) {

        return dotaMatchRepository.getRecentMatches(userId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

    public CompletionStage<Result> getMatchReplay(String matchId) throws IOException{

        File replay = dotaMatchRepository.getMatcheReplay(matchId).toCompletableFuture().join();

        return dotaMatchRepository.getMatchDetails(matchId).thenApplyAsync(Results::ok, httpExecutionContext.current());
    }
}
            
