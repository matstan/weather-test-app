package com.weather.test.app.gae.rest;

import org.glassfish.jersey.media.multipart.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by matic on 19.11.2016.
 */
@Path("uploadFile")
public class FileResource {

    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadFile(@FormDataParam("fileToUpload") InputStream fileInputStream,
                               @FormDataParam("fileToUpload") FormDataContentDisposition fileDetail) throws Exception {
        StringBuilder result = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
                result.append(currentLine);
            }
        }

        return Response.ok(result.toString()).build();
    }
}
