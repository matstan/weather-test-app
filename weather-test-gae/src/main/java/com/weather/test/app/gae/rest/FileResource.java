package com.weather.test.app.gae.rest;

import com.weather.test.app.data.gcs.GcsFileService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by matic on 19.11.2016.
 */
@Path("gcsStorage")
public class FileResource {

    private GcsFileService gcsFileService;

    @Inject
    public FileResource(GcsFileService gcsFileService) {
        this.gcsFileService = gcsFileService;
    }

    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFile(@FormDataParam("fileToUpload") InputStream fileInputStream,
                               @FormDataParam("fileToUpload") FormDataContentDisposition fileDetail) throws IOException {

        String fileName = fileDetail.getFileName();
        String bucketName = "testBucket";
        gcsFileService.writeFileToGCS(fileInputStream, fileName, bucketName);

        return Response.ok().build();
    }

    @GET
    @Path("/download")
    //@Produces("text/plain")
    public Response downloadFile(@QueryParam("fileName") String fileName,
                                 @QueryParam("bucketName") String bucketName) throws IOException {

        StreamingOutput streamingOutput = gcsFileService.readFileFromGCS(fileName, bucketName);

        return Response.ok(streamingOutput).header("content-disposition","attachment; filename = " + fileName).build();
    }


}
