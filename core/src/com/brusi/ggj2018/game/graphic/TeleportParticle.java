package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.utils.BatchUtils;

/**
 * Created by pc on 1/26/2018.
 */

public class TeleportParticle extends Particle {

    public static final int INIT_VEL = 500;

    private float totalTime;

    private final float rotationSpeed = Utils.random2Range(30);

    float stateTime = 0;

    public TeleportParticle(float x, float y, float time, Array<Sprite> sprites) {
        super(sprites.random(), x, y, getVel());
        totalTime = time;
    }

    private static Vector2 getVel() {
        Vector2 vel = Utils.randomDir();
        vel.x *= INIT_VEL * Utils.randomRange(0.6f, 1);
        vel.y *= INIT_VEL * Utils.randomRange(0.6f, 1);
        return vel;
    }

    @Override
    public void renderSprite(Batch batch, Sprite sprite) {
        BatchUtils.setBlendFuncAdd(batch);
        float tint = Utils.clamp01(totalTime - stateTime);
        sprite.setColor(tint, tint, tint, 1);
        super.renderSprite(batch, sprite);
        BatchUtils.setBlendFuncNormal(batch);
    }

    @Override
    public void update(float deltaTime, World world) {
        stateTime += deltaTime;
        vel.y += GRAVITY * deltaTime;
        rotation += rotationSpeed * deltaTime;
        super.update(deltaTime, world);
    }
}
