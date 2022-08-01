package bg.manhattan.singerscontests.sheduler;

import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class FinalizeEditionRegistrationScheduler {

    private final EditionService editionService;

    public FinalizeEditionRegistrationScheduler(EditionService editionService) {
        this.editionService = editionService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void ScheduleEditionFinalize(){
        this.editionService.generateScenarioOrder();
    }
}
