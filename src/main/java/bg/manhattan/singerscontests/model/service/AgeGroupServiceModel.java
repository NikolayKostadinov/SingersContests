package bg.manhattan.singerscontests.model.service;

public class AgeGroupServiceModel {
    private Long id;
    private String name;
    private Integer minAge;
    private Integer maxAge;

    public Long getId() {
        return id;
    }

    public AgeGroupServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AgeGroupServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public AgeGroupServiceModel setMinAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public AgeGroupServiceModel setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
        return this;
    }
}
