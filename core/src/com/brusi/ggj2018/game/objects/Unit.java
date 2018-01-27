package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.WorldRenderer;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by Asaf on 26/01/2018.
 */

class Unit extends DynamicGameObject implements Renderable, Updatable {



    public static final int BASE_ACCEL = -2000;
    public static final int BASE_FRACTION = 4000;
    public boolean grounded = false;
    public boolean dead = false;
    protected SpriteContainer[] states;
    protected int currentState = 0;
    protected float currentStateTime = 1;
    protected boolean loop = true;
    protected int nextState = 0;
    protected float nextStateLength = 1;
    protected float currentTime = 0;

    public boolean mirror = false;

    public Unit(float x, float y, float width, float height, SpriteContainer[] states) {
        super(x, y, width, height);
        this.states = states;
    }

    public Unit(float x, float y, float width, float height, Sprite sprite) {
        super(x, y, width, height);
        this.states = new SpriteContainer[] {SpriteContainer.get(sprite)};
    }

    public void play(int state, float  time) {
        loop = true;
        currentState = state;
        currentStateTime = time;
        currentTime = 0;
    }

    public void play(int state, float time, int nextState, float nextStateLength) {
        loop = false;
        currentState = state;
        currentStateTime = time;
        currentTime = 0;
        this.nextState = nextState;
        this.nextStateLength = nextStateLength;
    }

    protected void onStateDone() {}

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
        float damping = velocity.y * getDamping();
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

        if (currentTime > currentStateTime) {
            onStateDone();
            if (loop) {
                currentTime = 0;
            } else {
                currentTime = 0;
                currentState = nextState;
                currentStateTime = nextStateLength;
                loop = true;
            }
        } else {
            currentTime += deltaTime;
        }
    }

    protected float getDamping() {
        return -0.5f;
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
        int index = Math.min(states[currentState].size() - 1, (int) Math.floor((currentTime / currentStateTime) * states[currentState].size()));
        Sprite sprite = states[currentState].get(index);
        renderSprite(batch, sprite);
    }

    public void renderSprite(Batch batch, Sprite sprite) {
        sprite.setFlip(mirror, false);
        sprite.setAlpha(1);
        sprite.setRotation(getRotation());
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }

    float getRotation() {
        return 0;
    }
}
