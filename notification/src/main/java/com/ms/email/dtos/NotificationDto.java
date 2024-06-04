package com.ms.email.dtos;

import java.util.UUID;

public record NotificationDto(UUID userId,
                              String emailTo,
                              String subject,
                              String text) {
}
