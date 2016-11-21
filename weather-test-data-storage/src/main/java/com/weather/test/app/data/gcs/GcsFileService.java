package com.weather.test.app.data.gcs;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.StreamingOutput;

import org.jvnet.hk2.annotations.Contract;

/**
 * Created by matic on 20.11.2016.
 */
@Contract
public interface GcsFileService {

    StreamingOutput readFileFromGCS(String fileName, String bucketName) throws IOException;

    void writeFileToGCS(InputStream fileInputStream, String fileName, String bucketName) throws IOException;
}
