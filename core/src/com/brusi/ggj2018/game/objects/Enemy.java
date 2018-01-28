package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.graphic.FadeOutEffect;
import com.brusi.ggj2018.game.graphic.Particle;
import com.brusi.ggj2018.utils.Animation;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by pc on 1/26/2018.
 */

public class Enemy extends Unit implements Animation.AnimationCallback {

    public static final float PREPARE_SHOT_TIME = 0.5f;
    public static final float DIE_TIME = 0.25f;

    @Override
    public void onStateDone() {
        if (state == State.DYING)
        {
            setState(State.AFTER_DYING);
        }
    }

    enum State {
        IDLE,
        SHOOTING,
        JUMPING_IN,
        DYING,
        AFTER_DYING
    }

    State state = State.JUMPING_IN;
    float stateTime = 0;

    public Platform targetPlatform = null;

    private final Vector2 next_arrow_velocity = new Vector2();

    public Enemy(float x, float y) {
        super(x, y, 40, 76, new SpriteContainer[] {
                SpriteContainer.get(Assets.get().enemy),
                SpriteContainer.get(Assets.get().enemy_shoot),
                SpriteContainer.get(Assets.get().enemy_die)
        });
        accel.y = BASE_ACCEL;
        SoundAssets.get().playRandomSound(SoundAssets.get().enemy_appear);
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
        super.update(deltaTime, world);
        if (state == State.AFTER_DYING) {
            world.addObject(new FadeOutEffect(this, Assets.get().enemy_die.get(Assets.get().enemy_die.size - 1), null, 0.8f), 4);
            world.removeObject(this);
            return;
        }
        if (dead) {
            if (state != State.DYING) {
                if (!world.isDead()) {
                    ++world.killcount;
                }
                active = false;
                setState(State.DYING);
                setPosition(position.x - (mirror ? 1 : -1) * 46, position.y);
                animation.play(2, DIE_TIME);
            }
            return;
        }
        if (grounded && state == State.JUMPING_IN) {
            setState(State.IDLE);
        }
        if (state == State.IDLE && stateTime >= nextShoot) {
            setState(State.SHOOTING);
            lookAtPlayer(world);
            next_arrow_velocity.set(getArrowVelocity(world.player.position.x,
                                                     world.player.position.y));
            animation.play(1, PREPARE_SHOT_TIME, 0, 1);
        }
        if (state == State.SHOOTING) {
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

    private Vector2 getArrowVelocity(float shootToX, float shootToY) {
        Vector2 arrow_vel = new Vector2();
        arrow_vel.x = Math.signum(shootToX - position.x) * 300 + Utils.random2Range(30);

        float t = Math.abs(shootToX - position.x) / 300;
        float v = t == 0 ? 0 : (shootToY - position.y - t * t * (-10) / 2) / t;
        v = Math.min(v, 300);
        v = Math.max(v, -300);
        arrow_vel.y = v + Utils.random2Range(15);

        return arrow_vel;
    }

    private void shoot(World world) {
        Gdx.app.log("DEBUG", "shoot(world);");
        //lookAtPlayer(world);
        Arrow arrow = new Arrow(position.x, position.y);
        arrow.mirror = mirror;
        arrow.shooter = this;
        arrow.velocity.set(next_arrow_velocity);
        world.addObject(arrow, 12);
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
        if (state == State.SHOOTING) {
            Sprite arrow = Assets.get().arrow;
            arrow.setFlip(mirror, false);
            arrow.setAlpha(1);
            arrow.setRotation((mirror ? 180 : 0) - (Utils.vec2deg(next_arrow_velocity.x, next_arrow_velocity.y) + 90));
            float arrow_x = position.x;

            Utils.drawCenter(batch, arrow, arrow_x, position.y);
        }
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

    @Override
    public void kill() {
        if (dead) return;
        dead = true;
        SoundAssets.get().playRandomSound(SoundAssets.get().enemy_death);
    }
}
