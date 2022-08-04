package bg.manhattan.singerscontests.init;

import bg.manhattan.singerscontests.services.EditionService;
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

    public PrepareActualScenarioOrders(@Value("${seed.initScenario : false}") boolean isRequiredScenarioInitialization, EditionService editionService) {
        this.isRequiredScenarioInitialization = isRequiredScenarioInitialization;
        this.editionService = editionService;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("----------------- Generate Scenario orders -----------------");
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();
        LocalDate beginOfMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);
        for (int i = 0; i < today.getDayOfMonth(); i++) {
            this.editionService.generateScenarioOrder(beginOfMonth.plusDays(i));
        }
    }
}