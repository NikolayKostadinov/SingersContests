package bg.manhattan.singerscontests.model.service;

public class AgeGroupServiceModel {
    private Long id;

    public Integer displayNumber;
    private String name;
    private Integer minAge;
    private Integer maxAge;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public AgeGroupServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getDisplayNumber() {
        return displayNumber;
    }

    public AgeGroupServiceModel setDisplayNumber(Integer displayNumber) {
        this.displayNumber = displayNumber;
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

    public boolean isDeleted() {
        return deleted;
    }

    public AgeGroupServiceModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
