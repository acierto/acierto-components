package com.aciertoteam.mail;

import java.util.concurrent.TimeUnit;

/**
 * Asynchronous notifier about message deliveries.
 *
 * @author Bogdan Nechyporenko
 */
public interface EmailResultCallback {

    /**
     * Notify that the message has been received.
     *
     */
    void notifyThatMessageIsReceived();

    /**
     * Notify about the failure and include the reason message.
     *
     * @param message
     */
    void notifyAboutFailedDelivery(String message);

    /**
     * Waits for the result of message delivery.
     * If time of delivery exceeds the expected time the false will be return.
     *
     * @return
     */
    public DeliveryResult getResult(long time, TimeUnit timeUnit) throws InterruptedException;
}
