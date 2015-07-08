package com.weather.test.app.data.parser;

import com.weather.test.app.dm.WeatherReadingDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by matic on 06/07/15.
 */

@RunWith(JUnit4.class)
public class WeatherDataParserIntegrationTest {

    private WeatherDataParser weatherDataParser = new WeatherDataParserImpl();

    @Test
    public void parseFile() throws ParserConfigurationException, SAXException, IOException, ParseException {
        InputStream testXmlFileInputStream = this.getClass().getClassLoader().getResourceAsStream("meteo.si.xml");

        List<WeatherReadingDto> weatherReadingDtos = weatherDataParser.parseData(testXmlFileInputStream);
        Assert.assertNotNull(weatherReadingDtos);
        Assert.assertEquals(16, weatherReadingDtos.size());

        WeatherReadingDto weatherReadingDto = weatherReadingDtos.get(2);
        Assert.assertEquals(weatherReadingDto.getMeteoId(), "CRNOMELJ_");
        Assert.assertEquals(weatherReadingDto.getDomainTitle(), "CRNOMELJ");
        Assert.assertEquals(weatherReadingDto.getUpdated(), new SimpleDateFormat("dd.MM.yyyy hh:mm").parse("08.07.2015 17:32"));
        Assert.assertEquals(weatherReadingDto.getTemp(), 32, 0);
        Assert.assertEquals(weatherReadingDto.getPressure(), 1006.0, 0);
        Assert.assertEquals(weatherReadingDto.getHumidity(), 41.0, 0);
    }

    @Test
    public void parseOnlineData() throws IOException, ParserConfigurationException, SAXException {
        InputStream onlineInputStream = new URL(WeatherDataParserImpl.WEATHER_DATA_XML_URL).openStream();

        List<WeatherReadingDto> weatherReadingDtos = weatherDataParser.parseData(onlineInputStream);
        Assert.assertNotNull(weatherReadingDtos);

        // there should be 16 weather readings online all the time
        Assert.assertEquals(16, weatherReadingDtos.size());
    }
}
