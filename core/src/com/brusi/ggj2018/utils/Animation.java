package com.brusi.ggj2018.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Asaf on 27/01/2018.
 */

public class Animation {

    public interface AnimationRenderer {
        void renderSprite(Batch batch, Sprite sprite);
    }

    public interface AnimationCallback {
        void onStateDone();
    }

    public Animation(SpriteContainer[] states, AnimationRenderer obj) {
        this.states = states;
        this.obj = obj;
    }

    protected SpriteContainer[] states;
    protected int currentState = 0;
    protected float currentStateTime = 1;
    protected boolean loop = true;
    protected int nextState = 0;
    protected float nextStateLength = 1;
    protected float currentTime = 0;
    protected AnimationRenderer obj;

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

    public void update(float deltaTime)
    {
        if (currentTime > currentStateTime) {
            if (obj instanceof AnimationCallback)
            {
                ((AnimationCallback) obj).onStateDone();
            }
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

    public Sprite getCurrent()
    {
        int index = Math.min(states[currentState].size() - 1, (int) Math.floor((currentTime / currentStateTime) * states[currentState].size()));
        return states[currentState].get(index);
    }

    public void render(Batch batch) {
        Sprite sprite = getCurrent();
        obj.renderSprite(batch, sprite);
    }
}
