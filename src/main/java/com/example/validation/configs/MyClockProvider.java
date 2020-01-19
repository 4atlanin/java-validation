package com.example.validation.configs;

import javax.validation.ClockProvider;
import java.time.Clock;
import java.time.ZonedDateTime;
//Весь смысл этой шутки, определять текущий момент времени для аннотации @Past @Future
public class MyClockProvider implements ClockProvider {
    private Clock clock;


    public MyClockProvider(ZonedDateTime dateTime) {
        clock = Clock.fixed( dateTime.toInstant(), dateTime.getZone() );
    }

    @Override
    public Clock getClock() {
        return clock;
    }
}
