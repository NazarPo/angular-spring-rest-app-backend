package com.example.backend.model.tavern;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TavernRepository extends JpaRepository<Tavern, Long> {
}
