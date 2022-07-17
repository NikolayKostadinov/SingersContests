package bg.manhattan.singerscontests.model.service;

public class PerformanceCategoryServiceModel {
    private Long id;

    public Integer displayNumber;
    private String name;
    private boolean required;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public PerformanceCategoryServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getDisplayNumber() {
        return displayNumber;
    }

    public PerformanceCategoryServiceModel setDisplayNumber(Integer displayNumber) {
        this.displayNumber = displayNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public PerformanceCategoryServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public PerformanceCategoryServiceModel setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public PerformanceCategoryServiceModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
