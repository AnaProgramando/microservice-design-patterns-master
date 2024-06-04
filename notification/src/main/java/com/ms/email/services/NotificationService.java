package com.ms.email.services;

import com.ms.email.dtos.NotificationDto;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(NotificationDto notification){
        System.out.println(notification);
    }

}
