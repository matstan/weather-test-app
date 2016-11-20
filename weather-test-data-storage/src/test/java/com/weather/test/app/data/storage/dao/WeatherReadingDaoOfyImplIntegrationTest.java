package com.weather.test.app.data.storage.dao;

import com.weather.test.app.data.datastore.dao.WeatherReadingDao;
import com.weather.test.app.data.storage.WeatherRedingDataSetFactory;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import com.weather.test.app.testing.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherReadingDaoOfyImplIntegrationTest extends BaseUnitTest {

    @Inject
    private WeatherReadingDao weatherReadingDao;

    @Test
    public void saveWeatherReadingList() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data
        weatherReadingDao.saveWeatherReadingList(weatherReadingDtoList);

        // retrieve data
        List<WeatherReadingDto> savedWeatherReadingDtoList = getAllEntitiesByType(WeatherReadingDto.class);
        checkContentsOfDtoList(weatherReadingDtoList, savedWeatherReadingDtoList);
    }

    @Test
    public void saveWeatherReading() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data (only one element!)
        weatherReadingDao.saveWeatherReading(weatherReadingDtoList.get(0));

        // retrieve data
        List<WeatherReadingDto> savedWeatherReadingDtoList = getAllEntitiesByType(WeatherReadingDto.class);

        checkContentsOfDtoList(weatherReadingDtoList.subList(0, 1), savedWeatherReadingDtoList);
    }

    @Test
    public void getWeatherReadingList_getAllElements() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data (only one element!)
        saveAllEntities(weatherReadingDtoList);

        // retrieve data
        List<WeatherReadingDto> savedWeatherReadingDtoList = weatherReadingDao.getWeatherReadingList();

        checkContentsOfDtoList(weatherReadingDtoList, savedWeatherReadingDtoList);
    }

    @Test
    public void getWeatherReadingList_filterDomainTitle() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();
        String domainTitle = "NOVA_GORICA";

        // save data (only one element!)
        saveAllEntities(weatherReadingDtoList);

        // retrieve data with filter
        List<WeatherReadingDto> savedWeatherReadingDtoList = weatherReadingDao.getWeatherReadingList(domainTitle);

        checkContentsOfDtoList(weatherReadingDtoList.subList(0, 1), savedWeatherReadingDtoList);
    }

    @Test
    public void getWeatherReadingList_filterUpdated() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();
        Date updatedFrom = dateFormat.parse("1.1.1999");
        Date updatedTo = dateFormat.parse("1.2.2000");

        // save data (only one element!)
        saveAllEntities(weatherReadingDtoList);

        // retrieve data with filter
        List<WeatherReadingDto> savedWeatherReadingDtoList = weatherReadingDao.getWeatherReadingList(updatedFrom, updatedTo);

        checkContentsOfDtoList(weatherReadingDtoList.subList(0, 1), savedWeatherReadingDtoList);
    }

    @Test
    public void getWeatherReadingList_filterDomainTitleAndDate() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();
        String domainTitle = "NOVA_GORICA";
        Date updatedFrom = dateFormat.parse("1.1.1999");
        Date updatedTo = dateFormat.parse("1.2.2000");

        // save data
        saveAllEntities(weatherReadingDtoList);

        // retrieve data with filter
        List<WeatherReadingDto> savedWeatherReadingDtoList = weatherReadingDao.getWeatherReadingList(domainTitle, updatedFrom, updatedTo);

        checkContentsOfDtoList(weatherReadingDtoList.subList(0, 1), savedWeatherReadingDtoList);
    }

    @Test
    public void getWeatherReadingList_filterDomainTitleAndDateNoResult() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();
        String domainTitle = "NOVA_GORICA";
        Date updatedFrom = dateFormat.parse("1.1.1900");
        Date updatedTo = dateFormat.parse("1.1.1950");

        // save data
        saveAllEntities(weatherReadingDtoList);

        // retrieve data with filter
        List<WeatherReadingDto> savedWeatherReadingDtoList = weatherReadingDao.getWeatherReadingList(domainTitle, updatedFrom, updatedTo);

        Assert.assertEquals(0, savedWeatherReadingDtoList.size());
    }

    @Test
    public void deleteWeatherReadingList() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data
        saveAllEntities(weatherReadingDtoList);

        // delete
        weatherReadingDao.deleteWeatherReadingList(weatherReadingDtoList.subList(0, 2));

        // retrieve data
        List<WeatherReadingDto> savedWeatherReadingDtoList = getAllEntitiesByType(WeatherReadingDto.class);
        checkContentsOfDtoList(weatherReadingDtoList.subList(2, 3), savedWeatherReadingDtoList);
    }

    @Test
    public void deleteAllWeatherData() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data
        saveAllEntities(weatherReadingDtoList);

        // delete
        weatherReadingDao.deleteAllWeatherReading();

        // retrieve data
        List<WeatherReadingDto> savedWeatherReadingDtoList = getAllEntitiesByType(WeatherReadingDto.class);
        Assert.assertEquals(0, savedWeatherReadingDtoList.size());
    }
}
