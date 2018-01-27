package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;

/**
 * Created by pc on 1/26/2018.
 */

public class PlayerTarget extends StaticGraphicObject {
    public boolean on;
    public boolean mirror;

    public PlayerTarget() {
        super(Assets.get().playerOutline, 0, 0);
    }

    @Override
    public void renderSprite(Batch batch, Sprite sprite) {
        if (!on) return;
        sprite.setAlpha(0.5f);
        sprite.setFlip(mirror, false);
        super.renderSprite(batch, sprite);
    }
}
