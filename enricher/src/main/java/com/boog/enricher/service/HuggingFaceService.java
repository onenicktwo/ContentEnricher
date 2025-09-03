package com.boog.enricher.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Collections;

@Service
public class HuggingFaceService {

    // use env vars later
    private final String HF_API_KEY = "API_KEY";
    private final String API_URL = "https://api-inference.huggingface.co/models/nlpconnect/vit-gpt2-image-captioning";
    
    private final RestTemplate restTemplate = new RestTemplate();

    public String getCaption(String imageUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + HF_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String requestJson = "{\"inputs\":\"" + imageUrl + "\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        // slap this into a dto later
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);
        
        String responseBody = response.getBody();
        if (responseBody != null && responseBody.contains("generated_text")) {
            int start = responseBody.indexOf("generated_text\":\"") + 17;
            int end = responseBody.indexOf("\"}]");
            return responseBody.substring(start, end);
        }
        return "Caption could not be generated.";
    }
}
