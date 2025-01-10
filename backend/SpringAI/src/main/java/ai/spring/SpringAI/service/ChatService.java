package ai.spring.SpringAI.service;

import groovy.util.logging.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatService {
    private static final Logger log = LogManager.getLogger(ChatService.class);
    @Autowired
    private ChatModel chatModel;

    public String getResponse(String prompt) {
        try {
            ChatResponse chatResponse = chatModel.call(
                    new Prompt(
                            prompt,
                            OpenAiChatOptions.builder()
                                    .withModel("gpt-4o")
                                    .withTemperature(0.3)
                                    .build()
                    ));
            return chatResponse.getResult().getOutput().getContent();
        } catch (Exception e) {
            log.error("Error occurred while generating the response {}", e.getMessage());
            return null;
        }
    }

    public String getResponseOptions(String prompt){
        try {
            ChatResponse response = chatModel.call(
                    new Prompt(
                            prompt,
                            OpenAiChatOptions.builder()
                                    .withModel("gpt-4o")
                                    .withTemperature(0.3)
                                    .build()
                    ));
            return response.getResult().getOutput().getContent();
        } catch (Exception e) {
            log.error("Error occurred while getting response as options: {}", e.getMessage());
            return null;
        }
    }
}
