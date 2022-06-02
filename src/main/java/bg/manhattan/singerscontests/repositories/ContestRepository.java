package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {
}
