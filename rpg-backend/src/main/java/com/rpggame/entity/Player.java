package com.rpggame.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String name;
    
    @Column(nullable = false)
    private Integer health = 100;
    
    @Column(nullable = false)
    private Integer maxHealth = 100;
    
    @Column(nullable = false)
    private Integer attackPower = 20;
    
    @Column(nullable = false)
    private Integer defensePower = 10;
    
    @Column(nullable = false)
    private Integer level = 1;
    
    @Column(nullable = false)
    private Integer experience = 0;
    
    @Column(nullable = false)
    private Integer totalBattles = 0;
    
    @Column(nullable = false)
    private Integer wins = 0;
    
    @Column(nullable = false)
    private Integer losses = 0;
    
    @Column(nullable = false)
    private Boolean isDefending = false;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // 비즈니스 로직 메소드들
    
    public int takeDamage(int damage) {
        if (this.isDefending) {
            damage = damage / 2;
            System.out.println("방어로 데미지가 반감되었습니다!");
        }
        
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
        
        this.isDefending = false;
        return damage;
    }
    
    public void heal(int amount) {
        this.health = Math.min(this.health + amount, this.maxHealth);
    }
    
    public void gainExperience(int exp) {
        this.experience += exp;
        
        while (this.experience >= experienceToNextLevel()) {
            levelUp();
        }
    }
    
    private int experienceToNextLevel() {
        return 100 + (this.level * 50);
    }
    
    private void levelUp() {
        this.level++;
        this.maxHealth += 10;
        this.health = this.maxHealth;
        this.attackPower += 5;
        this.defensePower += 3;
        System.out.println("레벨 업! 현재 레벨: " + this.level);
    }
    
    public boolean isAlive() {
        return this.health > 0;
    }
    
    public void defend() {
        this.isDefending = true;
    }
    
    public void resetDefense() {
        this.isDefending = false;
    }
}