package es.martacue.springnative;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class Controller {

    @PostMapping(value = "/perform_login")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequest loginRequest) {
        var userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(true);
    }


    @PostMapping(value = "users/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> create(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(true);
    }

    @GetMapping("users/{username}")
    public ResponseEntity<User> find(@PathVariable String username) {
        var start = System.nanoTime();
        var user = userService.findById(username);
        var end = System.nanoTime();
        log.info("Obtained user in {} ns", end - start);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public List<User> users() {
        var start = System.currentTimeMillis();
        var users =  userService.findAll();
        var end = System.currentTimeMillis();
        log.info("Obtained all users in {} ms", (end - start));
        var totalMemory = Runtime.getRuntime().totalMemory();
        var freeMemory = Runtime.getRuntime().freeMemory();
        var usedMemory = totalMemory - freeMemory;
        log.info("Total memory {}", totalMemory);
        log.info("Free memory {}", freeMemory);
        log.info("Used memory {}", usedMemory);
        return users;
    }

    @DeleteMapping("users/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        userService.deleteById(username);
        return ResponseEntity.noContent().build();
    }

    @Autowired private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;

}
