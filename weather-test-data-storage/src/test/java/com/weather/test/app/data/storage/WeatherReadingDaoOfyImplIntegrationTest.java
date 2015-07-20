package com.weather.test.app.data.storage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.hk2.testing.junit.HK2Runner;
import org.mockito.runners.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class WeatherReadingDaoOfyImplIntegrationTest extends HK2Runner {

    @Inject
    private WeatherReadingDao weatherReadingDao;

    @Test
    public void initialTest() {
        System.out.println("TEST!!");
    }
}
