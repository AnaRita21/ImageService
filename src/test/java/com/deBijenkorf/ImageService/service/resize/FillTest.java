package com.deBijenkorf.ImageService.service.resize;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;
import com.deBijenkorf.ImageService.service.resize.Fill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FillTest {

    private Fill fill = new Fill();

    @Test
    public void resize() {
        ImageTypeConfig.Properties properties = new ImageTypeConfig.Properties();
        properties.setHeight(10);
        properties.setWidth(10);
        properties.setFillColor("#000000");

        BufferedImage output = fill.resize(new BufferedImage(11,11,BufferedImage.TYPE_INT_RGB), properties);
        assertThat(output).isNotNull();
    }

}
