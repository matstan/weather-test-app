package com.weather.test.app.data.storage.dao;

import com.googlecode.objectify.ObjectifyService;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import org.jvnet.hk2.annotations.Service;

import java.util.Date;
import java.util.List;

@Service
public class WeatherReadingDaoOfyImpl implements WeatherReadingDao {

    public void saveWeatherReadingList(List<WeatherReadingDto> weatherReadingDtoList) {
        ObjectifyService.ofy().save().entities(weatherReadingDtoList).now();
    }

    public void saveWeatherReading(WeatherReadingDto weatherReadingDto) {
        ObjectifyService.ofy().save().entity(weatherReadingDto).now();
    }

    public List<WeatherReadingDto> getWeatherReadingList() {
        return ObjectifyService.ofy().load().type(WeatherReadingDto.class).list();
    }

    public List<WeatherReadingDto> getWeatherReadingList(String domainTitle) {
        return ObjectifyService.ofy().load().type(WeatherReadingDto.class)
                                .filter("domainTitle ==", domainTitle).list();
    }

    public List<WeatherReadingDto> getWeatherReadingList(Date updatedFrom, Date updatedTo) {
        return ObjectifyService.ofy().load().type(WeatherReadingDto.class)
                                .filter("updated <=", updatedTo)
                                .filter("updated >", updatedFrom)
                                .list();
    }

    public List<WeatherReadingDto> getWeatherReadingList(String domainTitle, Date updatedFrom, Date updatedTo) {
        return ObjectifyService.ofy().load().type(WeatherReadingDto.class)
                                .filter("domainTitle ==", domainTitle)
                                .filter("updated <=", updatedTo)
                                .filter("updated >", updatedFrom)
                                .list();
    }


}
