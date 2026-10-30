package com.bonergi.frontend.objects.Systems;

import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.Player;
import com.bonergi.frontend.objects.bullets.Bullet;
import com.bonergi.frontend.objects.enemies.Enemy;
import com.bonergi.frontend.objects.items.Item;
import com.bonergi.frontend.objects.items.ItemType;
import com.bonergi.frontend.objects.systems.EntityFactory;

import java.util.ArrayList;
import java.util.List;


public class CollisionReferee {
    public void resolveCollisions(Player player, List<GameObject> entities, com.bonergi.frontend.systems.BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // 1. Evaluasi bullet Pemain vs Musuh
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps (enemy.getCoreHitbox())) {
                        boolean defeated = enemy.takeDamage(bullet.getDamage());
                        if (defeated) {
                            player.addScore(enemy.getScoreValue());
                            Item powerItem = EntityFactory.createItem(enemy.getX(), enemy.getY(), ItemType.POWERUP);
                            newDrops.add(powerItem);
                        }
                        bullet.destroy();
                        break;
                        }
                    }
                }
            }
        }

        // 2. Evaluasi bullet Musuh vs Pemain (Core Hit vs Graze Hit)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            if (player.isAlive() && !player.isDestroyed()) {
                //Prioritas 1 Core Hit
                if (bullet.getCoreHitBox().overlaps(player.getCoreHitbox())) {
                    player.takeDamage(bullet.getDamage());
                    bullet.destroy();
                }
                //Prioritas 2 Graze Hit
                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    bullet.setGrazed(true);
                    player.addscore(50);
                    System.out.println("[Collission Referee] GRAZE ! " + player.getName() + "grazed an enemy bullet (+50 points)!");
                }
            }
        }

        // 3. Evaluasi Pemain vs Item
        for (GameObject entity : entities) {
            if (entity.isDestroyed()) continue;

        // KASUS A: Pengumpulan Item
        if (entity instanceof Item item) {
            if (player.getCoreHitbox().overlaps(item.getCoreHitbox()) || player.getGrazeHitbox().overlaps(item.getCoreHitbox())) {
                player.collectItem(item);
            }
        }
        // KASUS B: Kontak Fisik dengan Musuh
        else if (entity instanceof Enemy enemy) {
            if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                player.takeDamage(10);
            }
        }
    }

        entities.addAll(newDrops);
    }
}
