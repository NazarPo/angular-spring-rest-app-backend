package com.example.backend.model.tavern;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.hero.Hero;
import com.example.backend.model.hero.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/taverns")
public class TavernController {

    @Autowired
    private TavernRepository tavernRepository;

    @Autowired
    private HeroRepository heroRepository;

    @GetMapping("")
    public List<Tavern> getAllTaverns() {
        return  tavernRepository.findAll();
    }

    @GetMapping("/top")
    public List<Tavern> getTopGuilds() {
        return tavernRepository.findAll(PageRequest.of(0, 4)).getContent();
    }

    @PostMapping("")
    public Tavern createTavern(@Valid @RequestBody Tavern tavern) {
        return tavernRepository.save(tavern);
    }

    @GetMapping("{id}")
    public Tavern getTavernById(@PathVariable(value = "id") Long tavernId) {
        return tavernRepository.findById(tavernId)
                .orElseThrow(() -> new ResourceNotFoundException("Tavern", "id", tavernId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTavern(@PathVariable(value = "id") Long tavernId) {
        Tavern tavern = tavernRepository.findById(tavernId)
                .orElseThrow(() -> new ResourceNotFoundException("Tavern", "id", tavernId));
        tavernRepository.delete(tavern);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/heroes/{heroId}")
    public ResponseEntity<?> removeHeroFromTavern(@PathVariable(value = "id") Long tavernId,
                                                  @PathVariable(value = "heroId") Long heroId) {
        Tavern tavern = tavernRepository.findById(tavernId)
                .orElseThrow(() -> new ResourceNotFoundException("Tavern", "id", tavernId));
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero", "id", heroId));

        tavern.removeHero(hero);
        tavernRepository.save(tavern);
        heroRepository.save(hero);
        return ResponseEntity.ok().build();
    }
}
