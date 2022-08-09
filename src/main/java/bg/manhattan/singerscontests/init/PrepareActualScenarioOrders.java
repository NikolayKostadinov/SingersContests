package bg.manhattan.singerscontests.init;

import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.SeedService;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PrepareActualScenarioOrders implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareActualScenarioOrders.class);
    private final boolean isRequiredScenarioInitialization;
    private final EditionService editionService;
    private final SeedService seedService;

    public PrepareActualScenarioOrders(@Value("${seed.init_scenario:false}") boolean isRequiredScenarioInitialization,
                                       EditionService editionService,
                                       SeedService seedService) {
        this.isRequiredScenarioInitialization = isRequiredScenarioInitialization;
        this.editionService = editionService;
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (isRequiredScenarioInitialization) {
            LOGGER.info("----------------- Generate Scenario orders -----------------");
            LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();
            LocalDate beginOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
            for (int i = 0; i < today.getDayOfMonth(); i++) {
                this.editionService.generateScenarioOrder(beginOfMonth.plusDays(i));
            }

            this.seedService.seedRatingsForFirstEdition();
        }
    }
}
