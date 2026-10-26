package com.bonergi.frontend.objects.bullets;

import com.bonergi.frontend.objects.BulletType;
import com.bonergi.frontend.objects.Collidable;
import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.enemies.Enemy;
import com.badlogic.gdx.graphics.Color;
import com.bonergi.frontend.Systems.AudioManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;

    private float vx;
    private float vy;
    private float angle = 90f;
    private boolean isPlayerBullet;
    private boolean grazed;
    private boolean active;

    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);

        this.bulletType = bulletType;
        this.damage = damage;

        this.vx = 0f;
        this.vy = 400f;
        this.angle = 90f;

        this.isPlayerBullet = true;
        this.grazed = false;

        AudioManager.getInstance().playShoot();
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 16, 16, speed, Color.YELLOW);

        this.bulletType = bulletType;
        this.damage = damage;

        this.vx = 0f;
        this.vy = speed;
        this.angle = (speed < 0) ? 270f : 90f;

        this.isPlayerBullet = true;
        this.grazed = false;

        AudioManager.getInstance().playShoot();
    }

    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        // 1. Perbarui posisi dan komponen kecepatan
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        // 2. Hitung sudut rotasi dalam derajat
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        // 3. Hitung magnitudo kecepatan
        this.speed = (float) Math.hypot(vx, vy);
        // 4. Perbarui identitas peluru
        this.bulletType = bulletType;
        this.damage = damage;
        this.isPlayerBullet = isPlayerBullet;
        // 5. Aktifkan kembali peluru dan reset waktu animasi
        this.active = true;
        this.stateTime = 0f;
        // 6. Reset status graze
        this.grazed = false;
    }

    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }
    // Getter untuk vx
    public float getVx() {
        return vx;
    }
    // Getter untuk vy
    public float getVy() {
        return vy;
    }
    // Getter untuk angle
    public float getAngle() {
        return angle;
    }
    // Setter untuk angle
    public void setAngle(float angle) {
        this.angle = angle;
    }
    // Getter untuk status grazed
    public boolean hasBeenGrazed() {
        return grazed;
    }
    // Setter untuk status grazed
    public void setGrazed(boolean grazed) {
        this.grazed = grazed;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        this.x += vx * delta;
        this.y += vy * delta;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Enemy enemy) {
            System.out.println(
                "Bullet hit " +
                    enemy.getName() +
                    " for " +
                    damage +
                    " DMG!"
            );

            enemy.takeDamage(damage);
            destroy();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                batch.draw(
                    sprite,
                    x,
                    y,
                    width / 2f,
                    height / 2f,
                    width,
                    height,
                    1f,
                    1f,
                    angle - 90f
                );
            } else {
                super.render(batch);
            }
        }
    }

    public BulletType getBulletType() {
        return bulletType;
    }
    public int getDamage() {
        return damage;
    }
}
