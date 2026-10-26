package com.bonergi.frontend.objects.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bonergi.frontend.systems.AudioManager;

public class AudioManager {
    private static AudioManager instance;

    private Music backgroundMusic;
    private Sound shootSound;
    private Sound hitSound;
    private Sound itemCollectSound;

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void init() {
        // Background Music Setup
        String musicPath = Gdx.files.internal("bell_avici.mp3").exists() ? "bell_avici.mp3" : "core/assets/bell_avici.mp3";
        if (Gdx.files.internal(musicPath).exists()) {
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.4f);
        }

        // Shoot SFX Setup
        String shootFile = Gdx.files.internal("freesound.mp3").exists() ? "freesound.mp3" : "core/assets/freesound.mp3";
        if (Gdx.files.internal(shootFile).exists()) {
            shootSound = Gdx.audio.newSound(Gdx.files.internal(shootFile));
        }

        // Hit SFX Setup
        String hitFile = Gdx.files.internal("universfie.mp3").exists() ? "universfie.mp3" : "core/assets/universfie.mp3";
        if (Gdx.files.internal(hitFile).exists()) {
            hitSound = Gdx.audio.newSound(Gdx.files.internal(hitFile));
        }

        // Item SFX Setup
        String itemFile = Gdx.files.internal("edr_power.mp3").exists() ? "edr_power.mp3" : "core/assets/edr_power.mp3";
        if (Gdx.files.internal(itemFile).exists()) {
            itemCollectSound = Gdx.audio.newSound(Gdx.files.internal(itemFile));
        }
    }

    public void playBgm() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void playShootSfx() {
        if (shootSound != null) shootSound.play(0.6f);
    }

    public void playHitSfx() {
        if (hitSound != null) hitSound.play(0.8f);
    }

    public void playItemSfx() {
        if (itemCollectSound != null) itemCollectSound.play(0.7f);
    }

    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
        if (shootSound != null) shootSound.dispose();
        if (hitSound != null) hitSound.dispose();
        if (itemCollectSound != null) itemCollectSound.dispose();
    }
}
