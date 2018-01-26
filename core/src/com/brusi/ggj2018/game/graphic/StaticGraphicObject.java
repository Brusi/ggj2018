package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.objects.Renderable;

/**
 * Created by pc on 1/26/2018.
 */

public class StaticGraphicObject implements Renderable {
    public final Vector2 position;
    protected final Sprite sprite;

    public StaticGraphicObject(Sprite sprite, float x, float y) {
        this.position = new Vector2(x,y);
        this.sprite = sprite;
    }

    @Override
    public void render(Batch batch) {
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }
}
