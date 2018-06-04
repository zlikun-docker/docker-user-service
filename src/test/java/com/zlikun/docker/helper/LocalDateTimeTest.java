package com.zlikun.docker.helper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@Slf4j
public class LocalDateTimeTest {

    @Test
    public void toEpochMilli() {
        LocalDateTime ldt = LocalDateTime.of(2018, 5, 28, 0, 0, 0, 0);
        assertEquals(1527436800000L, ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        assertEquals(1527436800000L, Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }

}
