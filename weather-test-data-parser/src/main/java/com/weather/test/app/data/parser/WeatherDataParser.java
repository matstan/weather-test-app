package com.weather.test.app.data.parser;

import com.weather.test.app.dm.WeatherReadingDto;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by matic on 08/07/15.
 */
public interface WeatherDataParser {

    List<WeatherReadingDto> parseData(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException;
}
