package com.example.backend.model.hero;

import com.example.backend.model.pet.Pet;
import com.example.backend.model.tavern.Tavern;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table(name = "hero")
public class Hero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "hero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pet pet;

    @Column(name = "guild_id")
    private Long guildId;

    @ManyToMany(mappedBy = "heroes")
    @JsonIgnoreProperties("heroes")
    private Set<Tavern> taverns = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public Set<Tavern> getTaverns() {
        return taverns;
    }

    public void setTaverns(Set<Tavern> taverns) {
        this.taverns = isNull(taverns) ? new HashSet<>() : taverns;
        for (Tavern tavern: this.taverns) {
            tavern.getHeroes().add(this);
        }
    }

    public void addTavern(Tavern tavern) {
        this.taverns.add(tavern);
        tavern.getHeroes().add(this);
    }

    public void removeTavern(Tavern tavern) {
        this.taverns.remove(tavern);
        tavern.getHeroes().remove(this);
    }
}
