package com.boog.enricher;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.boog.enricher.dto.EnrichmentResponse;
import com.boog.enricher.dto.ImageRequest;
import com.boog.enricher.service.HuggingFaceService;

@SpringBootApplication
public class EnricherApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnricherApplication.class, args);
	}

	@Bean
    private Function<ImageRequest, EnrichmentResponse> lambdaEnrichImage(HuggingFaceService hfService) {
        return request -> {
            // setup dynamodb later
            System.out.println("Received request for URL: " + request.getImageUrl());
            String caption = hfService.getCaption(request.getImageUrl());
            System.out.println("Generated caption: " + caption);
            return new EnrichmentResponse(request.getImageUrl(), caption);
        };
    }
}
