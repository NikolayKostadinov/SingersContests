package bg.manhattan.singerscontests.sheduler;

import org.springframework.scheduling.annotation.Scheduled;

public class FinalizeEditionRegistrationScheduler {

    @Scheduled(cron = "0 0 * * *")
    public void ScheduleEditionFinalize(){

    }
}
