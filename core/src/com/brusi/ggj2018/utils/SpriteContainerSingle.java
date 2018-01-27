package com.brusi.ggj2018.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Asaf on 27/01/2018.
 */

public class SpriteContainerSingle extends SpriteContainer {

    private Sprite sprite;

    public SpriteContainerSingle(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Sprite get(int index) {
        return sprite;
    }

    @Override
    public int size() {
        return 1;
    }
}
