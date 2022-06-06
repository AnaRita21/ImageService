package com.deBijenkorf.ImageService.exceptions;

import lombok.Getter;

/**
 * Error thrown when the requested source image does not exist
 */
@Getter
public class SourceImageException extends RuntimeException {

    private String process;
    private String type;

    public SourceImageException(String process, String type, String message) {
        super(message);
        this.process = process;
        this.type = type;
    }
}

