package repository;

import com.typesafe.config.Config;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.entities.ExpiredMatchEntity;
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

    public CompletionStage<Long> saveMatchEntity(MatchEntity matchEntity) {
        return supplyAsync(() -> { ebeanServer.insert(matchEntity);return matchEntity.id; }, executionContext);
    }

    public CompletionStage<Long> saveExpiredMatchEntity(ExpiredMatchEntity expiredMatchEntity) {
        return supplyAsync(() -> { ebeanServer.insert(expiredMatchEntity);return expiredMatchEntity.id; }, executionContext);
    }

    public Boolean containsReplay(long matchId){
        return lookup(MatchEntity.class, matchId).toCompletableFuture().join().isPresent();
    }

    public Boolean containsExpiredReplay(long matchId){
        return lookup(ExpiredMatchEntity.class, matchId).toCompletableFuture().join().isPresent();
    }

    public CompletionStage<MatchEntity> getMatchEntityByMatchId(long matchId){
        return supplyAsync(() -> ebeanServer.find(MatchEntity.class, matchId), executionContext);
    }

    public <T> CompletionStage<Optional<T>> lookup(Class<T> classType, long matchId){
        return supplyAsync(() -> Optional.ofNullable(ebeanServer.find(classType).setId(matchId).findOne()), executionContext);
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
