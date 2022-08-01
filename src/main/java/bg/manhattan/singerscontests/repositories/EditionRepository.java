package bg.manhattan.singerscontests.repositories;

import bg.manhattan.singerscontests.model.entity.Edition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {
    Optional<Edition> findOneByContestIdAndNumber(Long contestId, Integer number);

    @Query("SELECT DISTINCT e.beginDate " +
            "FROM Edition e " +
            "WHERE e.beginDate BETWEEN :begin AND :end")
    List<LocalDate> findAllByBeginDateIsBetween(LocalDate begin, LocalDate end);

    Page<Edition> findAllByBeginDateAfter(LocalDate targetDate, PageRequest request);


    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.endOfSubscriptionDate = :date")
    List<Edition> findAllByEndOfSubscriptionDate(@Param("date") LocalDate targetDate);
}
