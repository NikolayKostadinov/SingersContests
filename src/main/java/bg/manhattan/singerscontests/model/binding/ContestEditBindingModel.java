package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotNull;

public class ContestEditBindingModel extends ContestCreateBindingModel{

    @NotNull(message = "Contest not found")
    private Long id;

    public Long getId() {
        return id;
    }

    public ContestEditBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}
