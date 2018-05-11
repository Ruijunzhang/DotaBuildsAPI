package utilities;

import models.dtos.Hero;
import models.dtos.Heroes;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;

public class DotaDataFactory {

    private HashMap<Integer, Hero> heroMap;
    private final WSClient httpClient;
    private final DotaRemoteRepoManager dotaRemoteRepoManager;
    private final int defaultTimeout = 1000;
    private final int radiantIndex = 5;
    private final int downloadApiResponseLength = 5;
    private final int downloadableApiResponseLength = 3;


    @Inject
    public DotaDataFactory(WSClient client, DotaRemoteRepoManager dotaRemoteRepoManager){
        this.httpClient = client;
        this.dotaRemoteRepoManager =dotaRemoteRepoManager;
    }


    public HashMap<Integer, Hero> getHeroMap(){

        return  heroMap == null ? buildHeroMap() : heroMap;
    }

    private HashMap<Integer, Hero> buildHeroMap() {

        heroMap = new HashMap<>();

        WSRequest matchDetailsRequest = httpClient.url(dotaRemoteRepoManager.getHeroesApiRemoteUrl()).setRequestTimeout(Duration.of(defaultTimeout, ChronoUnit.MILLIS));

        CompletionStage<Heroes[]> heros= matchDetailsRequest.get().thenApply(json ->
                Json.fromJson(json.asJson(), Heroes[].class)
        );

        Heroes[] heroesArray = heros.toCompletableFuture().join();

        for(Heroes hero : heroesArray){
            Hero dotaBuildHero = new Hero(hero.getName(), hero.getId(), hero.getLocalizedName());
            heroMap.put(hero.getId(), dotaBuildHero);
        }
        return heroMap;
    }

    public int getRadiantIndex(){
        return radiantIndex;
    }

    public int getDownloadApiResponseLength() {
        return downloadApiResponseLength;
    }

    public int getDownloadableApiResponseLength() {
        return downloadableApiResponseLength;
    }
}
