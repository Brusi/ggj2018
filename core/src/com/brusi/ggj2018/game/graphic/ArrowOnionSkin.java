package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class ArrowOnionSkin extends Particle {
    private final boolean mirror;
    float stateTime = 0;

    public ArrowOnionSkin(float x, float y, float rotation, boolean mirror) {
        super(Assets.get().arrow_trail, x, y, Vector2.Zero);
        this.mirror = mirror;
        this.rotation = rotation;
    }

    @Override
    public void update(float deltaTime, World world) {
        stateTime += deltaTime;
        super.update(deltaTime, world);

        if (stateTime > 1) {
            this.active = false;
        }
    }

    @Override
    public void renderSprite(Batch batch, Sprite sprite) {
        float alpha = 1 - Utils.clamp01(stateTime * 2);
        sprite.setAlpha(alpha);
        sprite.setFlip(mirror, false);
        super.renderSprite(batch, sprite);
    }
}
