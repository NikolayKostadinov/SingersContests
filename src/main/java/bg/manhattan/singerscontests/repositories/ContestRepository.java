package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest,Long> {
    List<Contest> findAllByManagersContaining(User currentUser);
}
