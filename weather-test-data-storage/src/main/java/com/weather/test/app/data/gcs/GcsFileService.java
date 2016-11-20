package com.weather.test.app.data.gcs;

import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by matic on 20.11.2016.
 */
public interface GcsFileService {

    StreamingOutput readFileFromGCS(String fileName, String bucketName) throws IOException;

    void writeFileToGCS(InputStream fileInputStream, String fileName, String bucketName) throws IOException;
}
