package bg.manhattan.singerscontests.model.service;

public class PerformanceCategoryServiceModel {
    private Integer id;
    private String name;
    private boolean required;

    public Integer getId() {
        return id;
    }

    public PerformanceCategoryServiceModel setId(Integer id) {
        this.id = id;
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
}
