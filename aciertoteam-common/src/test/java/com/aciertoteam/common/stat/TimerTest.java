package com.aciertoteam.common.stat;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Bogdan Nechyporenko
 */
public class TimerTest {

    private DefaultTimer timer = new DefaultTimer();

    @Test
    public void timerTest() {
        int n = 10;
        timer.start(n);
        for (int i = 0; i < n; i++) {
            timer.next();
        }

        assertTrue(timer.end() > 0);
    }
}
