package bg.manhattan.singerscontests;

import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
//todo: delete test command line runner!!!

@Component
public class ConsoleTest implements CommandLineRunner {
    private final EditionService editionService;

    public ConsoleTest(EditionService editionService) {
        this.editionService = editionService;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
