package com.triple.o.labs.imageAnalizer.dtos.requests.points;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchwarzKorkhausDto {
    private String name;
    private PositionDto position;
    private boolean lineRelation;
    private String color;
    private boolean required;
    private boolean extendedLine;
}
