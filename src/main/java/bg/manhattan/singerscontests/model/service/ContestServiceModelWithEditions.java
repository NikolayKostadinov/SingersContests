package bg.manhattan.singerscontests.model.service;

import org.springframework.data.domain.Page;

public class ContestServiceModelWithEditions {
    private Long id;
    private String name;
    private Page<EditionServiceModel> editions;

    public ContestServiceModelWithEditions(Long id, String name, Page<EditionServiceModel> editions) {
        this.id = id;
        this.name = name;
        this.editions = editions;
    }

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

    public Page<EditionServiceModel> getEditions() {
        return editions;
    }

    public ContestServiceModelWithEditions setEditions(Page<EditionServiceModel> editions) {
        this.editions = editions;
        return this;
    }
}
