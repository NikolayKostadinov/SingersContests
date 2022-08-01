package bg.manhattan.singerscontests.sheduler;

import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.scheduling.annotation.Scheduled;

public class FinalizeEditionRegistrationScheduler {

    private final EditionService editionService;

    public FinalizeEditionRegistrationScheduler(EditionService editionService) {
        this.editionService = editionService;
    }

    @Scheduled(cron = "0 0 * * *")
    public void ScheduleEditionFinalize(){
        this.editionService.generateScenarioOrder();
    }
}
