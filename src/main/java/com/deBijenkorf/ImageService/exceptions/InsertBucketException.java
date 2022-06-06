package com.deBijenkorf.ImageService.exceptions;

import lombok.Getter;

/**
 * Error thrown when the requested has a problem writing the image to the S3 storage
 */
@Getter
public class InsertBucketException extends RuntimeException {

    private String process;
    private String type;

    public InsertBucketException(String process, String type, String message) {
        super(message);
        this.process = process;
        this.type = type;
    }

}
