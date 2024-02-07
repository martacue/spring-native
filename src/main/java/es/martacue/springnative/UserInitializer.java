package es.martacue.springnative;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@AllArgsConstructor
@Component
public class UserInitializer implements InitializingBean {

    private UserService userService;
    @Override
    public void afterPropertiesSet() {
        userService.save(new User("admin", "pass", "ADMIN"));
        userService.save(new User("user", "pass", "USER"));
        var newUsers = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            newUsers.add(new User("user" + i, "pass", "USER"));
        }
        userService.saveAll(newUsers);
    }
}
