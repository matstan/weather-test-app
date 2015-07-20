package com.weather.test.app.data.storage;

import com.googlecode.objectify.ObjectifyService;
import com.weather.test.app.dm.WeatherReadingDto;
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
         //return ObjectifyService.ofy().load().type(WeatherReadingDto.class).list();
        return null;
    }

    public List<WeatherReadingDto> getWeatherReadingList(String domainTitle, Date updatedAfter) {
        //List<WeatherReadingDto> weatherReadingDtoList = ObjectifyService.ofy().load().type(WeatherReadingDto.class).filter("domainTitle ==", domainTitle).list();
        return null;
    }


}
