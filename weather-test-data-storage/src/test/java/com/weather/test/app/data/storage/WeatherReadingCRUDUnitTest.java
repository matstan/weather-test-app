package com.weather.test.app.data.storage;

import com.google.appengine.api.datastore.*;
import com.googlecode.objectify.ObjectifyService;
import com.weather.test.app.dm.WeatherReadingDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.weather.test.app.data.storage.WeatherRedingDataSetFactory.createWeatherReadingDtoDataset;
import static com.weather.test.app.data.storage.WeatherRedingDataSetFactory.createWeatherReadingEntityDataset;

@RunWith(MockitoJUnitRunner.class)
public class WeatherReadingCRUDUnitTest extends BaseUnitTest {

    @Test
    public void insertDataOverDatastoreService() throws ParseException {
        // expected dataset, inserted into datastore
        List<WeatherReadingDto> expectedDataset = createWeatherReadingDtoDataset();

        // first insert data
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        ds.put(createWeatherReadingEntityDataset(expectedDataset));

        // retrieve data
        Query q = new Query(WeatherReadingDto.class.getSimpleName());
        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = ds.prepare(q);

        List<WeatherReadingDto> weatherReadingDtoList = new ArrayList<WeatherReadingDto>();
        for (Entity entity : pq.asIterable()) {
            WeatherReadingDto weatherReadingDto = new WeatherReadingDto();
            weatherReadingDto.setUpdated((Date) entity.getProperty("updated"));
            weatherReadingDto.setDomainTitle((String) entity.getProperty("domainTitle"));
            weatherReadingDtoList.add(weatherReadingDto);
        }

        // check for equality of lists
        checkContentsOfDtoList(expectedDataset, weatherReadingDtoList);
    }

    @Test
    public void insertDataOverObjectify() throws ParseException {
        // expected dataset, inserted into datastore
        List<WeatherReadingDto> expectedDataset = createWeatherReadingDtoDataset();

        // first save generated data
        ObjectifyService.ofy().save().entities(expectedDataset);

        // obtain generated data
        List<WeatherReadingDto> weatherReadingDtoList = ObjectifyService.ofy().load().type(WeatherReadingDto.class).list();

        // check for equality of lists
        checkContentsOfDtoList(expectedDataset, weatherReadingDtoList);
    }

    @Test
    public void getAllEntitiesByType() throws ParseException {
        // expected dataset, inserted into datastore
        List<WeatherReadingDto> expectedDataset = createWeatherReadingDtoDataset();

        // first save generated data
        saveAllEntities(expectedDataset);

        // obtain generated data
        List<WeatherReadingDto> weatherReadingDtoList = getAllEntitiesByType(WeatherReadingDto.class);

        // check for equality of lists
        checkContentsOfDtoList(expectedDataset, weatherReadingDtoList);
    }
}
