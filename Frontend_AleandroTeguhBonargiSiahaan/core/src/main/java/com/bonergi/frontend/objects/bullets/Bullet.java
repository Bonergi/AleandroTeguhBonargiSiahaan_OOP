package com.bonergi.frontend.objects.bullets;

import com.badlogic.gdx.graphics.Color;
import com.bonergi.frontend.objects.BulletType;
import com.bonergi.frontend.objects.Collidable;
import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.enemies.Enemy;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        this(x, y, 400.0f, bulletType, damage);
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 8, 16, speed, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        this.y += speed * delta;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy targetEnemy) {
            System.out.printf("Bullet Hit %s For %d DMG!%n", targetEnemy.getName(), damage);
            targetEnemy.takeDamage(damage);
            destroy();
        }
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public int getDamage() {
        return damage;
    }
}
