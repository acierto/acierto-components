package com.aciertoteam.common.stat;

/**
 * Used for collecting statistic information about the time elapsed to execute the task.
 *
 * @author Bogdan Nechyporenko
 */
public interface Timer {

    /**
     * Starts the timer.
     *
     * @param steps The number of times next to be invoked. It is used to show the percentage of executed task.
     */
    void start(int steps);

    /**
     * Increases step counter during the time is running.
     */
    void next();

    /**
     * Stops the timer and prints the result to the user.
     */
    long end();
}
