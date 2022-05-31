package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="performance_categories")
public class PerformanceCategory extends BaseEntity{

    @ManyToOne(optional = false)
    public Edition edition;

    public String name;

    public String getName() {
        return name;
    }

    public PerformanceCategory setName(String name) {
        this.name = name;
        return this;
    }
}
