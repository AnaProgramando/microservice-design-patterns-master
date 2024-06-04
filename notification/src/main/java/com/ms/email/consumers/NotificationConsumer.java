package com.ms.email.consumers;

import com.ms.email.dtos.NotificationDto;
import com.ms.email.services.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${broker.queue.notification.name}" )
    public void listenEmailQueue(@Payload NotificationDto emailRecordDto) {
        notificationService.sendNotification(emailRecordDto);
    }

}
