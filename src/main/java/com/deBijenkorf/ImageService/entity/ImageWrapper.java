package com.deBijenkorf.ImageService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageWrapper {
    private byte[] image;
    private String imageName;
}
