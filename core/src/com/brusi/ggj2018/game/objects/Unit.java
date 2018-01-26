package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.WorldRenderer;

/**
 * Created by Asaf on 26/01/2018.
 */

class Unit extends DynamicGameObject implements Renderable, Updatable {
    public static final int BASE_ACCEL = -2000;
    public static final int BASE_FRACTION = 4000;
    public boolean grounded = false;
    public boolean dead = false;
    private Sprite sprite;

    public boolean mirror = false;

    public Unit(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, width, height);
        this.sprite = sprite;
    }

    protected boolean collidePlatform(World world) {
        for (Platform platform : world.platforms) {
            if (platform.collide(this)) {
                velocity.y = 0;
                setPosition(position.x, platform.bounds.getY() + platform.bounds.getHeight() + bounds.getHeight() / 2);
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(float deltaTime, World world) {
        grounded = false;
        float damping = velocity.y * -0.5f;
        accel.y = getGAccel() + damping;
        setPosition(position.x+ velocity.x * deltaTime, position.y + velocity.y * deltaTime);
        velocity.y += accel.y * deltaTime;
        velocity.x += accel.x * deltaTime;
        if (collidePlatform(world) && velocity.y <= 0) {
            grounded = true;
        }

        if (grounded && Math.abs(velocity.x) > 0) {
            velocity.x -= Math.signum(velocity.x) * Math.min(Math.abs(velocity.x), deltaTime * BASE_FRACTION);
        }

        checkDeath();

        if (dead) {
            world.removeObject(this);
        }
    }

    private void checkDeath() {
        if (position.y < -WorldRenderer.FRUSTUM_HEIGHT / 2) {
            dead = true;
        }
    }

    protected float getGAccel() {
        return BASE_ACCEL;
    }

    @Override
    public void render(Batch batch) {
        sprite.setFlip(mirror, false);
        sprite.setAlpha(1);
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }
}
