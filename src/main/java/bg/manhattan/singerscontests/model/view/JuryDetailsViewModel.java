package bg.manhattan.singerscontests.model.view;

public class JuryDetailsViewModel {
    private Long id;
    private String fullName;
    private String details;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public JuryDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public JuryDetailsViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public JuryDetailsViewModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public JuryDetailsViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
