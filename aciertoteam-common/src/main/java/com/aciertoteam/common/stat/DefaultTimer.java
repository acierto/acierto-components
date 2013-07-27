package com.aciertoteam.common.stat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultTimer implements Timer {

    private static final Log LOG = LogFactory.getLog(DefaultTimer.class);

    private int current;
    private int percent;
    private int percentCount;
    private long startWorkflow;
    private long startTime;

    private static final int DIVIDER = 1000000;

    private DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS");

    public DefaultTimer() {
    }

    private void init() {
        current = 0;
        percentCount = 0;
    }

    public void start(int steps) {
        init();

        percent = steps / 100;
        startWorkflow = System.nanoTime();
        startTime = startWorkflow;

        LOG.info("Total size of items is: " + steps);
        LOG.info("Started time is: " + printTime());
    }

    public void next() {
        current++;
        if (percent < current) {
            current = 0;
            long time = (System.nanoTime() - startTime) / DIVIDER;
            LOG.info(String.format("Processed %s%% [Elapsed time is %sms]", ++percentCount, time));
            startTime = System.nanoTime();
        }
    }

    public long end() {
        long time = (System.nanoTime() - startWorkflow) / DIVIDER;
        long ms = time % 1000;
        long sec = time / 1000 % 60;
        long min = time / 1000 / 60 % 60;
        LOG.info(String.format("Processed 100%%. Total elapsed time is %smin %ss %sms", min, sec, ms));
        LOG.info("Ended time is: " + printTime());
        return time;
    }

    private String printTime() {
        return fmt.print(new Date().getTime());
    }
}

