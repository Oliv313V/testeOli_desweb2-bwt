package com.example.bestweekend_trip.bwt_api.repository;

import com.example.bestweekend_trip.bwt_api.entity.Destiny;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinyRepository extends JpaRepository<Destiny, Long> {

    List<Destiny> findByNameContainingIgnoreCase(String name);
    List<Destiny> findByLocationContainingIgnoreCase(String location);
}
