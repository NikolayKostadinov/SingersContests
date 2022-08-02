package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.pageing.Paged;

import java.time.LocalDate;
import java.util.Collection;

public class ContestEditionsViewModel {
    private Long id;
    private String name;
    private Paged<EditionListViewModel> editions;

    public ContestEditionsViewModel(Long id, String name, Paged<EditionListViewModel> editions) {
        this.id = id;
        this.name = name;
        this.editions = editions;
    }

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

    public Paged<EditionListViewModel> getEditions() {
        return editions;
    }

    public ContestEditionsViewModel setEditions(Paged<EditionListViewModel> editions) {
        this.editions = editions;
        return this;
    }
}
