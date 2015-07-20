package com.weather.test.app.gae.rest;

import com.weather.test.app.data.storage.WeatherReadingDao;
import com.weather.test.app.dm.WeatherReadingDto;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("weatherReadingResource")
public class WeatherReadingResource {

    @Inject
    WeatherReadingDao weatherReadingDao;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @GET
    @Path("/query")
    @Produces(MediaType.TEXT_HTML)
    public String getWeatherReadingList(@QueryParam("updatedAfter") String updatedAfterParam, @QueryParam("domainTitle") String domainTitle) {
//        Date updatedAfter = null;
//        try {
//            if (updatedAfterParam != null) {
//                updatedAfter = dateFormat.parse(updatedAfterParam);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        String u = updatedAfter != null ? updatedAfter.toString() : "emptyDate";

        //return "updatedAfter: " + u + " domainTitile: " + domainTitle;

        List<WeatherReadingDto> weatherReadingList = weatherReadingDao.getWeatherReadingList();
        return WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingList);

        //return WeatherReadingParser.convertWeatherReadingDtoList2String(weatherReadingDtoList);
    }
}
