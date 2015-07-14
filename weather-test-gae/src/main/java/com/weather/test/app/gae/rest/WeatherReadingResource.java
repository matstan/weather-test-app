package com.weather.test.app.gae.rest;

import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
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

@Path("weatherReadings")
public class WeatherReadingResource {

    @Inject
    WeatherDataParser weatherDataParser;

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/html" media type.
     *
     * @return String that will be returned as a text/html response.
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String parseNewWeatherData() throws IOException, ParserConfigurationException, SAXException {
        InputStream onlineInputStream = new URL(WeatherDataParserImpl.WEATHER_DATA_XML_URL).openStream();

        StringBuilder result = new StringBuilder();
        List<WeatherReadingDto> weatherReadingDtoList = weatherDataParser.parseData(onlineInputStream);
        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            result.append(weatherReadingDto.toString() + "<br>");
        }
        return result.toString();
    }
}
