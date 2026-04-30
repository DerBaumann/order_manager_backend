package ch.wegmann.pierre.order_manager.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(new User(420), HttpStatus.OK);
    }
}
