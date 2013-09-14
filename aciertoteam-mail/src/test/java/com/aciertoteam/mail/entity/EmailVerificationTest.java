package com.aciertoteam.mail.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import com.aciertoteam.bean.AbstractBeanTest;
import com.aciertoteam.mail.enums.RequestStatus;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author ishestiporov
 */
public class EmailVerificationTest extends AbstractBeanTest<EmailVerification> {

    public static final String TOKEN = "token";

    @Test
    public void testIsVerified() {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setRequestStatus(RequestStatus.ACCEPTED);
        assertTrue(emailVerification.isVerified());
    }

    @Test
    public void testUpdateVerificationStatus() {
        assertEmailVerificationStatusUpdate(new DateTime().plusDays(1).toDate(), TOKEN, RequestStatus.ACCEPTED);
    }

    @Test
    public void testUpdateVerificationStatusExpired() {
        assertEmailVerificationStatusUpdate(new DateTime().minusDays(1).toDate(), TOKEN, RequestStatus.EXPIRED);
    }

    @Test
    public void testUpdateVerificationStatusWrongToken() {
        assertEmailVerificationStatusUpdate(new DateTime().plusDays(1).toDate(), "token2", RequestStatus.FAILED);
    }

    private void assertEmailVerificationStatusUpdate(Date expiry, String token, RequestStatus expectedStatus) {
        EmailVerification emailVerification = createEmailVerification(expiry, token);
        assertEquals(expectedStatus, emailVerification.updateVerificationStatus(TOKEN));
    }

    private EmailVerification createEmailVerification(Date expiry, String token) {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.closeEndPeriod(expiry);
        emailVerification.setToken(token);
        return emailVerification;
    }
}
