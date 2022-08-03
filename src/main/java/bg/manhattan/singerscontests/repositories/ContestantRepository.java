package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Contestant;
import bg.manhattan.singerscontests.model.entity.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {
    List<Contestant> findAllByEdition(Edition edition);
}
