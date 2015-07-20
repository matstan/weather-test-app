package com.weather.test.app.data.storage.dao;

import com.googlecode.objectify.ObjectifyService;
import com.weather.test.app.data.storage.BaseUnitTest;
import com.weather.test.app.data.storage.WeatherRedingDataSetFactory;
import com.weather.test.app.data.storage.dao.WeatherReadingDao;
import com.weather.test.app.dm.WeatherReadingDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hk2.testing.junit.HK2Runner;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherReadingDaoOfyImplIntegrationTest extends BaseUnitTest {

    @Inject
    private WeatherReadingDao weatherReadingDao;

    @Test
    public void insertDataset() throws ParseException {
        List<WeatherReadingDto> weatherReadingDtoList = WeatherRedingDataSetFactory.createWeatherReadingDtoDataset();

        // save data
        weatherReadingDao.saveWeatherReadingList(weatherReadingDtoList);

        // retrieve data
        //List<WeatherReadingDto> weatherReadingDtoList = ObjectifyService.ofy().load().type(WeatherReadingDto.class).list();
    }
}
