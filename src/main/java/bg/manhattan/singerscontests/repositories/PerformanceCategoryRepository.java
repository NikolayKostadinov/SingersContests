package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.PerformanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceCategoryRepository extends JpaRepository<PerformanceCategory, Long> {
}
