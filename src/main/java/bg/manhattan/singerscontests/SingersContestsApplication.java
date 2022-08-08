package bg.manhattan.singerscontests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SingersContestsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SingersContestsApplication.class, args);
    }

}
