package bg.manhattan.singerscontests.model.view;

public class ManagerViewModel {
    private Long id;
    private String fullName;

    public Long getId() {
        return id;
    }

    public ManagerViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public ManagerViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
