package com.ph.repository;

import com.ph.domain.entities.City;
import com.ph.domain.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictsRepository extends JpaRepository<District, Long> {


}
