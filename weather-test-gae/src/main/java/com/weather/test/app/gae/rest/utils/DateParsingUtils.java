package com.weather.test.app.gae.rest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParsingUtils {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Parses date from string of form "yyyy-MM-dd" to Date object.
     *
     * @param dateString String representing date of form "yyyy-MM-dd"
     * @return
     */
    public static Date parseDateFromString(String dateString) {
        Date parsedDate = null;
        try {
            if (dateString != null) {
                parsedDate = dateFormat.parse(dateString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}
