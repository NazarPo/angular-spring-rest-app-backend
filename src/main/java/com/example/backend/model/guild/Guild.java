package com.example.backend.model.guild;

import com.example.backend.model.hero.Hero;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "guild")
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "guild_id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @OneToMany()
    @JoinColumn(name = "guild_id")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<Hero> heroes) {
        this.heroes = heroes;
    }

    public void addHero(Hero hero) {
        this.heroes.add(hero);
    }

    public void removeHero(Hero hero) {
        this.heroes.remove(hero);
    }
}
