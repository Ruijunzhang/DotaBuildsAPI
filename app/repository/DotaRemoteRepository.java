package repository;

import akka.util.ByteString;
import com.fasterxml.jackson.databind.JsonNode;
import models.Hero;
import models.Match;
import models.RecentMatches;
import play.libs.Json;
import play.libs.ws.*;
import scala.compat.java8.FutureConverters;
import utilities.DotaDataFactory;

import javax.inject.Inject;
import java.io.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import akka.stream.Materializer;
import akka.stream.javadsl.*;

public class DotaRemoteRepository {

    private final WSClient httpClient;
    private final WSClient downloadClient;
    private final Materializer materializer;
    private final DotaDataFactory dotaDataFactory;
    private final int defaultTimeout = 1000;

    @Inject
    public DotaRemoteRepository(WSClient ws, WSClient downloadClient, DotaDataFactory dotaDataFactory, Materializer materializer) {
        this.httpClient = ws;
        this.downloadClient = downloadClient;
        this.dotaDataFactory = dotaDataFactory;
        this.materializer = materializer;
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

    public CompletionStage<File> getMatchReplay() throws IOException{

        String url = "http://replay136.valve.net/570/3825092637_1544200095.dem.bz2";

        CompletionStage<WSResponse> futureResponse =
                downloadClient.url(url).setMethod("GET").stream();

        File file = File.createTempFile("stream", ".bz2", new File("C:\\Users\\ruijun\\Desktop\\replayDownload"));
        OutputStream outputStream = java.nio.file.Files.newOutputStream(file.toPath());

        CompletionStage<File> downloadedFile = futureResponse.thenCompose(res -> {

            Source<ByteString, ?> responseBody = res.getBodyAsSource();

            Sink<ByteString, CompletionStage<akka.Done>> outputWriter =
                    Sink.<ByteString>foreach(bytes -> outputStream.write(bytes.toArray()));

            CompletionStage<File> result = responseBody.runWith(outputWriter, materializer)
                    .whenComplete((value, error) -> {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                        }
                    }).thenApply(v -> file);
                return result;
            });
        return downloadedFile;
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
