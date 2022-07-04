package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.interfaces.Deletable;

import javax.validation.constraints.NotNull;

public class ManagerBindingModel implements Deletable {
    @NotNull(message = "Select valid manager!")
    private Long id;
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public ManagerBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public ManagerBindingModel setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
