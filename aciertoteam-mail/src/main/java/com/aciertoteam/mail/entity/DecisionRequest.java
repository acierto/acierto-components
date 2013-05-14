package com.aciertoteam.mail.entity;

import com.aciertoteam.common.interfaces.Identifiable;
import com.aciertoteam.mail.enums.RequestStatus;

/**
 * Marker interface for the requests which changes the status to REJECTED/SUCCESS only by manual user decision.
 *
 * @author Bogdan Nechyporenko
 */
public interface DecisionRequest extends Identifiable {

    /**
     * Returns the current status of the request.
     *
     * @return
     */
    RequestStatus getRequestStatus();

    /**
     * Changes the status of request to REJECTED status
     */
    void reject();

    /**
     * Changes the status of request to SUCCESS status
     */
    void approve();

    /**
     * The email of recipient where this notification to be sent
     *
     * @return
     */
    String getRecipient();
}
