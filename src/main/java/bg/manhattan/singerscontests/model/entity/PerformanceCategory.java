package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="performance_categories")
public class PerformanceCategory extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    public Edition edition;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    private Boolean required;

    public String getName() {
        return name;
    }

    public PerformanceCategory setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public PerformanceCategory setRequired(Boolean required) {
        this.required = required;
        return this;
    }

    public Edition getEdition() {
        return edition;
    }

    public PerformanceCategory setEdition(Edition edition) {
        this.edition = edition;
        return this;
    }
}
