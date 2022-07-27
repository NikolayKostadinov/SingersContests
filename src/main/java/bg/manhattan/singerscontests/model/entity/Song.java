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

    @Column(length = 101, nullable = false)
    private String name;

    @Column(name="composer_full_name", length = 101, nullable = false)
    private String composerFullName;

    @Column(name="arranger_full_name", length = 101)
    private String arrangerFullName;

    @Column(name="lyricist_full_name", length = 101, nullable = false)
    private String lyricistFullName;

    @Column(nullable = false)
    private Duration duration;

    @Column(length = 4097, nullable = false)
    private String instrumentalUrl;
}
