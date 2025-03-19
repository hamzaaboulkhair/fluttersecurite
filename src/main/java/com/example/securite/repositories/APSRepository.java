package com.example.securite.repositories;

import com.example.securite.entities.APS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APSRepository extends JpaRepository<APS, Long> {

}