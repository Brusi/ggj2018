package com.brusi.ggj2018.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Asaf on 27/01/2018.
 */

public abstract class SpriteContainer {
    public abstract Sprite get(int index);
    public abstract int size();

    public static SpriteContainer get(Sprite sprite) {
        return new SpriteContainerSingle(sprite);
    }

    public static SpriteContainer get(Array<Sprite> sprites) {
        return new SpriteContainerArray(sprites);
    }

}


