package com.weather.test.app.data.storage;

import com.google.appengine.api.datastore.Entity;
import com.weather.test.app.dm.WeatherReadingDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides methods for dataset creation for testing purposes.
 */
public class WeatherRedingDataSetFactory {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static List<WeatherReadingDto> createWeatherReadingDtoDataset() throws ParseException {
        List<WeatherReadingDto> dataSet = new ArrayList<WeatherReadingDto>(3);
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2000"), "NOVA_GORICA"));
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2001"), "LJUBLJANA"));
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2002"), "KRANJ"));

        return dataSet;
    }

    /**
     * Returns an element from weatherReadingDtoList, which has the domain title the same as domainTitle.
     * @param domainTitle
     * @param weatherReadingDtoList
     * @return
     */
    public static WeatherReadingDto getWeatherReadingByDomainTitle(String domainTitle, List<WeatherReadingDto> weatherReadingDtoList) {
        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            if (weatherReadingDto.getDomainTitle().equalsIgnoreCase(domainTitle)) {
                return weatherReadingDto;
            }
        }
        return null;
    }
    public static WeatherReadingDto createWeatherReadingDto(Date dateUpdated, String domainTitle) {
        WeatherReadingDto weatherReadingDto = new WeatherReadingDto();
        weatherReadingDto.setDomainTitle(domainTitle);
        weatherReadingDto.setUpdated(dateUpdated);

        return weatherReadingDto;
    }

    public static List<Entity> createWeatherReadingEntityDataset(List<WeatherReadingDto> weatherReadingDtoList) {
        List<Entity> entityList = new ArrayList<Entity>();
        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            Entity weatherReadingDtoEntity = new Entity(WeatherReadingDto.class.getSimpleName());
            weatherReadingDtoEntity.setProperty("updated", weatherReadingDto.getUpdated());
            weatherReadingDtoEntity.setProperty("domainTitle", weatherReadingDto.getDomainTitle());
            entityList.add(weatherReadingDtoEntity);
        }
        return entityList;
    }
}
