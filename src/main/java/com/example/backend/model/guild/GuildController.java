package com.example.backend.model.guild;

import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.model.hero.Hero;
import com.example.backend.model.hero.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/api/guilds")
public class GuildController {

    @Autowired
    private GuildRepository guildRepository;

    @Autowired
    private HeroRepository heroRepository;

    @GetMapping("")
    public List<Guild> getAllGuilds() {
        return guildRepository.findAll();
    }

    @GetMapping("/top")
    public List<Guild> getTopGuilds() {
        return guildRepository.findAll(PageRequest.of(0, 4)).getContent();
    }

    @PostMapping("")
    public Guild createGuild(@Valid @RequestBody Guild guild) {
        return guildRepository.save(guild);
    }

    @GetMapping("{id}")
    public Guild getGuildById(@PathVariable(value = "id") Long guildId) {
        return guildRepository.findById(guildId)
                .orElseThrow(() -> new ResourceNotFoundException("Guild", "id", guildId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGuild(@PathVariable(value = "id") Long guildId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new ResourceNotFoundException("Guild", "id", guildId));
        guildRepository.delete(guild);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/heroes/{heroId}")
    public ResponseEntity<Hero> removeHero(@PathVariable(value = "id") Long guildId,
                                           @PathVariable(value = "heroId") Long heroId) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new ResourceNotFoundException("Guild", "id", guildId));

        Optional<Hero> hero =
                guild.getHeroes().stream().filter(h -> h.getId().equals(heroId)).findFirst();

        if (hero.isPresent()) {
            guild.getHeroes().remove(hero.get());
            guildRepository.save(guild);
        } else {
            throw new ResourceNotFoundException("Hero", "id", heroId);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/heroes")
    public ResponseEntity<Hero> addHero(@PathVariable(value = "id") Long guildId,
                                        @RequestBody List<Hero> heroesToAdd) {
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new ResourceNotFoundException("Guild", "id", guildId));

        heroesToAdd.forEach(hero -> {
            Hero existedHero = heroRepository.findById(hero.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hero", "id", hero.getId()));

            if (Optional.ofNullable(hero.getGuildId()).isPresent()){
                throw new ValidationException(format("Hero with id %d already exists in another guild", existedHero.getId()));
            }

            if (guild.getHeroes().stream().anyMatch(h -> h.getId().equals(existedHero.getId()))) {
                throw new EntityExistsException(format("Hero with id %d already exists in guild with id %d", existedHero.getId(), guild.getId()));
            } else {
                guild.addHero(hero);
            }
        });
        guildRepository.save(guild);
        return ResponseEntity.ok().build();
    }
}
