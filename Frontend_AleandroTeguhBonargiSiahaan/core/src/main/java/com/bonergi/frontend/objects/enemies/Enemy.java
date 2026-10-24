package com.bonergi.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.bonergi.frontend.objects.Collidable;
import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.Player;

public class Enemy extends GameObject {
    private String name;
    private int hp;
    private int maxHp;
    private long scoreValue;
    protected float collisionCooldown = 0.0f;

    public Enemy(String name, int hp) {
        this(0.0f, 0.0f, 32.0f, 32.0f, 100.0f, Color.WHITE, name, hp, 500L);
    }

    public Enemy(float x, float y, String name, int hp) {
        this(x, y, 32.0f, 32.0f, 100.0f, Color.WHITE, name, hp, 500L);
    }

    public Enemy(float x, float y, float width, float height, float speed, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, speed, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void attack(Player targetPlayer, int damage) {
        System.out.printf("%s attacks %s for %d DMG!%n", name, targetPlayer.getName(), damage);
        targetPlayer.takeDamage(damage);
    }

    public boolean takeDamage(int damage) {
        boolean currentlyAlive = isAlive();
        this.hp = Math.max(0, this.hp - damage);

        System.out.printf("%s took %d damage! HP: %d/%d%n", name, damage, hp, maxHp);

        if (currentlyAlive && hp == 0) {
            System.out.println(name + " was defeated!");
            destroy();
            return true;
        }
        return false;
    }

    public boolean canCollide() {
        return collisionCooldown <= 0.0f;
    }

    public void resetCollisionCooldown() {
        this.collisionCooldown = 1.0f;
    }

    @Override
    public void update(float delta) {
        if (collisionCooldown > 0.0f) {
            collisionCooldown = Math.max(0.0f, collisionCooldown - delta);
        }
    }

    @Override
    public void onCollision(Collidable other) {
        // Subclass override
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public long getScoreValue() { return scoreValue; }
}
