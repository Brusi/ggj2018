package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by pc on 1/26/2018.
 */

public class Player extends Unit {

    public static float IDLE_TIME = 1f;
    public static float DIE_TIME = 0.1f;
    public static int DIE_STATE = 1;
    public static int AFTER_DIE_STATE = 2;
    public static float AFTER_DIE_TIME = 1;

    public Player(float x, float y) {
        super(x, y, 20, 72, new SpriteContainer[]
                {
                        SpriteContainer.get(Assets.get().player_idle),
                        SpriteContainer.get(Assets.get().player_die),
                        SpriteContainer.get(Assets.get().player_die.get(Assets.get().player_die.size - 1)),
                        SpriteContainer.get(Assets.get().player_land),
                        SpriteContainer.get(Assets.get().player_land.first()),
                        SpriteContainer.get(Assets.get().player_teleport)
                });
        accel.y = BASE_ACCEL;
        animation.play(0, IDLE_TIME);
    }

    public void playDie() {
        animation.play(DIE_STATE, DIE_TIME, AFTER_DIE_STATE, AFTER_DIE_TIME);
    }

    @Override
    public void update(float deltaTime, World world) {
        boolean wasLanded = grounded;
        super.update(deltaTime, world);
        if (dead) return;
        if (!wasLanded && grounded) {
            animation.play(3, 0.2f, 0, IDLE_TIME);
        }
        if (wasLanded && !grounded) {
            animation.play(4, 0.2f);
        }
    }

    @Override
    public void kill() {
        if (dead == true) return;
        dead = true;
        SoundAssets.get().playRandomSound(SoundAssets.get().player_death);
    }

    @Override
    protected float getDamping() {
        return super.getDamping();
    }
}
