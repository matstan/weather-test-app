package com.weather.test.app.data.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by matic on 08/07/15.
 */
@RunWith(JUnit4.class)
public class DateParsingUnitTest {

    @Test
    public void parseDateFromExampleString() throws ParseException {
        String target = "Thu Sep 28 20:29:30 JST 2000";
        DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        Date result =  df.parse(target);

        Assert.assertNotNull(result);
    }

    @Test
    public void parseDateFromString() throws ParseException {
        String target = "08.07.2015 17:32 UTC";
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        Date result =  df.parse(target);

        Assert.assertNotNull(result);

        Calendar c = Calendar.getInstance();
        c.setTime(result);

        Assert.assertEquals(8, c.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(6, c.get(Calendar.MONTH));
        Assert.assertEquals(2015, c.get(Calendar.YEAR));
        Assert.assertEquals(17, c.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(32, c.get(Calendar.MINUTE));

    }
}
