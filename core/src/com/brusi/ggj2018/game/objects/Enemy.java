package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.graphic.Particle;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by pc on 1/26/2018.
 */

public class Enemy extends Unit {

    public static final float PREPARE_SHOT_TIME = 0.5f;

    enum State {
        IDLE,
        SHOOTING,
        JUMPING_IN
    }

    State state = State.JUMPING_IN;
    float stateTime = 0;

    public Platform targetPlatform = null;

    public Enemy(float x, float y) {
        super(x, y, 40, 76, new SpriteContainer[] {
                SpriteContainer.get(Assets.get().enemy),
                SpriteContainer.get(Assets.get().enemy_shoot)
        });
        accel.y = BASE_ACCEL;
    }

    @Override
    protected boolean collidePlatform(World world) {
        if (null == targetPlatform) return super.collidePlatform(world);
        if (targetPlatform.collide(this)) {
            velocity.y = 0;
            setPosition(position.x, targetPlatform.bounds.getY() + targetPlatform.bounds.getHeight() + bounds.getHeight() / 2);
            targetPlatform = null;
            return true;
        }
        return false;
    }

    private float nextShoot = Utils.randomRange(0.3f, 4f);

    @Override
    public void update(float deltaTime, World world) {
        stateTime += deltaTime;
        if (dead) {
            world.addBoneParticles(position.x, position.y);
            active = false;
            world.removeObject(this);
            return;
        }
        super.update(deltaTime, world);
        if (grounded && state == State.JUMPING_IN) {
            setState(State.IDLE);
        }
        if (state == State.IDLE && stateTime >= nextShoot) {
            setState(State.SHOOTING);
            animation.play(1, PREPARE_SHOT_TIME, 0, 1);
        }
        if (state == State.SHOOTING) {
            lookAtPlayer(world);
            if (stateTime > PREPARE_SHOT_TIME) {
                shoot(world);
                nextShoot = Utils.randomRange(0.5f, 4f);
                setState(State.IDLE);
            }
        }
    }

    private void lookAtPlayer(World world) {
        mirror = world.player.position.x > position.x;
    }

    private void setState(State state) {
        if (this.state == state) return;
        this.state = state;
        this.stateTime = 0;
    }

    private void shoot(World world) {
        Gdx.app.log("DEBUG", "shoot(world);");
        lookAtPlayer(world);
        Arrow arrow = new Arrow(position.x, position.y);
        arrow.velocity.x = Math.signum(world.player.position.x - position.x) * 300 + Utils.random2Range(30);
        arrow.mirror = mirror;
        float t = Math.abs(world.player.position.x - position.x) / 300;
        float v = t == 0 ? 0 : (world.player.position.y - position.y - t * t * (-10) / 2) / t;
        v = Math.min(v, 300);
        v = Math.max(v, -300);
        arrow.velocity.y = v + Utils.random2Range(15);
        arrow.shooter = this;
        world.addObject(arrow, 12);
    }

    /*@Override
    public void render(Batch batch) {
        sprite = computeSprite();
        super.render(batch);
    }

    private Sprite computeSprite() {
        if (state == State.IDLE || state == State.JUMPING_IN) {
            return Assets.get().enemy;
        }
        int index = Math.min(Assets.get().enemyShoot.size - 1, (int) Math.floor((stateTime / PREPARE_SHOT_TIME) * Assets.get().enemyShoot.size));
        return sprite = Assets.get().enemyShoot.get(index);
    }*/
}
