package com.aciertoteam.mail.services.impl;

import com.aciertoteam.mail.EmailResultCallback;
import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.services.EmailService;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.aciertoteam.mail.routers.EmailRouteBuilder.MessageContainer;

/**
 * @author Bogdan Nechyporenko
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DefaultEmailService implements EmailService {

    private CamelContext camelContext;

    private Executor executor = Executors.newFixedThreadPool(5);

    @Value("${send.email.endpoint}")
    private String sendEmailEndpoint;

    @Autowired
    public DefaultEmailService(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void sendMessage(final NotificationDTO notification, final EmailResultCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MessageContainer container = new MessageContainer(notification, callback);
                camelContext.createProducerTemplate().sendBody(sendEmailEndpoint, container);
            }
        });
    }
}
