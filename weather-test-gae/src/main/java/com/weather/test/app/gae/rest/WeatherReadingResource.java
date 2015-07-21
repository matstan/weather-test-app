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
    public String getWeatherReadingList(@QueryParam("updatedFrom") String updatedFrom, @QueryParam("updatedTo") String updatedTo, @QueryParam("domainTitle") String domainTitle) {
        Date updatedFromDate = DateParsingUtils.parseDateFromString(updatedFrom);
        Date updatedToDate = DateParsingUtils.parseDateFromString(updatedTo);

        if (updatedFromDate == null || updatedToDate == null || domainTitle == null) {
            return "Please provide all input parameters.";
        }

        List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList(domainTitle, updatedFromDate, updatedToDate);
        return WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);
    }
}


