package bg.manhattan.singerscontests.model.binding;

public class PerformanceCategoryViewModel {
    private Long id;
    private String name;

    private Boolean required;

    public Long getId() {
        return id;
    }

    public PerformanceCategoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PerformanceCategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public PerformanceCategoryViewModel setRequired(Boolean required) {
        this.required = required;
        return this;
    }
}
