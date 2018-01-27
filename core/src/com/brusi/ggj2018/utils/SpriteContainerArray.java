package com.brusi.ggj2018.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Asaf on 27/01/2018.
 */

public class SpriteContainerArray extends SpriteContainer {

    private Array<Sprite> sprites;

    public SpriteContainerArray(Array<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public Sprite get(int index) {
        return sprites.get(index);
    }

    @Override
    public int size() {
        return sprites.size;
    }
}
