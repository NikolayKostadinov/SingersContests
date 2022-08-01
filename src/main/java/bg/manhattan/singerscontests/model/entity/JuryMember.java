package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="jury_members")
public class JuryMember {
    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String details;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @ManyToMany(mappedBy = "juryMembers", fetch = FetchType.EAGER)
    private Set<Edition> editions;

    public JuryMember() {
        this.editions = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public JuryMember setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public JuryMember setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public JuryMember setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public JuryMember setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public JuryMember setEditions(Set<Edition> editions) {
        this.editions = editions;
        return this;
    }
}
