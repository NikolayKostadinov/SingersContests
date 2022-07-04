package bg.manhattan.singerscontests.model.view;

import java.util.Collection;

public class ContestEditionsViewModel {
    private Long id;
    private String name;
    private Collection<EditionListViewModel> editions;

    public Long getId() {
        return id;
    }

    public ContestEditionsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContestEditionsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Collection<EditionListViewModel> getEditions() {
        return editions;
    }

    public ContestEditionsViewModel setEditions(Collection<EditionListViewModel> editions) {
        this.editions = editions;
        return this;
    }
}
