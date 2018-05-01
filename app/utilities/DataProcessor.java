package utilities;

import akka.Done;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.typesafe.config.Config;
import models.AccessibleReplayInfo;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import play.libs.F;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class DataProcessor {

    private final WSClient httpClient;
    private final Config config;
    private final Materializer materializer;
    private final int unCompressingBufferSize = 1024;

    @Inject
    public DataProcessor(WSClient wsClient, Config config, Materializer materializer){
        this.materializer = materializer;
        this.httpClient = wsClient;
        this.config = config;
    }

    public List<File> unCompressingReplayAsync(List<CompletionStage<File>> fileCompletionStageList) throws IOException {

        List<File> fileList = new ArrayList<>();

        for(CompletionStage<File> fileCompletionStage : fileCompletionStageList){

            fileCompletionStage.whenComplete((file, error) -> {
                try{
                    fileList.add(unCompressingReplayFile(file));
                }catch (IOException e){
                }
            });
        }
        return fileList;
    }

    public File unCompressingReplayFile(File file) throws IOException {

        InputStream fin = Files.newInputStream(file.toPath());
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get(file.toPath().toString().replace(".bz2", "")));
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
        final byte[] buffer = new byte[unCompressingBufferSize];
        int n = 0;
        while (-1 != (n = bzIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        bzIn.close();
        return Paths.get(file.toPath().toString().replace(".bz2", "")).toFile();
    }

    public void unCompressingReplay() throws IOException {

        InputStream fin = Files.newInputStream(Paths.get("C:\\Users\\ruijun\\Desktop\\replayDownload\\3853797312.dem.bz2"));
        BufferedInputStream in = new BufferedInputStream(fin);
        OutputStream out = Files.newOutputStream(Paths.get("C:\\Users\\ruijun\\Desktop\\replayDownload\\3853797312.dem"));
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
        final byte[] buffer = new byte[unCompressingBufferSize];
        int n = 0;
        while (-1 != (n = bzIn.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.close();
        bzIn.close();
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
