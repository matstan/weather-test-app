package com.weather.test.app.dm;

import java.util.Date;

/**
 * Created by matic on 08/07/15.
 */
public class WeatherReadingDto {

    private String meteoId;
    private String domainTitle;

    private Date updated;
    private double temp;
    private double humidity;
    private double pressure;


    public String getMeteoId() {
        return meteoId;
    }

    public void setMeteoId(String meteoId) {
        this.meteoId = meteoId;
    }

    public String getDomainTitle() {
        return domainTitle;
    }

    public void setDomainTitle(String domainTitle) {
        this.domainTitle = domainTitle;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "meteoId: " + meteoId + " domainTitle: " + domainTitle + " updated: " + updated + " temp: " + temp + " pressure: " + pressure + " humidity: " + humidity;
    }
}
