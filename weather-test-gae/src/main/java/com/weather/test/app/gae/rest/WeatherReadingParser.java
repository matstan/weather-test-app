package com.weather.test.app.gae.rest;

import com.googlecode.objectify.ObjectifyService;
import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import com.weather.test.app.data.storage.WeatherReadingDao;
import com.weather.test.app.dm.WeatherReadingDto;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Path("weatherReadingParser")
public class WeatherReadingParser {

    @Inject
    WeatherDataParser weatherDataParser;

    @Inject
    WeatherReadingDao weatherReadingDao;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/html" media type.
     *
     * @return String that will be returned as a text/html response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String parseNewWeatherData() throws IOException, ParserConfigurationException, SAXException {
        InputStream weatherDataInputStream = this.getClass().getClassLoader().getResourceAsStream("meteo.si.xml");
        List<WeatherReadingDto> weatherReadingDtoList = weatherDataParser.parseData(weatherDataInputStream);

        return convertWeatherReadingDtoList2String(weatherReadingDtoList);
    }

    @GET
    @Path("/ofy")
    @Produces(MediaType.TEXT_HTML)
    public String parseNewWeatherDataAndSaveOfy() throws IOException, ParserConfigurationException, SAXException {
        // parse data from source
        InputStream weatherDataInputStream = this.getClass().getClassLoader().getResourceAsStream("meteo.si.xml");
        List<WeatherReadingDto> weatherReadingDtoList = weatherDataParser.parseData(weatherDataInputStream);

        // save data via Ofy to GAE Datastore
        weatherReadingDao.saveWeatherReadingList(weatherReadingDtoList);

        return convertWeatherReadingDtoList2String(weatherReadingDtoList);
    }

    public static String convertWeatherReadingDtoList2String(List<WeatherReadingDto> weatherReadingDtoList) {
        StringBuilder result = new StringBuilder();
        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            result.append(weatherReadingDto.toString() + "<br>");
        }
        return result.toString();
    }
}
