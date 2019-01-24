package com.example.backend.model.tavern;

import com.example.backend.model.hero.Hero;

import javax.persistence.*;
import static java.util.Objects.isNull;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tavern")
public class Tavern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tavern_hero",
            joinColumns = @JoinColumn(name = "tavern_id"),
            inverseJoinColumns = @JoinColumn(name = "hero_id")
    )
    private Set<Hero> heroes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<Hero> heroes) {
        this.heroes = isNull(heroes) ? new HashSet<>() : heroes;
        for (Hero hero: this.heroes) {
            hero.getTaverns().add(this);
        }
    }

    public void addHero(Hero hero) {
        this.heroes.add(hero);
        hero.getTaverns().add(this);
    }

    public void removeHero(Hero hero) {
        this.heroes.remove(hero);
        hero.getTaverns().remove(this);
    }
}
