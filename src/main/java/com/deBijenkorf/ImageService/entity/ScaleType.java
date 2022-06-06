package com.deBijenkorf.ImageService.entity;

import lombok.Getter;

/**
 * Types of resized when the new height and width are not in the same aspect ratio
 */
@Getter
public enum ScaleType {
    CROP, FILL, SKEW;
}
