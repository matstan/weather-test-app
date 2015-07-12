package com.weather.test.app.gae;

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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initializing hk2 ServiceLocator");

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();


        ServletContext ctx = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Destroying hk2 ServiceLocator");

    }
}
