package com.triple.o.labs.imageAnalizer.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationResponseDto {
    private Long id;
    private String message;
    private boolean isRead;
}
