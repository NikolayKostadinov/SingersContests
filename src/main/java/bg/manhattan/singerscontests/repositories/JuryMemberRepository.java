package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.JuryMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuryMemberRepository extends JpaRepository<JuryMember, Long> {
}
