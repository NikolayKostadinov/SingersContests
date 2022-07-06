package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.entity.Edition;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PerformanceCategoryBindingModel {

    public Edition edition;

    @NotEmpty(message = "Name is required!")
    public String name;

    @NotNull(message = "Field is required")
    private Boolean required;

    private boolean isDeleted;

    public Edition getEdition() {
        return edition;
    }

    public PerformanceCategoryBindingModel setEdition(Edition edition) {
        this.edition = edition;
        return this;
    }

    public String getName() {
        return name;
    }

    public PerformanceCategoryBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public PerformanceCategoryBindingModel setRequired(Boolean required) {
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
