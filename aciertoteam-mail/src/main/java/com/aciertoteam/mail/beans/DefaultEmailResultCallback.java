package com.aciertoteam.mail.beans;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.aciertoteam.mail.DeliveryResult;
import com.aciertoteam.mail.EmailResultCallback;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultEmailResultCallback implements EmailResultCallback {

    private DeliveryResult deliveryResult;

    private CountDownLatch latch = new CountDownLatch(1);

    public void notifyThatMessageIsReceived() {
        deliveryResult = DeliveryResult.createSuccessDeliveryResult();
        latch.countDown();
    }

    @Override
    public void notifyAboutFailedDelivery(String message) {
        deliveryResult = DeliveryResult.createFailedDeliveryResult(message);
        latch.countDown();
    }

    @Override
    public DeliveryResult getResult(long time, TimeUnit timeUnit) throws InterruptedException {
        if (latch.await(time, timeUnit)) {
            return deliveryResult;
        }

        return DeliveryResult.createFailedDeliveryResult("Timeout");
    }
}
