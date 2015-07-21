package com.weather.test.app.gae.rest;

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
    @Path("/parse/{mode}")
    @Produces(MediaType.TEXT_HTML)
    public String parseNewWeatherDataStatic(@PathParam("mode") String mode) throws IOException, ParserConfigurationException, SAXException {
        InputStream weatherDataInputStream = null;
        if (mode == null) {
            return "Please provide correct input parameters.";
        }
        else if (mode.equals("online")) {
            weatherDataInputStream = new URL(WeatherDataParserImpl.WEATHER_DATA_XML_URL).openStream();
        } else if (mode.equals("static")) {
            // parse data from source
            weatherDataInputStream = this.getClass().getClassLoader().getResourceAsStream("meteo.si.xml");
        }

        List<WeatherReadingDto> weatherReadingDtoList = weatherDataParser.parseData(weatherDataInputStream);

        return convertWeatherReadingDtoList2String(weatherReadingDtoList);
    }

    @GET
    @Path("/create/{mode}")
    @Produces(MediaType.TEXT_HTML)
    public String parseNewWeatherDataAndSaveOfy(@PathParam("mode") String mode) throws IOException, ParserConfigurationException, SAXException {
        InputStream weatherDataInputStream = null;
        if (mode == null) {
            return "Please provide correct input parameters.";
        }
        else if (mode.equals("online")) {
            weatherDataInputStream = new URL(WeatherDataParserImpl.WEATHER_DATA_XML_URL).openStream();
        } else if (mode.equals("static")) {
            // parse data from source
            weatherDataInputStream = this.getClass().getClassLoader().getResourceAsStream("meteo.si.xml");
        }

        List<WeatherReadingDto> weatherReadingDtoList = weatherDataParser.parseData(weatherDataInputStream);
        // save data via Ofy to GAE Datastore
        weatherReadingDao.saveWeatherReadingList(weatherReadingDtoList);
        return convertWeatherReadingDtoList2String(weatherReadingDtoList);
    }

    @GET
    @Path("/delete")
    @Produces(MediaType.TEXT_HTML)
    public String deleteWeatherData() throws IOException, ParserConfigurationException, SAXException {
        weatherReadingDao.deleteAllWeatherReading();

        return "Successfully deleted all weather readings.";
    }

    public static String convertWeatherReadingDtoList2String(List<WeatherReadingDto> weatherReadingDtoList) {
        StringBuilder result = new StringBuilder();
        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            result.append(weatherReadingDto.toString() + "<br>");
        }
        return result.toString();
    }
}
