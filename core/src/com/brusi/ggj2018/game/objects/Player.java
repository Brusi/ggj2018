package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class Player extends DynamicGameObject implements Renderable, Updatable {

    public static final int BASE_ACCEL = -2000;

    public boolean grounded = false;

    public Player(float x, float y) {
        super(x, y, 40, 72);
        accel.y = BASE_ACCEL;
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
        Sprite sprite = Assets.get().player;
        sprite.setAlpha(1);
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }
}
