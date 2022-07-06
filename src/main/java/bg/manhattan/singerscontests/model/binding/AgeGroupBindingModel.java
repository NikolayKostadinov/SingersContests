package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AgeGroupBindingModel {
    @NotEmpty(message = "Name is required!")
    @Size(max = 50, message = "Name must be less than {max} characters!")
    private String name;

    @NotEmpty(message = "Minimal age is required!")
    @Positive(message = "Minimal age must be a positive number!")
    private Integer minAge;

    @NotEmpty(message = "Maximal age is required!")
    @Positive(message = "Maximal age must be a positive number!")
    private Integer maxAge;

    private Long editionId;

    public String getName() {
        return name;
    }

    public AgeGroupBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public AgeGroupBindingModel setMinAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public AgeGroupBindingModel setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public AgeGroupBindingModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }
}
