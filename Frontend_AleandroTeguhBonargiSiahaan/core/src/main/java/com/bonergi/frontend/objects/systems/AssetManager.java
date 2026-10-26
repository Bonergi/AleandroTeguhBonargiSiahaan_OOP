package com.bonergi.frontend.objects.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;
import java.util.Map

public class AssetManager {
    private static AssetManager instance;

    private Map<String, TextureRegion> regionCache;
    private Map<String, Animation<TextureRegion>> animationCache;
    private Map<String, Texture> textureCache;

    private AssetManager() {
        regionCache = new HashMap<>();
        animationCache = new HashMap<>();
        textureCache = new HashMap<>();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        if (textureCache.containsKey(filename)) {
            return textureCache.get(filename);
        }

        String filePath = filename;
        if (Gdx.files != null && !Gdx.files.internal(filePath).exists()) {
            filePath = "core/assets/" + filename;
        }

        if (Gdx.files != null && Gdx.files.internal(filePath).exists()) {
            Texture imageTex = new Texture(Gdx.files.internal(filePath));
            textureCache.put(filename, imageTex);
            return imageTex;
        }

        return null;
    }

    public void registerRegion(String key, TextureRegion region) {
        regionCache.put(key, region);
    }

    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture imageTex = loadTexture(filename);
        if (imageTex != null) {
            TextureRegion[][] spriteGrid = TextureRegion.split(imageTex, tileWidth, tileHeight);
            regionCache.put(key, spriteGrid[row][col]);
        }
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture imageTex = loadTexture(filename);
        if (imageTex != null) {
            TextureRegion[][] spriteGrid = TextureRegion.split(imageTex, tileWidth, tileHeight);
            TextureRegion[] frameArray = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                // FIXED: spriteGrid[row][startCol + i]
                frameArray[i] = spriteGrid[row][startCol + i];
            }
            Animation<TextureRegion> animation = new Animation<>(frameDuration, frameArray);
            animation.setPlayMode(playMode);
            animationCache.put(key, animation);

            if (frameArray.length > 0) {
                regionCache.put(key, frameArray[0]);
            }
        }
    }

    public TextureRegion getTextureRegion(String key) {
        return regionCache.get(key);
    }

    public TextureRegion getRegion(String key) {
        return getTextureRegion(key);
    }

    public Animation<TextureRegion> getAnimation(String key) {
        return animationCache.get(key);
    }

    public void init() {
        // Player Animations Setup
        registerAnimationFromSheet("player_idle", "player.png", 32, 48, 0, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_left", "player.png", 32, 48, 1, 0, 4, 0.12f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("player_right", "player.png", 32, 48, 2, 0, 4, 0.12f, Animation.PlayMode.LOOP);

        // Boss Animations Setup
        registerAnimationFromSheet("boss_idle", "rumia.png", 64, 64, 0, 0, 4, 0.2f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("boss_left", "rumia.png", 64, 64, 1, 0, 4, 0.15f, Animation.PlayMode.REVERSED);
        registerAnimationFromSheet("boss_right", "rumia.png", 64, 64, 2, 0, 4, 0.15f, Animation.PlayMode.NORMAL);

        // Fairy Animations Setup
        registerAnimationFromSheet("fairy_idle_red", "fairy.png", 32, 32, 0, 0, 8, 0.125f, Animation.PlayMode.LOOP);
        registerAnimationFromSheet("fairy_idle_blue", "fairy.png", 32, 32, 1, 0, 8, 0.125f, Animation.PlayMode.LOOP);

        // Bullet & Item Regions
        registerRegionFromSheet("bullet_amulet", "amulet_reimu.png", 16, 16, 0, 0);
        registerRegionFromSheet("bullet_amulet_homing", "amulet_reimu.png", 16, 16, 1, 0);
        registerRegionFromSheet("bullet_danmaku", "bullets_small.png", 16, 16, 2, 3);

        registerRegionFromSheet("item_power", "items.png", 16, 16, 0, 0);
        registerRegionFromSheet("item_point", "items.png", 16, 16, 0, 1);
        registerRegionFromSheet("item_bomb", "items.png", 16, 16, 0, 3);
        registerRegionFromSheet("item_life", "items.png", 16, 16, 0, 5);
    }

    public void dispose() {
        for (Texture texture : textureCache.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        textureCache.clear();
        regionCache.clear();
        animationCache.clear();
    }
}
