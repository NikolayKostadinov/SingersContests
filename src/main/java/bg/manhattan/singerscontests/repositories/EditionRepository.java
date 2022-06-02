package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Edition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Repository> {
}
