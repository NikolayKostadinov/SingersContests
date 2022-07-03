package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ContestEditBindingModel {
    @NotNull(message = "Contest not found")
    private Long id;

    @Size(min=5, max = 75, message = "Name must be between {min} and {max} characters long!")
    private String name;

    @NotNull
    @NotEmpty(message = "There must be at least one manager!")
    private List<@NotNull Long> managers;

    public ContestEditBindingModel() {
        this.managers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public ContestEditBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContestEditBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getManagers() {
        return managers;
    }

    public ContestEditBindingModel setManagers(List<Long> managers) {
        this.managers = managers;
        return this;
    }
}
