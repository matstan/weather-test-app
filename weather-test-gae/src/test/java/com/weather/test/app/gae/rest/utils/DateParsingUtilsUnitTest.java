package com.weather.test.app.gae.rest.utils;


import com.weather.test.app.testing.BaseUnitTest;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateParsingUtilsUnitTest extends BaseUnitTest {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    public void parseDateFromString_validDate() {
        String dateString = "01.01.2011";
        Date date = DateParsingUtils.parseDateFromString(dateString);

        String format = dateFormat.format(date);

        Assert.assertEquals(dateString, format);
    }

    @Test
    public void parseDateFromString_invalidDate() {
        String dateString = "45.2011";
        Date date = DateParsingUtils.parseDateFromString(dateString);
        Assert.assertNull(date);
    }
}
