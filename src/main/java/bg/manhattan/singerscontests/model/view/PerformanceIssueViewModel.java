package bg.manhattan.singerscontests.model.view;

public class PerformanceIssueViewModel {
    private final String source;
    private final Long executionTime;

    public PerformanceIssueViewModel(String source, Long executionTime) {
        this.source = source;
        this.executionTime = executionTime;
    }

    public String getSource() {
        return source;
    }

    public Long getExecutionTime() {
        return executionTime;
    }
}
