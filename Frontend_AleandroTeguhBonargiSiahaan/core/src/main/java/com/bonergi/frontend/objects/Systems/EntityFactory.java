package com.bonergi.frontend.objects.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bonergi.frontend.objects.Player;
import com.bonergi.frontend.objects.bullets.Bullet;
import com.bonergi.frontend.objects.BulletType;
import com.bonergi.frontend.objects.enemies.Boss;
import com.bonergi.frontend.objects.enemies.Fairy;
import com.bonergi.frontend.objects.items.Item;
import com.bonergi.frontend.objects.items.ItemType;

public class EntityFactory {

    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player playerInstance = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> playerAnim = AssetManager.getInstance().getAnimation("player_idle");
        playerInstance.setAnimation(playerAnim);
        return playerInstance;
    }

    public static Boss createBoss(float x, float y, String name, int hp) {
        Boss bossInstance = new Boss(x, y, name, hp);
        Animation<TextureRegion> bossAnim = AssetManager.getInstance().getAnimation("boss_idle");
        bossInstance.setAnimation(bossAnim);
        return bossInstance;
    }

    public static Fairy createFairy(float x, float y, String name, int hp) {
        Fairy fairyInstance = new Fairy(x, y, name, hp);
        // Menggunakan key "fairy_idle_red" (disesuaikan dengan AssetManager)
        Animation<TextureRegion> fairyAnim = AssetManager.getInstance().getAnimation("fairy_idle_red");
        fairyInstance.setAnimation(fairyAnim);
        return fairyInstance;
    }

    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        Fairy fairyInstance = new Fairy(x, y, name, hp);
        Animation<TextureRegion> customAnim = AssetManager.getInstance().getAnimation(keyString);
        fairyInstance.setAnimation(customAnim);
        return fairyInstance;
    }

    public static Item createItem(float x, float y, ItemType itemType) {
        Item itemInstance = new Item(x, y, itemType);
        String assetKey = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB -> "item_bomb";
            case LIFE -> "item_life";
        };
        TextureRegion itemSprite = AssetManager.getInstance().getTextureRegion(assetKey);
        itemInstance.setSprite(itemSprite);
        return itemInstance;
    }

    public static Bullet createEnemyBullet(float x, float y, int damage) {
        Bullet enemyBullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        TextureRegion bulletSprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
        enemyBullet.setSprite(bulletSprite);
        return enemyBullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage, String spriteKey) {
        TextureRegion customSprite = AssetManager.getInstance().getTextureRegion(spriteKey);
        Bullet playerBullet = new Bullet(x, y, BulletType.AMULET, damage);
        playerBullet.setSprite(customSprite);
        return playerBullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage) {
        return createPlayerBullet(x, y, damage, "bullet_amulet");
    }
}
