package com.deBijenkorf.ImageService.exceptions;

import com.deBijenkorf.ImageService.entity.ErrorLog;
import com.deBijenkorf.ImageService.repository.ErrorLogsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception handler to store the logs in the database prior returning the service response
 */
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private ErrorLogsRepository errorLogsRepository;

    /**
     * Handles all other exceptions
     *
     * @param ex that was caught
     * @param request request is not used
     * @return error 404
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest request) {
        LOGGER.error("Message: " + ex.getMessage());
        LOGGER.error("Stack: " + ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);


        ErrorLog errorLog = new ErrorLog("Generic Error",ex.getMessage(),"Error", "" + System.currentTimeMillis() / 1000);
        errorLogsRepository.save(errorLog);

        return new ResponseEntity<>("Error optimizing the image, please review. " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles generic exceptions
     *
     * @param ex that was caught
     * @param request request is not used
     * @return error 404
     */
    @ExceptionHandler(GenericException.class)
    private ResponseEntity<Object> handleGenericExceptions(GenericException ex, HttpServletRequest request) {
        LOGGER.error("Generic error occurred: " + ex.getMessage());

        ErrorLog errorLog = new ErrorLog(ex.getProcess(),ex.getMessage(),ex.getType(), "" + System.currentTimeMillis() / 1000);
        errorLogsRepository.save(errorLog);

        return new ResponseEntity<>("Error optimizing the image, please review. " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles PredefinedImageException
     *
     * @param ex that was caught
     * @param request request is not used
     * @return error 404
     */
    @ExceptionHandler(PredefinedImageException.class)
    private ResponseEntity<Object> handlePredefinedImageException(PredefinedImageException ex, HttpServletRequest request) {
        LOGGER.info("Message: " + ex.getMessage());

        ErrorLog errorLog = new ErrorLog(ex.getProcess(), ex.getMessage(),ex.getType(), "" + System.currentTimeMillis() / 1000);
        errorLogsRepository.save(errorLog);

        return new ResponseEntity<>("Error optimizing the image, please review. " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles InsertBucketException
     *
     * @param ex that was caught
     * @param request request is not used
     * @return error 404
     */
    @ExceptionHandler(InsertBucketException.class)
    private ResponseEntity<Object> handleInsertBucketException(InsertBucketException ex, HttpServletRequest request) {
        LOGGER.info("Message: " + ex.getMessage());

        ErrorLog errorLog = new ErrorLog(ex.getProcess(), ex.getMessage(),ex.getType(), "" + System.currentTimeMillis() / 1000);
        errorLogsRepository.save(errorLog);

        return new ResponseEntity<>("Error optimizing the image, please review. " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles SourceImageException
     *
     * @param ex that was caught
     * @param request request is not used
     * @return error 404
     */
    @ExceptionHandler(SourceImageException.class)
    private ResponseEntity<Object> handleSourceImageException(SourceImageException ex, HttpServletRequest request) {
        LOGGER.info("Message: " + ex.getMessage());

        ErrorLog errorLog = new ErrorLog(ex.getProcess(), ex.getMessage(),ex.getType(), "" + System.currentTimeMillis() / 1000);
        errorLogsRepository.save(errorLog);

        return new ResponseEntity<>("Error optimizing the image, please review. " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
