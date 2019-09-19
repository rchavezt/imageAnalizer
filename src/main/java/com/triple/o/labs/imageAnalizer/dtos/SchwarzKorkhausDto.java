package com.triple.o.labs.imageAnalizer.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchwarzKorkhausDto {
    private String name;
    private float pointAX;
    private float pointAY;
    private String nameA;
    private float pointBX;
    private float pointBY;
    private String nameB;
    private boolean required;
    private String color;
}
