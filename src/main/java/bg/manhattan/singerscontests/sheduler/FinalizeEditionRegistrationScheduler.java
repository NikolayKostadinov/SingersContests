package bg.manhattan.singerscontests.sheduler;

import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FinalizeEditionRegistrationScheduler {

    private final EditionService editionService;

    public FinalizeEditionRegistrationScheduler(EditionService editionService) {
        this.editionService = editionService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void ScheduleEditionFinalize(){
        LocalDate targetDate = DateTimeProvider
                .getCurrent()
                .utcNow()
                .toLocalDate()
                .minusDays(1);
        this.editionService.generateScenarioOrder(targetDate);
    }
}
