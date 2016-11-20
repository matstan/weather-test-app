package com.weather.test.app.gae.rest;

import com.google.appengine.tools.cloudstorage.*;
import org.glassfish.jersey.media.multipart.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.channels.Channels;

/**
 * Created by matic on 19.11.2016.
 */
@Path("uploadFile")
public class FileResource {

    // should be > 1kb and < 10MB
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
            .initialRetryDelayMillis(10)
            .retryMaxAttempts(10)
            .totalRetryPeriodMillis(15000)
            .build());

    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFile(@FormDataParam("fileToUpload") InputStream fileInputStream,
                               @FormDataParam("fileToUpload") FormDataContentDisposition fileDetail) throws IOException {

        String bucketName = "testBucket";
        String fileName = fileDetail.getFileName();
        String writeFileToGCS = writeFileToGCS(fileInputStream, fileName, bucketName);

        return Response.ok(writeFileToGCS).build();
    }

//    private String writeFileToGCS(InputStream fileInputStream, String fileName, String bucketName) {
//        StringBuilder result = new StringBuilder();
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {
//            String currentLine;
//
//            while ((currentLine = br.readLine()) != null) {
//                System.out.println(currentLine);
//                result.append(currentLine);
//            }
//        } catch (IOException e) {
//            result.append("Error reading uploaded file: " + e.getMessage());
//        }
//
//        return result.toString();
//    }

    private String writeFileToGCS(InputStream fileInputStream, String fileName, String bucketName) throws IOException {

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

        return "OK";
    }

//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
//        GcsFilename fileName = getFileName(req);
//        GcsOutputChannel outputChannel;
//        outputChannel = gcsService.createOrReplace(fileName, instance);
//        copy(req.getInputStream(), Channels.newOutputStream(outputChannel));
//    }
//[END doPost]

//    private GcsFilename getFileName(HttpServletRequest req) {
//        String[] splits = req.getRequestURI().split("/", 4);
//        if (!splits[0].equals("") || !splits[1].equals("gcs")) {
//            throw new IllegalArgumentException("The URL is not formed as expected. " +
//                    "Expecting /gcs/<bucket>/<object>");
//        }
//        return new GcsFilename(splits[2], splits[3]);
//    }
//
//    /**
//     * Transfer the data from the inputStream to the outputStream. Then close both streams.
//     */
//    private void copy(InputStream input, OutputStream output) throws IOException {
//        try {
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int bytesRead = input.read(buffer);
//            while (bytesRead != -1) {
//                output.write(buffer, 0, bytesRead);
//                bytesRead = input.read(buffer);
//            }
//        } finally {
//            input.close();
//            output.close();
//        }
//    }
}
