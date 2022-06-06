package com.deBijenkorf.ImageService.utils;

import com.deBijenkorf.ImageService.exceptions.GenericException;
import com.deBijenkorf.ImageService.utils.ImageUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ImageUtilsTest {

    @Test
    public void compress_OK() throws IOException {
        BufferedImage output = ImageUtils.compressImage(new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB), "jpg", 5);
        assertThat(output).isNotNull();
    }

    @Test(expected = GenericException.class)
    public void compress_fileNotExists() throws IOException {
        ImageUtils.compressImage(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB), null, 5);
    }
}
