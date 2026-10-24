package com.bonergi.frontend.objects;

import com.badlogic.gdx.graphics.Color;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    private long score;
    private int power;

    public Player(String name, int hp) {
        super(100, 100, 32, 32, 200f, Color.BLUE);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.score = 0;
        this.power = 1;
    }

    public void takeDamage(int damage) {
        this.hp = Math.max(0, this.hp - damage);
        System.out.println(name + " took " + damage + " damage! Current HP: " + hp);
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, this.hp + amount);
        System.out.println(name + " healed " + amount + " HP! Current HP: " + hp);
    }

    public void addScore(int points) {
        this.score += points;
        System.out.println("Score added: " + points + " | Total Score: " + score);
    }

    public void increasePower(int amount) {
        this.power += amount;
        System.out.println("Power increased by " + amount + " | Current Power: " + power);
    }

    @Override
    public void update(float delta) {}

    @Override
    public void onCollision(Collidable other) {}

    public String getName() { return name; }
    public int getHp() { return hp; }
    public long getScore() { return score; }
    public int getPower() { return power; }
}
