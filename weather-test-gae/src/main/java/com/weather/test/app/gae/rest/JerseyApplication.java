package com.weather.test.app.gae.rest;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by matic on 14.7.2015.
 */
public class JerseyApplication extends ResourceConfig {

    private static Logger log = LoggerFactory.getLogger(JerseyApplication.class);

    public JerseyApplication() {
        log.info("Booting JerseyApplication...");
        register(new Hk2Binder());
        register(MultiPartFeature.class);
    }
}