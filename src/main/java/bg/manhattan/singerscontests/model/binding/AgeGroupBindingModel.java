package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AgeGroupBindingModel {
    private Long id;
    @NotEmpty(message = "Name is required!")
    @Size(max = 50, message = "Name must be less than {max} characters!")
    private String name;

    @NotNull(message = "Minimal age is required!")
    @Positive(message = "Minimal age must be a positive number!")
    private Integer minAge;

    @NotNull(message = "Maximal age is required!")
    @Positive(message = "Maximal age must be a positive number!")
    private Integer maxAge;

    private boolean deleted;


    public Long getId() {
        return id;
    }

    public AgeGroupBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

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

    public boolean isDeleted() {
        return deleted;
    }

    public AgeGroupBindingModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
