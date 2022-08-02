package bg.manhattan.singerscontests.sheduler;

import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableScheduling
public class FinalizeEditionRegistrationScheduler {

    private final EditionService editionService;

    public FinalizeEditionRegistrationScheduler(EditionService editionService) {
        this.editionService = editionService;
    }

    @Scheduled(cron = "0 19 19 * * ?")
    public void ScheduleEditionFinalize(){
        LocalDate targetDate = DateTimeProvider
                .getCurrent()
                .utcNow()
                .toLocalDate()
                .minusDays(1);
        this.editionService.generateScenarioOrder(targetDate);
    }
}
