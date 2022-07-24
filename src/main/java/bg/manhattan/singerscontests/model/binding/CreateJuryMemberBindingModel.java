package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateJuryMemberBindingModel {
    @NotNull(message = "Please choose a valid user!")
    private Long id;

    @NotBlank(message = "Details are required!")
    private String details;

    @NotBlank(message = "Picture is required!")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public CreateJuryMemberBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public CreateJuryMemberBindingModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateJuryMemberBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
