package ai.spring.SpringAI.controller;

import ai.spring.SpringAI.service.ChatService;
import ai.spring.SpringAI.service.ImageService;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class GenAIController {
    private static final Logger log = LoggerFactory.getLogger(GenAIController.class);
    @Autowired
    private ChatService chatService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/chat")
    public ResponseEntity<String> getResponse(@RequestParam String prompt) {
        log.info("Getting response...");
        try {
            String response = chatService.getResponse(prompt);
            if(null != response) {
                log.info("Response received successfully.");
                return ResponseEntity.ok().body(response);
            } else {
                log.debug("Failed to generate response");
                return ResponseEntity.badRequest().body("Failed to generate response, please try again.");
            }
        } catch (Exception e) {
            log.error("Error occurred while getting response: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Something went wrong while getting response");
        }
    }

    @GetMapping("/chat/options")
    public ResponseEntity<String> getResponseOptions(@RequestParam String prompt) {
        log.info("Getting response as options...");
        try {
            String responseOptions = chatService.getResponseOptions(prompt);
            if(null != responseOptions) {
                log.info("Response as options received successfully.");
                return ResponseEntity.ok().body(responseOptions);
            } else {
                log.debug("Failed to generate response as options");
                return ResponseEntity.badRequest().body("Failed to generate response, please try again.");
            }
        } catch (Exception e) {
            log.error("Error occurred while getting response as options: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Something went wrong while getting response");
        }
    }

    @GetMapping("/generate-image")
    public ResponseEntity<List<String>> generateImages(HttpServletResponse response,
                                       @RequestParam String prompt,
                                       @RequestParam(defaultValue = "hd") String quality,
                                       @RequestParam(defaultValue = "1") int n,
                                       @RequestParam(defaultValue = "1024") int width,
                                       @RequestParam(defaultValue = "1024") int height) {
        log.info("Generating image...");
        try {
            ImageResponse imageResponse = imageService.generateImage(prompt, quality, n, width, height);
            if(null != imageResponse) {
                List<String> imageUrls = imageResponse.getResults().stream()
                        .map(result -> result.getOutput().getUrl())
                        .toList();
                log.info("Image generated successfully.");
                return ResponseEntity.ok().body(imageUrls);
            } else {
                log.info("Failed to generate image.");
                return ResponseEntity.badRequest().body(Collections.singletonList("Failed to generate image, please try again."));
            }
        } catch (Exception e) {
            log.error("Something went wrong while generating image.");
            return ResponseEntity.internalServerError().body(Collections.singletonList("Something went wrong while generating image."));
        }
    }
}
