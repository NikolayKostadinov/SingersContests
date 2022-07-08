package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
    Optional<Edition> findOneByContestIdAndNumber(Long contestId, Integer number);
}
