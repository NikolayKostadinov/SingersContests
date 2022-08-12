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

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.beginOfSubscriptionDate <= :date AND e.endOfSubscriptionDate >= :date")
    Page<Edition> findAllAvailableForSubscription(@Param("date") LocalDate targetDate, PageRequest request);

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.endOfSubscriptionDate = :date")
    List<Edition> findAllByEndOfSubscriptionDate(@Param("date") LocalDate targetDate);

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.endOfSubscriptionDate = :date")
    Page<Edition> findAllByEndOfSubscriptionDate(@Param("date") LocalDate targetDate, PageRequest request);

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.beginDate <= :date AND e.endDate >= :date AND " +
            "EXISTS ( SELECT ed " +
            "FROM Edition ed INNER JOIN ed.juryMembers j " +
            "WHERE ed.id = e.id AND j.id = :juryMemberId)")
    Page<Edition> findAllActiveForJuryMember(@Param("date") LocalDate targetDate,
                                             @Param("juryMemberId") Long juryMemberId, PageRequest request);

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.contest.id = :contestId AND e.beginOfSubscriptionDate > :date")
    Page<Edition> findAllByContestIdInFuture(@Param("contestId") Long contestId, @Param("date") LocalDate targetDate, PageRequest request);

    @Query("SELECT e " +
            "FROM Edition e " +
            "WHERE e.endDate <= :date")
    Page<Edition> findAllFinishedEditions(@Param("date")LocalDate date, PageRequest request);
}
