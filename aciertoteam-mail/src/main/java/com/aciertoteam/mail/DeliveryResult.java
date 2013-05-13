package com.aciertoteam.mail;

/**
 * @author Bogdan Nechyporenko
 */
public class DeliveryResult {

    private final boolean success;

    private final String message;

    private DeliveryResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static DeliveryResult createSuccessDeliveryResult() {
        return new DeliveryResult(true, null);
    }

    public static DeliveryResult createFailedDeliveryResult(String message) {
        return new DeliveryResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
