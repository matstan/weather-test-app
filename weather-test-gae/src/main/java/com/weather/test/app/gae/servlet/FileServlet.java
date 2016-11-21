package com.weather.test.app.gae.servlet;


import static com.weather.test.app.gae.GaeAppSettings.FILE_UPLOAD_BUCKET_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.glassfish.hk2.api.ServiceLocator;

import com.weather.test.app.data.gcs.GcsFileService;

/**
 * Created by matic on 12/07/15.
 */
public class FileServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceLocator serviceLocator = (ServiceLocator) getServletContext().getAttribute(Hk2EnablementContextListener.HK2_SERVICE_LOCATOR_ATTR_NAME);
        GcsFileService gcsFileService = serviceLocator.getService(GcsFileService.class);

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

        PrintWriter writer = resp.getWriter();
        writer.print("success");
    }
}
