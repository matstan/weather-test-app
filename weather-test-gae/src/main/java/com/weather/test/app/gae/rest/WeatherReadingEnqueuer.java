package com.weather.test.app.gae.rest;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import com.weather.test.app.data.storage.dao.WeatherReadingDao;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Path("weatherReadingEnqueuer")
public class WeatherReadingEnqueuer {

    private static final String WEATHER_READING_QUEUE = "weather-reading-queue";

    @GET
    @Path("/create/{mode}")
    @Produces(MediaType.TEXT_HTML)
    public String createWeatherReadingsViaQueue(@PathParam("mode") String mode) throws IOException, ParserConfigurationException, SAXException {
        if (mode == null) {
            return "Please provide correct input parameters.";
        }

        // Add the task to the queue.
        Queue queue = QueueFactory.getQueue(WEATHER_READING_QUEUE);
        queue.add(TaskOptions.Builder.withUrl("/createWeatherReadingsQueueServlet").param("mode", mode));

        return "Successfully added task to task queue.";
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public String deleteWeatherReadingsViaQueue() throws IOException, ParserConfigurationException, SAXException {
        // Add the task to the queue.
        Queue queue = QueueFactory.getQueue(WEATHER_READING_QUEUE);
        queue.add(TaskOptions.Builder.withUrl("/deleteWeatherReadingsQueueServlet"));

        return "Successfully added task to task queue.";
    }
}
