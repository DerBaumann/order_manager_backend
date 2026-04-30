package ch.wegmann.pierre.order_manager.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
