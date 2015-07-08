package com.weather.test.app.data.parser;

import com.weather.test.app.dm.WeatherReadingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherDataParserImpl implements WeatherDataParser {

    private static Logger log = LoggerFactory.getLogger(WeatherDataParserImpl.class);

    public static final String WEATHER_DATA_XML_URL = "http://meteo.arso.gov.si/uploads/probase/www/observ/surface/text/sl/observation_si_latest.xml";

    public List<WeatherReadingDto> parseData(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        List<WeatherReadingDto> resultList = new ArrayList<WeatherReadingDto>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        // iterate over "metData" nodes and read all their child nodes
        Element documentElement = document.getDocumentElement();
        NodeList metDataNodes = documentElement.getElementsByTagName("metData");
        if (metDataNodes != null && metDataNodes.getLength() > 0) {
            for (int i = 0; i < metDataNodes.getLength(); i++) {
                Node node = metDataNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equalsIgnoreCase("metData")) {
                    Map<String, String> parsedNode = new HashMap<String, String>();
                    Element metData = (Element) node;
                    NodeList childNodes = metData.getChildNodes();
                    for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
                        Node childNode = childNodes.item(childIndex);

                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element child = (Element) childNodes.item(childIndex);
                            String childNodeName = child.getNodeName();
                            String nodeValue = child.getChildNodes().item(0) != null ? child.getChildNodes().item(0).getNodeValue() : null;
                            parsedNode.put(childNodeName, nodeValue);
                        }
                    }
                    WeatherReadingDto dto = createWeatherReading(parsedNode);
                    if (dto != null) {
                        log.debug("Parsed dto: {}", dto.toString());
                        resultList.add(dto);
                    }
                }
            }
        }
        return resultList;
    }

    private static WeatherReadingDto createWeatherReading(Map<String, String> parsedNode) {
        WeatherReadingDto dto = new WeatherReadingDto();

        String meteoId = parsedNode.get("domain_meteosiId");

        // skip this reading if it has no id
        if (meteoId == null) {
            return null;
        }
        dto.setMeteoId(meteoId);

        String domainTitle = parsedNode.get("domain_title");
        dto.setDomainTitle(domainTitle);

        String dateUpdatedString = parsedNode.get("tsUpdated_UTC");
        if (dateUpdatedString != null) {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            Date updatedDate = null;
            try {
                updatedDate = df.parse(dateUpdatedString);
            } catch (ParseException e) {
                log.error("Failed to parse the date");
            }
            dto.setUpdated(updatedDate);

        }

        String temp = parsedNode.get("t");
        if (temp != null) {
            dto.setTemp(Double.parseDouble(temp));
        }

        String pressure = parsedNode.get("msl");
        if (pressure != null) {
            dto.setPressure(Double.parseDouble(pressure));
        }

        String humidity = parsedNode.get("rh");
        if (humidity != null) {
            dto.setHumidity(Double.parseDouble(humidity));
        }

        return dto;
    }
}