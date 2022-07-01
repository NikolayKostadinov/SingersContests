package bg.manhattan.singerscontests.model.binding;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.List;

public class ContestCreateBindingModel {
    @Size(min=5, max = 75, message = "Name must be between {min} and {max} characters long!")
    private String name;

    private List<Long> managersList;


    public String getName() {
        return name;
    }

    public ContestCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getManagersList() {
        return managersList;
    }

    public ContestCreateBindingModel setManagersList(List<Long> managersList) {
        this.managersList = managersList;
        return this;
    }
}
