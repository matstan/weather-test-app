package com.weather.test.app.dm;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by matic on 08/07/15.
 */
@Entity
public class WeatherReadingDto {

    @Id
    Long weatherReadingId;

    private String meteoId;

    @Index
    private String domainTitle;

    @Index
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
        return "objectifyId: " + weatherReadingId + " meteoId: " + meteoId + " domainTitle: " + domainTitle + " updated: " + updated + " temp: " + temp + " pressure: " + pressure + " humidity: " + humidity;
    }

    /**
     * Returns true if domainTitle and updated fields are equal.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass())) {
            return false;
        }
        WeatherReadingDto other = (WeatherReadingDto) obj;
        if (other.getDomainTitle().equalsIgnoreCase(this.getDomainTitle()) &&
                other.getUpdated().equals(this.getUpdated())) {
            return true;
        }

        return false;
    }
}
