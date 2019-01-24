package com.example.backend.model.pet;

import com.example.backend.model.hero.Hero;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="pet")
public class Pet {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank
  @Column(name = "name")
  private String name;

  @NotBlank
  @Column(name = "health_points")
  private Long hp;

  @NotBlank
  @Column(name = "damage")
  private Long damage;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hero_id")
  @JsonIgnore
  private Hero hero;

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }

  public Long getDamage() { return damage; }

  public void setDamage(Long damage) { this.damage = damage; }

  public Long getHp() { return hp; }

  public void setHp(Long hp) { this.hp = hp; }

  public Long getId() { return id; }

  public void setId(Long petId) { this.id = petId; }

  public Hero getHero() { return hero; }

  public void setHero(Hero hero) { this.hero = hero; }
}
