package com.triple.o.labs.imageAnalizer.dtos.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestDto {
    byte[] upper;
    byte[] lower;
}
