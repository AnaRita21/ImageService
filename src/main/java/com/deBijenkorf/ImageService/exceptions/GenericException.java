package com.deBijenkorf.ImageService.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error thrown when the requested source image does not exist
 */
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GenericException extends RuntimeException {

    private String process;
    private String type;

    public GenericException(String process, String type, String message) {
        super(message);
        this.process = process;
        this.type = type;
    }
}
