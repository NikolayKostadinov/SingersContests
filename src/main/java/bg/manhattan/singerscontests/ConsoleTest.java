package bg.manhattan.singerscontests;

import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
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
        Paged<EditionServiceModel> editions = this.editionService.getFutureEditions(1, 3);
        editions.getPage()
                .stream()
                .forEach(System.out::println);

        System.out.println("Page number: " + editions.getPaging().getPageNumber());
        System.out.println("Page size: " + editions.getPaging().getPageSize());
    }
}
