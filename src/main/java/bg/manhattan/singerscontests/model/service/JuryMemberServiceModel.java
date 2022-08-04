package bg.manhattan.singerscontests.model.service;

public class JuryMemberServiceModel {
    private Long id;
    private UserServiceModel user;
    private String details;
    private String imageUrl;

    public Long getId() {
        return id;
    }


    public JuryMemberServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public JuryMemberServiceModel setUser(UserServiceModel user) {
        this.user = user;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public JuryMemberServiceModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public JuryMemberServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
