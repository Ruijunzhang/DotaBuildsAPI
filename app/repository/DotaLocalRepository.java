package repository;

import com.typesafe.config.Config;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.entities.MatchEntity;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class DotaLocalRepository {

    private final Config config;
    private final EbeanServer ebeanServer;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public DotaLocalRepository(EbeanConfig ebeanConfig, DatabaseExecutionContext databaseExecutionContext, Config config){
        this.config = config;
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = databaseExecutionContext;
    }

    public CompletionStage<Long> saveMatchEntity(MatchEntity match) {
        return supplyAsync(() -> { ebeanServer.insert(match);return match.id; }, executionContext);
    }

    public Boolean containsReplay(long matchId){
        return lookup(matchId).toCompletableFuture().join().isPresent();
    }

    public CompletionStage<MatchEntity> getMatchEntityByMatchId(long matchId){
        return supplyAsync(() -> ebeanServer.find(MatchEntity.class, matchId), executionContext);
    }

    public CompletionStage<Optional<MatchEntity>> lookup(long matchId){
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(MatchEntity.class).setId(matchId).findOne()), executionContext);
    }

    public Boolean containsReplay(String matchId){
        return new File(getDownloadDirectory() + matchId + ".dem").isFile();
    }

    public String getReplay(String matchId){
        return getDownloadDirectory() + matchId + ".dem";
    }

    private String getDownloadDirectory(){
        return config.getString("replay.download.directory");
    }

}
