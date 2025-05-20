package com.example.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final CircuitBreaker circuitBreaker = new CircuitBreaker();
    public String callService(String url) throws IOException, InterruptedException {
        int attempts = 0;
        while (attempts < 2) {
            attempts++;
            if (!circuitBreaker.allowRequest()) {
                throw new IllegalStateException("Circuit Breaker is OPEN");
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() >= 500) {
                    circuitBreaker.failed();
                    if (attempts == 2) {
                        return "Server Error after 2 attempts";
                    }
                } else {
                    circuitBreaker.success();
                    return response.body();
                }
            } catch (IOException | InterruptedException e) {
                circuitBreaker.failed();
                if (attempts == 2) throw e;
            }
        }
        return "Failed after retries";
    }

}
