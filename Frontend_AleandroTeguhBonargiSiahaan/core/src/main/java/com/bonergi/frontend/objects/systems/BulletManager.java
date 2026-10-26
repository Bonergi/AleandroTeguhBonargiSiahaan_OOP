package com.bonergi.frontend.systems;

import com.bonergi.frontend.objects.BulletType;
import com.bonergi.frontend.objects.bullets.Bullet;
import com.bonergi.frontend.objects.systems.EntityFactory;

import java.util.*;

public class BulletManager {
    // 1. Antrean Pool untuk Daur Ulang Objek (Object Pool Pattern)
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    // 2. Daftar Peluru Aktif di Layar
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        // 1. Inisialisasi playerBulletPool dan enemyBulletPool sebagai LinkedList kosong
        // 2. Inisialisasi activePlayerBullets dan activeEnemyBullets sebagai ArrayList kosong

        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();

        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.PLAYER_AMULET, damage, true);
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, vx, vy, damage);
        }
        activePlayerBullets.add(bullet);
        return bullet;
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;
        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
            bullet.init(x, y, vx, vy, BulletType.ENEMY_BALL, damage, false);
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, vx, vy, damage);
        }
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        // 1. Daur ulang & pergerakan bullet pemain (5 poin)
        Iterator<Bullet> playerIter = activePlayerBullets.iterator();
        while (playerIter.hasNext()) {
            Bullet b = playerIter.next();
            b.update(delta);
            if (b.isDestroyed() || b.getY() > screenHeight || b.getY() < 0 || b.getX() > screenWidth) {
                b.destroy();
                playerIter.remove();
                playerBulletPool.offer(b);
            }
        }

        // 2. Daur ulang & pergerakan bullet musuh (5 poin)
        Iterator<Bullet> enemyIter = activePlayerBullets.iterator();
        while (enemyIter.hasNext()) {
            Bullet b = enemyIter.next();
            b.update(delta);
            if (b.isDestroyed() || b.getY() < 0 || b.getY() > screenHeight || b.getX() > screenWidth) {
                b.destroy();
                enemyIter.remove();
                enemyBulletPool.offer(b);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    // Metode pembantu akses pool dan daftar aktif
    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}
