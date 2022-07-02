package bg.manhattan.singerscontests.init;

import bg.manhattan.singerscontests.services.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeAppDatabase implements CommandLineRunner {
    private final SeedService seedService;

    public InitializeAppDatabase(SeedService seedService) {
        this.seedService = seedService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.seedService.seed();
    }
}
