package bg.manhattan.singerscontests.model.service;

import java.util.List;

public class ContestCreateServiceModel {
    private String name;
    private List<Long> managers;

    public String getName() {
        return name;
    }

    public ContestCreateServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<Long> getManagers() {
        return managers;
    }

    public ContestCreateServiceModel setManagers(List<Long> managers) {
        this.managers = managers;
        return this;
    }
}
