package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.entity.Edition;

import java.util.List;

public class ContestServiceModelWithEditions {
    private Long id;
    private String name;
    private List<Edition> editions;

    public Long getId() {
        return id;
    }

    public ContestServiceModelWithEditions setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContestServiceModelWithEditions setName(String name) {
        this.name = name;
        return this;
    }

    public List<Edition> getEditions() {
        return editions;
    }

    public ContestServiceModelWithEditions setEditions(List<Edition> editions) {
        this.editions = editions;
        return this;
    }
}
