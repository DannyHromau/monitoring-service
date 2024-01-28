package com.dannyhromau.monitoring.meter.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MeterReading {
    private long id;
    private MeterType meterType;
    private LocalDateTime date;
    private int value;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MeterType getMeterReadingType() {
        return meterType;
    }

    public void setMeterReadingType(MeterType meterType) {
        this.meterType = meterType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MeterReading{" +
                "meter's type = " + meterType.getType() +
                ", date = " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+
                ", value = " + value +
                '}';
    }
}
