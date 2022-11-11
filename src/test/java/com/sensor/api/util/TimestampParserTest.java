package com.sensor.api.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TimestampParserTest {



    @Test
    void shouldParseDateCorrectly() {
        LocalDateTime localDateTime = TimestampParser.parseDateTime("2003-06-30T20:00:00");

        assertThat(localDateTime, is(LocalDateTime.of(2003, 6, 30, 20, 0, 0)));
    }


}