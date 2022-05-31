package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name="songs")
public class Song extends BaseEntity {

    @ManyToOne(optional = false)
    private PerformanceCategory category;

    @ManyToOne(optional = false)
    private Contestant contestant;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private Duration duration;

    @Column(length = 4096, nullable = false)
    private String instrumentalUrl;
}
