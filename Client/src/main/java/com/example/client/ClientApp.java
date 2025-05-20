package com.example.client;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ClientApp {

    private final ApiClient apiClient = new ApiClient();

    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class, args);
    }

    @GetMapping("/call")
    public String callServer() {
        String url = "http://localhost:8081/service";

        try {
            return apiClient.callService(url);
        } catch (Exception e) {
            return "Request failed: " + e.getMessage();
        }
    }
}
