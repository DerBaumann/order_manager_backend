package ch.wegmann.pierre.order_manager.core;

import ch.wegmann.pierre.order_manager.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        return new ResponseEntity<>(Map.of("message", "Hello, Spring!"), HttpStatus.OK);
    }
}
