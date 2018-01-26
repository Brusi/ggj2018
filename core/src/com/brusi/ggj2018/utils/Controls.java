package com.brusi.ggj2018.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by pc on 1/26/2018.
 */

public class Controls {
    private final TouchToPoint ttp;
    private final Camera cam;

    private boolean touchedBefore;
    private final Vector2 basePos = new Vector2();
    private final Vector2 touchPos = new Vector2();
    private final Vector2 diffPos = new Vector2();
    private boolean released;

    public Controls(TouchToPoint ttp, Camera cam) {
        this.ttp = ttp;
        this.cam = cam;
    }

    public void update() {
        released = false;
        boolean touchedNow = Gdx.input.isTouched();
        if (!touchedNow) {
            if (touchedBefore) {
                released = true;
            }
            touchedBefore = false;
            return;
        }
        touchPos.set(ttp.toPoint(Gdx.input.getX(), Gdx.input.getY()));
        if (!touchedBefore) {
            basePos.set(touchPos);
        }
        touchedBefore = true;
    }

    public Vector2 getBasePos() {
        if (touchedBefore || released) return basePos; else return null;
    }

    public Vector2 getTouchPos() {
        if (touchedBefore || released) return touchPos; else return null;
    }

    public Vector2 getDiff() {
        diffPos.set(basePos.x - touchPos.x, basePos.y - touchPos.y);
        return diffPos;
    }

    public boolean getReleased() {
        return released;
    }

    public boolean isTouched() {
        return touchedBefore;
    }
}
