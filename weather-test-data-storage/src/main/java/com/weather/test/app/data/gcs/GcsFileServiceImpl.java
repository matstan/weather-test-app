package com.weather.test.app.data.gcs;

import com.google.appengine.tools.cloudstorage.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

/**
 * Created by matic on 20.11.2016.
 */
public class GcsFileServiceImpl implements GcsFileService {

    // should be > 1kb and < 10MB
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());


    public void writeFileToGCS(InputStream fileInputStream, String fileName, String bucketName) throws IOException {
        // initialize output stream
        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
        // valid GcsFilename is: /gcs/<bucket>/<object>
        GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
        GcsOutputChannel outputChannel = gcsService.createOrReplace(gcsFileName, instance);
        OutputStream outputStream = Channels.newOutputStream(outputChannel);

        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = fileInputStream.read(buffer);
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead);
                bytesRead = fileInputStream.read(buffer);
            }
        } finally {
            fileInputStream.close();
            outputStream.close();
        }
    }

    public StreamingOutput readFileFromGCS(String fileName, String bucketName) throws IOException {
        GcsFilename gcsFileName = new GcsFilename(bucketName, fileName);
        GcsInputChannel readChannel = gcsService.openPrefetchingReadChannel(gcsFileName, 0, BUFFER_SIZE);
        final InputStream gcsInputStream = Channels.newInputStream(readChannel);

        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                try {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bytesRead = gcsInputStream.read(buffer);
                    while (bytesRead != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        bytesRead = gcsInputStream.read(buffer);
                    }
                } finally {
                    gcsInputStream.close();
                    outputStream.close();
                }
            }
        };
        return streamingOutput;
    }
}
