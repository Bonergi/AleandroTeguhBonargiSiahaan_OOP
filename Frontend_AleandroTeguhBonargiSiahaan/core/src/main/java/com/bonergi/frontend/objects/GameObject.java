package com.bonergi.frontend.objects;

import com.badlogic.gdx.graphics.Color;

public abstract class GameObject implements Collidable {
    protected float x, y;
    protected float width, height;
    protected float speed;
    protected Color color;
    protected boolean destroyed = false;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public abstract void update(float delta);

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
