package com.weather.test.app.gae.rest;

import com.weather.test.app.data.parser.WeatherDataParser;
import com.weather.test.app.data.parser.WeatherDataParserImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by matic on 14.7.2015.
 */
public class Hk2Binder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(WeatherDataParserImpl.class).to(WeatherDataParser.class);
    }
}
