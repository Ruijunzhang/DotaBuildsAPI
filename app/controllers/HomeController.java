package controllers;

import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repository.DotaMatchRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


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

        return  dotaMatchRepository.getMatchDetails(matchId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

    public CompletionStage<Result> getRecentMatches(String userId) {

        return  dotaMatchRepository.getRecentMatches(userId).thenApplyAsync(Results::ok, httpExecutionContext.current());

    }

}
            
