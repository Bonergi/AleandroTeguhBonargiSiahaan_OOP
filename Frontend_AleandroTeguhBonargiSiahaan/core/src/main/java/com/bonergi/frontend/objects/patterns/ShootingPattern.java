package com.bonergi.frontend.objects.patterns;

import com.bonergi.frontend.objects.Systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
