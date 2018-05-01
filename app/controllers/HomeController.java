package controllers;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repository.DotaMatchRepository;
import utilities.DataProcessor;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletionStage;


public class HomeController extends Controller {

    private final HttpExecutionContext httpExecutionContext;
    private final DotaMatchRepository dotaMatchRepository;
    private final DataProcessor dataProcessor;

    @Inject
    public HomeController(DotaMatchRepository dotaMatchRepository,
                          HttpExecutionContext httpExecutionContext, DataProcessor dataProcessor) {
        this.dotaMatchRepository = dotaMatchRepository;
        this.httpExecutionContext = httpExecutionContext;
        this.dataProcessor = dataProcessor;
    }


    public CompletionStage<Result> getMatchDetails(String matchId) {

        return dotaMatchRepository.getMatchDetails(matchId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

    public CompletionStage<Result> getRecentMatches(String userId) {

        return dotaMatchRepository.getRecentMatches(userId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

    public CompletionStage<Result> getMatchReplay(String matchId) throws IOException{

        List<File> replay = dotaMatchRepository.getMatchesReplay(matchId);
        return dotaMatchRepository.getMatchDetails(matchId).thenApplyAsync(Results::ok, httpExecutionContext.current());
    }
}
            
