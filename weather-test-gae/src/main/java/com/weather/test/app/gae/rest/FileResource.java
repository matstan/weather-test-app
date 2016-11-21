package com.weather.test.app.gae.rest;

import static com.weather.test.app.gae.GaeAppSettings.FILE_UPLOAD_BUCKET_NAME;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.weather.test.app.data.gcs.GcsFileService;

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
    public Response uploadFile(@Context HttpServletRequest req) throws IOException {
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload();
        try {
            // Parse the request
            FileItemIterator iter = upload.getItemIterator(req);
            // iterate over all received files
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getName();
                InputStream stream = item.openStream();
                // process only files (non-form fields
                if (!item.isFormField()) {
                    gcsFileService.writeFileToGCS(stream, name, FILE_UPLOAD_BUCKET_NAME);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        return Response.ok().build();
    }

    @GET
    @Path("/download")
    public Response downloadFile(@QueryParam("fileName") String fileName,
                                 @QueryParam("bucketName") String bucketName) throws IOException {

        StreamingOutput streamingOutput = gcsFileService.readFileFromGCS(fileName, bucketName);

        return Response.ok(streamingOutput).header("content-disposition","attachment; filename = " + fileName).build();
    }


}
