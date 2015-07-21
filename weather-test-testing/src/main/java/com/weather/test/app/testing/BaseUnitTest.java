package com.weather.test.app.testing;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.jvnet.hk2.testing.junit.HK2Runner;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Enables unit test for Datastore operations.
 */
public class BaseUnitTest extends HK2Runner {

    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    // New Objectify 5.1 Way. See https://groups.google.com/forum/#!topic/objectify-appengine/O4FHC_i7EGk
    protected Closeable session;

    @BeforeClass
    public static void setUpBeforeClass() {
        // Reset the Factory so that all translators work properly.
        ObjectifyService.setFactory(new ObjectifyFactory());
    }

    /** */
    @Before
    public void setUp() {
        // New Objectify 5.1 Way. See https://groups.google.com/forum/#!topic/objectify-appengine/O4FHC_i7EGk
        session = ObjectifyService.begin();
        ObjectifyService.ofy().factory().register(WeatherReadingDto.class);
        helper.setUp();
    }

    /** */
    @After
    public void tearDown() throws IOException {
        AsyncCacheFilter.complete();

        // New Objectify 5.1 Way. See https://groups.google.com/forum/#!topic/objectify-appengine/O4FHC_i7EGk
        session.close();
        helper.tearDown();
    }

    /**
     * Returns all entities of type entityType from Datastore.
     *
     * @param entityType
     * @param <T>
     * @return
     */
    public static <T> List<T> getAllEntitiesByType(Class<T> entityType) {
        List<T> entityList = ObjectifyService.ofy().load().type(entityType).list();

        return entityList;
    }

    /**
     * Saves entity list to datastore.
     *
     * @param entityList
     * @param <T>
     */
    public static <T> void saveAllEntities(List<T> entityList) {
        ObjectifyService.ofy().save().entities(entityList).now();
    }

    /**
     * Checks whether two lists contain the same WeatherReadingDto elements. Comparison is made according
     * to overriden equals method of the WeatherReadingDto class.
     *
     * @param expectedWeatherReadingDtoList
     * @param actualWeatherReadingDtoList
     * @throws ParseException
     */
    public static void checkContentsOfDtoList(List<WeatherReadingDto> expectedWeatherReadingDtoList, List<WeatherReadingDto> actualWeatherReadingDtoList) throws ParseException {
        // check list size
        Assert.assertEquals(expectedWeatherReadingDtoList.size(), actualWeatherReadingDtoList.size());

        // check list elements
        for (WeatherReadingDto weatherReadingDto : actualWeatherReadingDtoList) {
            Assert.assertTrue(expectedWeatherReadingDtoList.contains(weatherReadingDto));
        }
    }
}
