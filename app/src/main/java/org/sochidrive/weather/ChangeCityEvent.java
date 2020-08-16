package org.sochidrive.weather;

public class ChangeCityEvent {
    private String city;

    ChangeCityEvent(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
