package bg.manhattan.singerscontests.model.view;

public class AgeGroupViewModel {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public AgeGroupViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AgeGroupViewModel setName(String name) {
        this.name = name;
        return this;
    }
}
