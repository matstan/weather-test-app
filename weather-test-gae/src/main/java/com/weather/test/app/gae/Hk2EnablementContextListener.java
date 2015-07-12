package com.weather.test.app.gae;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by matic on 12/07/15.
 */
public class Hk2EnablementContextListener implements ServletContextListener {

    private static Logger log = LoggerFactory.getLogger(Hk2EnablementContextListener.class);

    public static final String HK2_SERVICE_LOCATOR_ATTR_NAME = "serviceLocator";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initializing hk2 ServiceLocator");

        ServiceLocator serviceLocator = ServiceLocatorUtilities.createAndPopulateServiceLocator("weather-test-service-locator");

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute(HK2_SERVICE_LOCATOR_ATTR_NAME, serviceLocator);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Destroying hk2 ServiceLocator");

        ServletContext ctx = sce.getServletContext();
        ServiceLocator serviceLocator = (ServiceLocator) ctx.getAttribute(HK2_SERVICE_LOCATOR_ATTR_NAME);
        serviceLocator.shutdown();
    }
}
