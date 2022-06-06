package com.deBijenkorf.ImageService.controller;

import com.deBijenkorf.ImageService.entity.ImageWrapper;
import com.deBijenkorf.ImageService.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Class that exposes image service that optimize or delete the selected image from bucket
 * The return of each endpoint is a success or failure message and a return code.
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService service;

    /**
     * Run the optimization process for a given image
     *
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @param seoName - optional field - non-used parameter that could be used to trick search engines into
     *                understanding the image a bit more
     * @param reference - unique filename and/or relative path to identify the original image
     * @return is a success or failure message and a return code
     */
    @GetMapping(value = {"/show/{predefinedTypeName}/{seoName}","/show/{predefinedTypeName}"})
    public ResponseEntity<ByteArrayResource> requestOptimizedImage(
            @PathVariable String predefinedTypeName,
            @PathVariable (required = false) String seoName,
            @RequestParam String reference) throws IOException {

        ImageWrapper data = service.requestOptimizedImage(reference, predefinedTypeName);

        ByteArrayResource resource = new ByteArrayResource(data.getImage());

        return ResponseEntity
                .ok()
                .contentLength(data.getImage().length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + data.getImageName() + "\"")
                .body(resource);
    }

    /**
     * Run the flush process for a given image
     *
     * @param predefinedTypeName name of the definition type, for example, thumbnail understanding
     *                           the image a bit more
     * @param reference - unique filename and/or relative path to identify the original image
     * @return is a success or failure message and a return code
     */
    @DeleteMapping(value = {"/flush/{predefinedTypeName}"})
    public ResponseEntity<String> flushImage(
            @PathVariable String predefinedTypeName,
            @RequestParam String reference) throws IOException {

        String data = service.flushImage(reference, predefinedTypeName);

        return new ResponseEntity<>(data, HttpStatus.OK);

    }


}
