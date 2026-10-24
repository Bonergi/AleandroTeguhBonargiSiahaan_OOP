package com.bonergi.frontend.objects.items;

import com.badlogic.gdx.graphics.Color;
import com.bonergi.frontend.objects.Collidable;
import com.bonergi.frontend.objects.GameObject;
import com.bonergi.frontend.objects.Player;

public class Item extends GameObject {
    private ItemType itemType;
    private int value;

    public Item(float x, float y, ItemType itemType, int value) {
        super(x, y, 16, 16, 50.0f, Color.GREEN);
        this.itemType = itemType;
        this.value = value;
    }

    public Item(float x, float y, float speed, ItemType itemType, int value) {
        super(x, y, 16, 16, speed, Color.GREEN);
        this.itemType = itemType;
        this.value = value;
    }

    @Override
    public void update(float delta) {
        this.y -= speed * delta;
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Player targetPlayer) {
            System.out.printf("Player collected item %s with value %d%n", itemType, value);

            switch (itemType) {
                case HEAL -> targetPlayer.heal(value);
                case SCORE -> targetPlayer.addScore(value);
                case POWERUP -> targetPlayer.increasePower(value);
            }

            destroy();
        }
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getValue() {
        return value;
    }
}
