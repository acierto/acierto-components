package com.aciertoteam.mail.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.aciertoteam.bean.AbstractBeanTest;
import com.aciertoteam.mail.enums.RequestStatus;
import org.junit.Test;

/**
 * @author ishestiporov
 */
public class EmailVerificationTest extends AbstractBeanTest<EmailVerification> {

    @Test
    public void testIsVerified() {
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setRequestStatus(RequestStatus.SUCCESS);
        assertTrue(emailVerification.isVerified());
    }
}
