package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {
    Page<Contest> findAllByManagersContaining(User currentUser, PageRequest request);
}
