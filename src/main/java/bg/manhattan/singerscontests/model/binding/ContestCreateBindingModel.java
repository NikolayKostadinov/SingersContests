package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ContestCreateBindingModel{
    @Size(min=5, max = 75, message = "Name must be between {min} and {max} characters long!")
    private String name;

    @NotNull(message = "There must be at least one manager!")
    @NotEmpty(message = "There must be at least one manager!")
    private List<@NotNull(message = "Please select valid manager!") Long> managers;

    public ContestCreateBindingModel() {
        this.managers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ContestCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getManagers() {
        return managers;
    }

    public ContestCreateBindingModel setManagers(List<Long> managers) {
        this.managers = managers;
        return this;
    }
}
