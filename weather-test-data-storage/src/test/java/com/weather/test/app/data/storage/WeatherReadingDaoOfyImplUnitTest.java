package com.weather.test.app.data.storage;

import com.google.appengine.api.datastore.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.weather.test.app.dm.WeatherReadingDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.internal.runners.JUnit44RunnerImpl;
import org.mockito.runners.MockitoJUnitRunner;
import sun.plugin.services.WNetscape4BrowserService;

import java.io.Closeable;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WeatherReadingDaoOfyImplUnitTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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

    @Test
    public void testInsertDataOverDatastoreService() throws ParseException {
        // first insert data
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        ds.put(createWeatherReadingEntityDataset(createWeatherReadingDtoDataset()));

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
        checkContentsOfDtoList(weatherReadingDtoList);
    }

    @Test
    public void testInsertDataOverObjectify() throws ParseException {
        // first save generated data
        ObjectifyService.ofy().save().entities(createWeatherReadingDtoDataset());

        // obtain generated data
        List<WeatherReadingDto> weatherReadingDtoList = ObjectifyService.ofy().load().type(WeatherReadingDto.class).list();

        // check for equality of lists
        checkContentsOfDtoList(weatherReadingDtoList);
    }

    private void checkContentsOfDtoList(List<WeatherReadingDto> weatherReadingDtoList) throws ParseException {
        // obtain reference dto list
        List<WeatherReadingDto> expectedDtoList = createWeatherReadingDtoDataset();

        // check list size
        Assert.assertEquals(expectedDtoList.size(), weatherReadingDtoList.size());

        for (WeatherReadingDto weatherReadingDto : weatherReadingDtoList) {
            Assert.assertTrue(expectedDtoList.contains(weatherReadingDto));
        }
    }

    private List<WeatherReadingDto> createWeatherReadingDtoDataset() throws ParseException {
        List<WeatherReadingDto> dataSet = new ArrayList<WeatherReadingDto>(3);
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2000"), "NOVA_GORICA"));
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2001"), "LJUBLJANA"));
        dataSet.add(createWeatherReadingDto(dateFormat.parse("1.1.2002"), "KRANJ"));

        return dataSet;
    }
    private WeatherReadingDto createWeatherReadingDto(Date dateUpdated, String domainTitle) {
        WeatherReadingDto weatherReadingDto = new WeatherReadingDto();
        weatherReadingDto.setDomainTitle(domainTitle);
        weatherReadingDto.setUpdated(dateUpdated);

        return weatherReadingDto;
    }

    private List<Entity> createWeatherReadingEntityDataset(List<WeatherReadingDto> weatherReadingDtoList) {
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
