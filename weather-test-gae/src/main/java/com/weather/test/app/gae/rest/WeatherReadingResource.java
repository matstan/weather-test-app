package com.weather.test.app.gae.rest;

import com.weather.test.app.data.storage.dao.WeatherReadingDao;
import com.weather.test.app.dm.dto.WeatherReadingDto;
import com.weather.test.app.gae.rest.utils.DateParsingUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("weatherReadingResource")
public class WeatherReadingResource {

    @Inject
    WeatherReadingDao weatherReadingDao;

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_HTML)
    public String getWeatherReadingListByQuery(@QueryParam("updatedFrom") String updatedFrom, @QueryParam("updatedTo") String updatedTo, @QueryParam("domainTitle") String domainTitle) {
        Date updatedFromDate = DateParsingUtils.parseDateFromString(updatedFrom);
        Date updatedToDate = DateParsingUtils.parseDateFromString(updatedTo);

        // if data is sent from html form, domainTitle may be empty, but never null,
        // therefore set it to null if it is empty for further checking to succeed
        if (domainTitle != null) {
            domainTitle = domainTitle.isEmpty() ? null : domainTitle;
        }

        String result = "updatedFrom: " + updatedFrom + " updatedTo: " + updatedTo + " domainTitle: " + domainTitle + "<br>";

        if (updatedFromDate != null && updatedToDate != null && domainTitle != null) {
            List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList(domainTitle, updatedFromDate, updatedToDate);
            result += WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
        } else if (updatedFromDate == null && updatedToDate == null && domainTitle != null) {
            List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList(domainTitle);
            result += WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
        } else if (updatedFromDate != null && updatedToDate != null && domainTitle == null) {
            List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList(updatedFromDate, updatedToDate);
            result += WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
        } else if (updatedFromDate == null && updatedToDate == null && domainTitle == null) {
            List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList();
            result += WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
        }else {
            result += "Please provide consistent input parameters.";
        }

        return result;
    }

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_HTML)
    public String getWeatherReadingList() {
        List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList();
        return WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
    }
}


