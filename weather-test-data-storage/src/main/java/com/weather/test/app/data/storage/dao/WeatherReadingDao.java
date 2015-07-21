package com.weather.test.app.data.storage.dao;

import com.weather.test.app.dm.WeatherReadingDto;
import org.jvnet.hk2.annotations.Contract;

import java.util.Date;
import java.util.List;

@Contract
public interface WeatherReadingDao {

    void saveWeatherReadingList(List<WeatherReadingDto> weatherReadingDtoList);

    void saveWeatherReading(WeatherReadingDto weatherReadingDto);

    List<WeatherReadingDto> getWeatherReadingList();

    List<WeatherReadingDto> getWeatherReadingList(String domainTitle);

    List<WeatherReadingDto> getWeatherReadingList(Date updatedFrom, Date updatedTo);

    List<WeatherReadingDto> getWeatherReadingList(String domainTitle, Date updatedFrom, Date updatedTo);
}
