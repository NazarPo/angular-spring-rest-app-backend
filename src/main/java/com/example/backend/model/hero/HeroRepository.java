package com.example.backend.model.hero;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {
    @Query("select hero from Hero hero where hero.guildId = null")
    List<Hero> findAllAvailableHeroes();
}
