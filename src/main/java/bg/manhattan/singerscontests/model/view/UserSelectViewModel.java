package bg.manhattan.singerscontests.model.view;

public class UserSelectViewModel {
    private Long id;
    private String fullName;

    public Long getId() {
        return id;
    }

    public UserSelectViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserSelectViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
