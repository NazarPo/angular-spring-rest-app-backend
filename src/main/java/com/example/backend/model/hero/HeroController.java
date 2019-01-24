package com.example.backend.model.hero;

import com.example.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {

    @Autowired
    private HeroRepository heroRepository;

    @GetMapping("")
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }

    @GetMapping("/top")
    public List<Hero> getTopHeroes() {
        return heroRepository.findAll(PageRequest.of(0, 4)).getContent();
    }

    @PostMapping("")
    public Hero createHero(@Valid @RequestBody Hero hero) {
        return heroRepository.save(hero);
    }

    @GetMapping("{id}")
    public Hero getHeroById(@PathVariable(value = "id") Long heroId) {
        return heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero", "id", heroId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHero(@PathVariable(value = "id") Long heroId) {
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero", "id", heroId));
        heroRepository.delete(hero);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    public List<Hero> getAvailableHeroes() {
        return heroRepository.findAllAvailableHeroes();
    }
}
