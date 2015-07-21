package com.weather.test.app.gae.servlet;

import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import org.glassfish.hk2.api.ServiceLocator;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by matic on 12/07/15.
 */
public class ParsingServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceLocator serviceLocator = (ServiceLocator) getServletContext().getAttribute(Hk2EnablementContextListener.HK2_SERVICE_LOCATOR_ATTR_NAME);

        WeatherDataParser weatherDataParser = serviceLocator.getService(WeatherDataParser.class);

        List<WeatherReadingDto> weatherReadingDtoList = null;
        resp.setContentType("text/plain");

        InputStream onlineInputStream = new URL(WeatherDataParserImpl.WEATHER_DATA_XML_URL).openStream();
        try {
            weatherReadingDtoList = weatherDataParser.parseData(onlineInputStream);
        } catch (ParserConfigurationException e) {
            resp.getWriter().println("Error parsing online weather data: " + e);
        } catch (SAXException e) {
            resp.getWriter().println("Error deserializing xml data: " + e);
        }

        if (weatherReadingDtoList != null && weatherReadingDtoList.size() > 0) {
            for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
                resp.getWriter().println(weatherReadingDto.toString());
            }
        }
    }
}
