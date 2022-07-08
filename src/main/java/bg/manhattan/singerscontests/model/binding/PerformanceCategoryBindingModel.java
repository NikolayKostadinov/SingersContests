package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.entity.Edition;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PerformanceCategoryBindingModel {

    private Long id;
    @NotEmpty(message = "Name is required!")
    private String name;

    @NotNull(message = "Field is required")
    private boolean required;

    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public PerformanceCategoryBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PerformanceCategoryBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public PerformanceCategoryBindingModel setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public PerformanceCategoryBindingModel setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
