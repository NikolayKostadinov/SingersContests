package bg.manhattan.singerscontests.model.view;

public class ContestViewModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public ContestViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ContestViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
