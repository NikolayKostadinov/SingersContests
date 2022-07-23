package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateJuriMemberBindingModel {
    @NotNull(message = "Please choose a valid user!")
    private Long id;

    @NotBlank(message = "Details are required!")
    private String details;

    @NotBlank(message = "Picture is required!")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public CreateJuriMemberBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public CreateJuriMemberBindingModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateJuriMemberBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
