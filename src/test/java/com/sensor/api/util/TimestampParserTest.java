package com.sensor.api.util;

import com.sensor.api.controller.MissingTimestampException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class TimestampParserTest {



    @Test
    void shouldParseDateCorrectly() {
        LocalDateTime localDateTime = TimestampParser.parseDateTime("2022-11-03T20:00:00");

        assertThat(localDateTime, is(LocalDateTime.of(2022, 11, 3, 20, 0, 0)));
    }

    @Test
    void shouldThrowMissingTimestampExceptionWhenNoTimestampIsProvided() {
        assertThrows(MissingTimestampException.class, () -> TimestampParser.parseDateTime(null));
    }


}