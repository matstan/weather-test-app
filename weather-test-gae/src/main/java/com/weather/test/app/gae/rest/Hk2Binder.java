package com.weather.test.app.gae.rest;

import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import com.weather.test.app.data.storage.WeatherReadingDao;
import com.weather.test.app.data.storage.WeatherReadingDaoOfyImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class Hk2Binder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(WeatherDataParserImpl.class).to(WeatherDataParser.class);
        bind(WeatherReadingDaoOfyImpl.class).to(WeatherReadingDao.class);
    }
}
