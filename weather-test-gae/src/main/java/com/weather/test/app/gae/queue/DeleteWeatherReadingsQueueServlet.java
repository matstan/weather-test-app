package com.weather.test.app.gae.queue;

import com.weather.test.app.data.storage.dao.WeatherReadingDao;
import com.weather.test.app.gae.servlet.Hk2EnablementContextListener;
import org.glassfish.hk2.api.ServiceLocator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements worker logic for push queue worker.
 */
public class DeleteWeatherReadingsQueueServlet extends HttpServlet {

    /**
     * Deletes weather data from Datastore.
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceLocator serviceLocator = (ServiceLocator) getServletContext().getAttribute(Hk2EnablementContextListener.HK2_SERVICE_LOCATOR_ATTR_NAME);
        WeatherReadingDao weatherReadingDao = serviceLocator.getService(WeatherReadingDao.class);

        weatherReadingDao.deleteAllWeatherReading();
    }
}
