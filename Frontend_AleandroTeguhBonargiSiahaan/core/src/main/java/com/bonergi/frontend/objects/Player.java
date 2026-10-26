package com.bonergi.frontend.objects;

import com.bonergi.frontend.Systems.AssetManager;
import com.bonergi.frontend.Systems.EntityFactory;
import com.bonergi.frontend.objects.bullets.BulletType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.bonergi.frontend.objects.bullets.Bullet;
import com.bonergi.frontend.objects.enemies.Enemy;
import com.bonergi.frontend.objects.items.Item;
import com.bonergi.frontend.objects.items.ItemType;
import com.bonergi.frontend.Systems.AudioManager;
import com.bonergi.frontend.systems.BulletManager;
import com.bonergi.frontend.pattern.ShootingPattern;

public class Player extends GameObject {
    public String name;
    public int hp;
    public int power;
    public int spellCards;

    private long score;
    private int currentDir = 0;
    private ShootingPattern shootingPattern;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 200, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 48, 200, Color.RED);

        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) {
            return;
        }

        float bulletX = x + width / 2f - 8f;
        float bulletY = y + height;
        int damage = 10 + power;

        System.out.println(
            getName() + " shoots bullet dealing " + damage + " DMG!"
        );

        if (shootingPattern != null) {
            shootingPattern.execute(
                bulletX,
                bulletY,
                bulletManager,
                true
            );
        } else {
            bulletManager.spawnPlayerBullet(
                bulletX,
                bulletY,
                0f,
                400f,
                damage
            );
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();

        System.out.println(
            getName() +
                " shoots " +
                target.getName() +
                " dealing " +
                damage +
                " DMG!"
        );

        boolean defeated = target.takeDamage(damage);

        if (defeated) {
            addScore(target.getScoreValue());
        }
    }
    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) return;

        ItemType type = item.getItemTypeEnum();

        if (type != null) {
            switch (type) {
                case POWER:
                    this.power += type.getPowerBonus();
                    addScore(item.getScoreValue());
                    System.out.println(
                        name +
                            " collected POWER item! Power increased to " +
                            power
                    );
                    break;

                case POINT:
                    addScore(item.getScoreValue());
                    System.out.println(
                        name + " collected POINT item!"
                    );
                    break;

                case BOMB:
                    this.spellCards += 1;
                    addScore(item.getScoreValue());
                    System.out.println(
                        name +
                            " collected BOMB item! SpellCards: " +
                            spellCards
                    );
                    break;

                case LIFE:
                    this.hp += 20;
                    addScore(item.getScoreValue());
                    System.out.println(
                        name +
                            " collected LIFE item! HP: " +
                            hp
                    );
                    break;
            }
        }

        item.destroy();
    }

    public void takeDamage(int damage) {
        setHp(getHp() - damage);

        System.out.println(
            getName() +
                " took " +
                damage +
                " damage! Remaining HP: " +
                getHp()
        );

        if (getHp() == 0) {
            System.out.println(
                getName() +
                    " was defeated (Pichuun~)!"
            );
        }
    }

    public void addScore(long points) {
        if (points > 0) {
            this.score += points;

            System.out.println(
                getName() +
                    " gained " +
                    points +
                    " pts! Total Score: " +
                    this.score
            );
        }
    }

    public boolean isAlive() {
        return getHp() > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
    }

    public long getScore() {
        return score;
    }

    @Override
    public void update(float delta) {
        // TODO 1: panggil update(delta) milik GameObject melalui super.
        super.update(delta);

        // TODO 2: Siapkan variabel lokal float dx dengan nilai awal 0
        // (dx = delta x, mencatat perubahan arah horizontal untuk animasi)
        float dx = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) ||
            Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) ||
            Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) ||
            Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;

            // TODO 3: Ganti nilai dx sesuai dengan arahnya.
            // (Kalau ke kiri, maka dx ke mana ya?)
            dx = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) ||
            Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;

            // TODO 4: Ganti nilai dx sesuai dengan arahnya.
            // (Kalau ke kanan, maka dx ke mana ya?)
            dx = 1;
        }

        // TODO 5: Panggil updateAnimationState(dx)
        updateAnimationState(dx);
    }

    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();

        if (dx < 0) {
            // TODO:
            // 1. Lanjutkan perubahan hanya jika currentDir bukan -1.
            // 2. Ubah currentDir menjadi -1.
            // 3. Ambil animasi "player_left" melalui assets.getAnimation(...).
            //    Simpan pada variabel lokal bertipe Animation<TextureRegion> bernama anim.
            // 4. Jika anim tidak null, pasang anim melalui setAnimation(...).
            if (currentDir != -1) {
                currentDir = -1;

                Animation<TextureRegion> anim =
                    assets.getAnimation("player_left");

                if (anim != null) {
                    setAnimation(anim);
                }
            }

        } else if (dx > 0) {
            // TODO:
            // 1. Lanjutkan perubahan hanya jika currentDir bukan 1.
            // 2. Ubah currentDir menjadi 1.
            // 3. Ambil animasi "player_right" melalui assets.getAnimation(...).
            //    Simpan pada variabel lokal bertipe Animation<TextureRegion> bernama anim.
            // 4. Jika anim tidak null, pasang anim melalui setAnimation(...).
            if (currentDir != 1) {
                currentDir = 1;

                Animation<TextureRegion> anim =
                    assets.getAnimation("player_right");

                if (anim != null) {
                    setAnimation(anim);
                }
            }

        } else {
            // TODO:
            // 1. Lanjutkan perubahan hanya jika currentDir bukan 0.
            // 2. Ubah currentDir menjadi 0.
            // 3. Ambil animasi "player_idle" melalui assets.getAnimation(...).
            //    Simpan pada variabel lokal bertipe Animation<TextureRegion> bernama anim.
            // 4. Jika anim tidak null, pasang anim melalui setAnimation(...).
            if (currentDir != 0) {
                currentDir = 0;

                Animation<TextureRegion> anim =
                    assets.getAnimation("player_idle");

                if (anim != null) {
                    setAnimation(anim);
                }
            }
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Item) {
            Item item = (Item) other;

            if (!item.isDestroyed()) {
                collectItem(item);
                AudioManager.getInstance().playItemPickup();
            }
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(
            x + (width / 2f) - 4f,
            y + (height / 2f) - 4f,
            8f,
            8f
        );
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
    }
}
