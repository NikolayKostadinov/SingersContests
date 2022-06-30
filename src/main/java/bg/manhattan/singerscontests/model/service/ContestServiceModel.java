package bg.manhattan.singerscontests.model.service;

public class ContestServiceModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public ContestServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContestServiceModel setName(String name) {
        this.name = name;
        return this;
    }
}
