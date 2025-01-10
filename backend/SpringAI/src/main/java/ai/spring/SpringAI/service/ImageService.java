package ai.spring.SpringAI.service;

import groovy.util.logging.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageService {

    private static final Logger log = LogManager.getLogger(ImageService.class);
    @Autowired
    private OpenAiImageModel openAiImageModel;

    public ImageResponse generateImage(String prompt, String quality, int n, int width, int height){
        try {
            ImageResponse imageResponse = openAiImageModel.call(
                    new ImagePrompt(prompt,
                            OpenAiImageOptions.builder()
                                    .withModel("dall-e-2")
                                    .withQuality(quality)
                                    .withN(n)
                                    .withHeight(height)
                                    .withWidth(width).build())
            );
            return imageResponse;
        } catch (Exception e) {
            log.error("Error occurred while generating images: {}", e.getMessage());
            return null;
        }
    }
}
