package com.finsight.finsight_ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeminiClient {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAnalysis(String prompt) {

	String url =
        	"https://generativelanguage.googleapis.com/v1beta/models/"
        	+ "gemini-3.5-flash-lite:generateContent";

        Map<String, Object> request = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", prompt)
                                }
                        )
                }
        );


	HttpHeaders headers = new HttpHeaders();
 	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.set("x-goog-api-key", apiKey);


        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response;

        try {
            response = restTemplate.postForEntity(
                    url,
                    entity,
                    String.class
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to connect to Gemini API: " + e.getMessage()
            );
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(
                    "Gemini API error: " + response.getBody()
            );
        }

        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.getBody());

            return root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse Gemini response: " + response.getBody(),
                    e
            );
        }
    }
}
