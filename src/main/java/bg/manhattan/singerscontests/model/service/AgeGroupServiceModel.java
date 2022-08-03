package bg.manhattan.singerscontests.model.service;

public class AgeGroupServiceModel implements Comparable<AgeGroupServiceModel> {
    public Integer displayNumber;
    private Long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgeGroupServiceModel that)) return false;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public int compareTo(AgeGroupServiceModel o) {
        if (this.displayNumber == null || o.displayNumber == null) return 0;
        return this.displayNumber.compareTo(o.displayNumber);
    }
}
