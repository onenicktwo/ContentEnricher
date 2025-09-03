package com.boog.enricher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrichmentResponse {
    private String imageUrl;
    private String caption;
}
