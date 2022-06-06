package com.deBijenkorf.ImageService.exceptions;

import lombok.Getter;

/**
 * Error thrown when the requested predefined image type does not exist
 */
@Getter
public class PredefinedImageException extends RuntimeException {

    private String process;
    private String type;

    public PredefinedImageException(String process, String type, String message) {
        super(message);
        this.process = process;
        this.type = type;
    }

}

