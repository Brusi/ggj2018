package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by Asaf on 26/01/2018.
 */

class Unit extends DynamicGameObject implements Renderable, Updatable {
    public static final int BASE_ACCEL = -2000;
    public boolean grounded = false;
    private Sprite sprite;

    public Unit(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, width, height);
        this.sprite = sprite;
    }

    private boolean collidePlatform(World world) {
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
        accel.y = BASE_ACCEL + damping;
        setPosition(position.x, position.y + velocity.y * deltaTime);
        velocity.y += accel.y * deltaTime;
        if (!grounded && collidePlatform(world)) {
            grounded = true;
        }
    }

    @Override
    public void render(Batch batch) {
        sprite.setAlpha(1);
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }
}
