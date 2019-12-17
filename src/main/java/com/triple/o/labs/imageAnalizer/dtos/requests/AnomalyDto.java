package com.triple.o.labs.imageAnalizer.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnomalyDto {
    private float centralLeftLength;
    private float centralRightLength;
    private float lateralLeftLength;
    private float lateralRightLength;
}
