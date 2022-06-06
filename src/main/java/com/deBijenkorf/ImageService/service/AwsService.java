package com.deBijenkorf.ImageService.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.deBijenkorf.ImageService.exceptions.InsertBucketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Service to manage the calls to AWS bucket
 */
@Service
public class AwsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsService.class);

    @Value("${application.bucket.name}")
    private String bucket;

    @Autowired
    private AmazonS3 client;

    /**
     * Get a specific image from desired bucket
     * @param fileName unique filename and/or relative path to identify the original image
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @return image from the bucket
     */
    public BufferedImage getImageFromBucket(String fileName, String predefinedTypeName){
        String bucketPath = bucket + "/" +predefinedTypeName ;

        try {
            S3Object object = client.getObject(bucketPath, fileName);

            ImageInputStream iin = ImageIO.createImageInputStream(object.getObjectContent());
            return ImageIO.read(iin);

        } catch (AmazonS3Exception | IOException e) {
            LOGGER.info("DownloadImage - image not found in bucket: " + bucketPath);
            return null;
        }
    }

    /**
     * Upload image to a specific bucket
     * @param image to upload
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @param type extension of the image to upload
     * @param fileName unique filename and/or relative path to identify the original image
     * @param throwError boolean to validate if retries to upload image again or throw error
     */
    public void uploadImageToBucket (BufferedImage image,
                                     String predefinedTypeName,
                                     String type,
                                     String fileName,
                                     boolean throwError) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, type, os);
            byte[] buffer = os.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);

            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(buffer.length);
            client.putObject(new PutObjectRequest(bucket + "/" + predefinedTypeName, fileName, is, meta));

            LOGGER.info("Image uploaded to bucket " + bucket + "/" + predefinedTypeName);
        } catch (IOException e) {
            try {
                if (throwError) {
                    throw new InsertBucketException("uploadFile", "WARNING", "There is a problem writing the image to the S3 storage.");
                } else{
                    Thread.sleep(200);
                    uploadImageToBucket(image,predefinedTypeName,type, fileName, true );
                }

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    /**
     * Deletes image from specific bucket
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @param fileName unique filename and/or relative path to identify the original image
     */
    public void flushImage (String fileName, String predefinedTypeName){
        client.deleteObject(bucket + "/" + predefinedTypeName, fileName);

    }


}
