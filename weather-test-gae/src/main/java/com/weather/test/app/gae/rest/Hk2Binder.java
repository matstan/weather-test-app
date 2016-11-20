package com.weather.test.app.gae.rest;

import com.weather.test.app.data.datastore.dao.WeatherReadingDao;
import com.weather.test.app.data.datastore.dao.WeatherReadingDaoOfyImpl;
import com.weather.test.app.data.gcs.GcsFileService;
import com.weather.test.app.data.gcs.GcsFileServiceImpl;
import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class Hk2Binder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(WeatherDataParserImpl.class).to(WeatherDataParser.class).in(Singleton.class);
        bind(WeatherReadingDaoOfyImpl.class).to(WeatherReadingDao.class).in(Singleton.class);
        bind(GcsFileServiceImpl.class).to(GcsFileService.class).in(Singleton.class);
    }
}
