package bg.manhattan.singerscontests.events;

import bg.manhattan.singerscontests.model.view.PerformanceIssueViewModel;
import org.springframework.context.ApplicationEvent;

public class PerformanceIssueEvent extends ApplicationEvent {
    private final PerformanceIssueViewModel performanceProblem;

    public PerformanceIssueEvent(Object source, PerformanceIssueViewModel performanceProblem) {
        super(source);
        this.performanceProblem = performanceProblem;
    }

    public PerformanceIssueViewModel getPerformanceProblem() {
        return performanceProblem;
    }
}
