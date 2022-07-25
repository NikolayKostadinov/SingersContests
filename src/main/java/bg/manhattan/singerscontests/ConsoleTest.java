package bg.manhattan.singerscontests;

import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleTest implements CommandLineRunner {
    private final EditionService editionService;

    public ConsoleTest(EditionService editionService) {
        this.editionService = editionService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<LocalDate> dates = this.editionService.getDatesForMonth(7,2022);
        dates.stream().forEach(System.out::println);
    }
}
