package com.weather.test.app.data.parser;

import com.weather.test.app.dm.dto.WeatherReadingDto;
import org.jvnet.hk2.annotations.Contract;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Contract
public interface WeatherDataParser {
    List<WeatherReadingDto> parseData(InputStream inputStream) throws IOException, ParserConfigurationException, SAXException;
}
