package com.ph.repository;

import com.ph.domain.entities.TourRequest;
import com.ph.domain.enums.Status;
import com.ph.payload.response.TourRequestCountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface TourRequestsRepository extends JpaRepository<TourRequest, Long> {
    @Query("""
            select (count(t) > 0) from TourRequest t
            where t.tourDate = ?1 and t.tourTime = ?2 and t.status = ?3 and t.guestUser.id = ?4""")
    boolean existsByTourDateAndTourTimeAndStatusAndGuestUser_Id(LocalDate tourDate, LocalTime tourTime, Status status, Long id);
    @Query("select (count(t) > 0) from TourRequest t where t.tourDate = ?1 and t.tourTime = ?2 and t.status = ?3")
    boolean existsByTourDateAndTourTimeAndStatus(LocalDate tourDate, LocalTime tourTime, Status status);

    List<TourRequest> findByTourDateEquals(LocalDate tourDate);

    boolean existsByTourDateAndTourTimeAndAdvert_Id(LocalDate tourDate, LocalTime tourTime, Long id);

    @Query("select count(t) from TourRequest t where t.advert.id = ?1")
    long countByAdvert_Id(Long id);


    List<TourRequest> findByGuestUser_Id(Long id);

    Page<TourRequest> findByAdvert_Id(Long id, Pageable pageable);

    boolean existsByOwnerUser_Id(Long id);

    boolean existsByGuestUser_Id(Long id);

    Optional<TourRequest> findByIdAndGuestUser_Id(Long id, Long id1);

    @Query("SELECT COUNT(tr) > 0 FROM TourRequest tr WHERE tr.tourDate = :tourDate AND tr.tourTime = :tourTime AND tr.status IN (:allowedStatuses)")
    boolean existsByTourDateAndTourTimeAndStatusIn(LocalDate tourDate, LocalTime tourTime, List<Status> allowedStatuses);

    Page<TourRequest> findAllByGuestUser_Id(Long id, Pageable pageable);

    @Query("select t from TourRequest t where " +
            "(t.tourDate between :start_date and :end_date) and " +
            "(:status is null or t.status = :status)")
    List<TourRequest> findForExcel(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate,
            @Param("status") Status status
    );

    // Not: getTour\requestCount
//    @Query("select count(t) from TourRequest t where t.advert.id = :advertId and t.owner.user.id = :id")
    Long countByAdvert_IdAndOwnerUser_Id(Long advertId, Long id);

    @Query("""
            select t from TourRequest t
            where t.advert.title ilike concat('%', ?1, '%')""")
    Page<TourRequest> search(String query, Pageable pageable);


    // Not: getTour\requestCount new
    @Query("SELECT NEW com.ph.payload.response.TourRequestCountResponse(tr.advert.id, COUNT(tr)) " +
            "FROM TourRequest tr " +
            "GROUP BY tr.advert.id")
    List<TourRequestCountResponse> getCountsTourRequests();


    @Query("SELECT NEW com.ph.payload.response.TourRequestCountResponse(tr.advert.id, COUNT(tr)) " +
            "FROM TourRequest tr " +
            "WHERE tr.advert.id IN :advertIds " +
            "GROUP BY tr.advert.id")
    List<TourRequestCountResponse> getCountsTourRequestsCustomer(@Param("advertIds") List<Long> advertIds);

    boolean existsByAdvert_IdAndTourTimeAndTourDate(Long advertId, LocalTime tourTime, LocalDate tourDate);

    List<TourRequest> findAllByTourDateAndTourTime(LocalDate requestDate, LocalTime requestTime);

    List<TourRequest> findAllByTourDateAndTourTimeAndStatus(LocalDate requestDate, LocalTime requestTime, Status approved);

    @Query("SELECT tr FROM TourRequest tr WHERE tr.tourDate < :today AND tr.status = :status")
    List<TourRequest> findPendingTourRequestsBeforeToday(LocalDate today, Status status);
}