package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication {

    private boolean flag = true;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @GetMapping("/service")
    public ResponseEntity<String> unstableService() {
        flag = !flag;
        if (flag) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
