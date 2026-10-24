package com.bonergi.frontend.objects.enemies;

import com.bonergi.frontend.objects.Collidable;
import com.bonergi.frontend.objects.Player;

public class Fairy extends Enemy {

    public Fairy(String name, int hp) {
        super(name, hp);
    }

    public Fairy(float x, float y) {
        this(x, y, "Fairy", 20);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, name, hp);
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player targetPlayer) {
            if (canCollide()) {
                System.out.println("Player collided with Fairy!");
                targetPlayer.takeDamage(10);
                resetCollisionCooldown();
            }
        }
    }
}
