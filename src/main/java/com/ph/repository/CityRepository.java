package com.ph.repository;

import com.ph.domain.entities.City;
import com.ph.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


}
