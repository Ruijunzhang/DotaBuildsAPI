package repository;

import com.typesafe.config.Config;

import javax.inject.Inject;
import java.io.File;

public class DotaLocalRepository {

    private final Config config;

    @Inject
    public DotaLocalRepository(Config config){
        this.config = config;
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
