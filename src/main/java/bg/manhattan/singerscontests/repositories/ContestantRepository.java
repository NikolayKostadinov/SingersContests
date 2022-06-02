package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Contestant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {
}
