package utilities;

import akka.Done;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.typesafe.config.Config;
import models.AccessibleReplayInfo;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletionStage;

public class DataProcessor {

    private final WSClient httpClient;
    private final Config config;
    private final Materializer materializer;

    @Inject
    public DataProcessor(WSClient wsClient, Config config, Materializer materializer){
        this.materializer = materializer;
        this.httpClient = wsClient;
        this.config = config;
    }

    public CompletionStage<File> downloadReplay(AccessibleReplayInfo accessibleReplayInfo)throws IOException{

        String downloadUrl = accessibleReplayInfo.toDownloadUrl();

        CompletionStage<WSResponse> futureResponse =
                httpClient.url(downloadUrl).setMethod("GET").stream();

        File replayFile = createFileByMatch(accessibleReplayInfo);

        OutputStream outputStream = java.nio.file.Files.newOutputStream(replayFile.toPath());

        return futureResponse.thenCompose(res -> {

            Source<ByteString, ?> responseBody = res.getBodyAsSource();

            Sink<ByteString, CompletionStage<Done>> outputWriter =
                    Sink.<ByteString>foreach(bytes -> outputStream.write(bytes.toArray()));

            return responseBody.runWith(outputWriter, materializer)
                    .whenComplete((value, error) -> {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                        }
                    }).thenApply(v -> replayFile);
        });
    }

    private File createFileByMatch(AccessibleReplayInfo accessibleReplayInfo) throws IOException {

        File file = new File(getDownloadDirectory() + accessibleReplayInfo.toDownloadFileName());
        file.getParentFile().mkdirs();
        file.createNewFile();
        return file;
    }

    private String getDownloadDirectory(){
        return config.getString("replay.download.directory");
    }

}
