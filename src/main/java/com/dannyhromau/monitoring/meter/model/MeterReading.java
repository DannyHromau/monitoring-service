package com.dannyhromau.monitoring.meter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeterReading {
    private long id;
    //TODO: implement getting meterType from db
    private MeterType meterType;
    private LocalDateTime date;
    private int value;
    private long userId;
    private long meterTypeId;

    @Override
    public String toString() {
        return "MeterReading{" +
                "meter's type = " + meterType.getType() +
                ", date = " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+
                ", value = " + value +
                '}';
    }
}
