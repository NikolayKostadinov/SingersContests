package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotNull;

public class JuryMemberBindingModel {
    @NotNull(message = "Select valid manager!")
    private Long id;
    private boolean isDeleted;

    public Long getId() {
        return id;
    }

    public JuryMemberBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public JuryMemberBindingModel setDeleted(boolean deleted) {
        isDeleted = deleted;
        return this;
    }
}
