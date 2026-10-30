package com.bonergi.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.Player;
import com.bonergi.frontend.objects.enemies.Enemy;
import com.bonergi.frontend.objects.systems.AssetManager;
import com.bonergi.frontend.objects.systems.AudioManager;
import com.bonergi.frontend.objects.systems.EntityFactory;
import com.bonergi.frontend.objects.systems.BulletManager;
import com.bonergi.frontend.objects.systems.CollisionReferee;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;

    // Manager & Referee sesuai modul
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private float enemyShootTimer = 0f;

    // Entitas game (Player, Boss/Enemy, dsb.)
    private Player player;
    private Enemy boss;
    private List<GameObject> entities;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // 1. Inisialisasi Asset & Audio Systems
        AssetManager.getInstance().init();
        AudioManager.getInstance().init();
        AudioManager.getInstance().playBgm();

        // 2. Inisialisasi Object Pool & Collision Referee
        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();

        entities = new ArrayList<>();

        // 3. Inisialisasi Player & Boss menggunakan EntityFactory & koordinat layar
        float screenWidth = 800; // atau Gdx.graphics.getWidth();
        float screenHeight = 600; // atau Gdx.graphics.getHeight();

        player = EntityFactory.createPlayer(screenWidth / 2f - 16, 50, "Reimu Hakurei", 100, 15, 3);
        boss = EntityFactory.createBoss(screenWidth / 2f - 32, screenHeight - 150, "Cirno (Stage 2 Boss)", 150);

        // 4. Masukkan ke dalam list entities agar di-update dan di-render otomatis
        entities.add(player);
        entities.add(boss);
    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;
        if (enemyShootTimer >= 1.5f) {
            if (boss != null && !boss.isDestroyed()) {
                boss.shootBullet(bulletManager);
            }
            enemyShootTimer = 0f;
        }
    }

    // Helper untuk update & membersihkan entitas standar
    private void updateAndClean(List<GameObject> entityList, float delta) {
        java.util.Iterator<GameObject> iterator = entityList.iterator();
        while (iterator.hasNext()) {
            GameObject entity = iterator.next();
            entity.update(delta);
            if (entity.isDestroyed()) {
                iterator.remove();
            }
        }
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 1. Input Pemain (Menembak dengan tombol Z)
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (player != null && !player.isDestroyed()) {
                player.shootBullet(bulletManager);
                AudioManager.getInstance().playShootSfx();
            }
        }

        // 2. Scheduler tembakan berkala Bos / Musuh
        updateEnemyScheduler(delta);

        // 3. Generic update & clean untuk entitas standar (Player, Enemy, Item)
        updateAndClean(entities, delta);

        // 4. Update dan daur ulang Object Pool
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // 5. Evaluasi & selesaikan seluruh tabrakan melalui Mediator Pattern
        collisionReferee.resolveCollisions(player, entities, bulletManager);

        // 6. Bersihkan layar
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        batch.begin();
        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        // 7. Render seluruh bullet aktif dari pool
        bulletManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        AssetManager.getInstance().dispose();
        AudioManager.getInstance().dispose();
    }
}
