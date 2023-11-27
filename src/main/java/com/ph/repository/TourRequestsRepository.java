package com.ph.repository;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.TourRequest;
import com.ph.domain.enums.Status;
import com.ph.domain.enums.StatusForAdvert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface TourRequestsRepository extends JpaRepository<TourRequest, Long> {

    Optional<TourRequest> findByIdAndGuestUser_Id(Long id, Long id1);

    boolean existsByTourDateAndTourTime(LocalDate tourDate, LocalTime tourTime);
    Page<TourRequest> findAllByGuestUser_Id(Long id, Pageable pageable);

    @Query("select t from TourRequest t where " +

            "(:start_date is null or t.tourDate >= :start_date) and " +
            "(:end_date is null or t.tourDate <= :end_date) and " +
            "(:status is null or t.status = :status)" )
    List<TourRequest> findForExcel(
            @Param("start_date") LocalDate startDate,
            @Param("end_date") LocalDate endDate,
            @Param("status") Status status
    );




}
