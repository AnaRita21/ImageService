package com.deBijenkorf.ImageService.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.deBijenkorf.ImageService.service.AwsService;
import org.mockito.internal.util.reflection.FieldSetter;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AwsServiceTest {

    @InjectMocks
    private AwsService awsService;

    @Mock
    private AmazonS3 client;

    @BeforeAll
    public void before() throws NoSuchFieldException {
        FieldSetter fieldSetter = new FieldSetter(awsService, awsService.getClass().getDeclaredField("bucket"));
        fieldSetter.set("testBucket");
    }

    @Test
    public void getImageFromBucket() throws IOException {
        S3Object object = new S3Object();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(new BufferedImage(11,11,BufferedImage.TYPE_INT_RGB), "jpeg", os);                          // Passing: ​(RenderedImage im, String formatName, OutputStream output)
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        object.setObjectContent(is);

        when(client.getObject(anyString(), anyString())).thenReturn(object);
        BufferedImage output = awsService.getImageFromBucket("Test", "type");
        assertThat(output).isNotNull();
    }

    @Test
    public void getImageFromBucket_notExists() {

        when(client.getObject(anyString(), anyString())).thenThrow(new AmazonS3Exception("error"));
        BufferedImage output = awsService.getImageFromBucket("Test", "type");
        assertThat(output).isNull();
    }

}
