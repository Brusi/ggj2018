package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.brusi.ggj2018.assets.Assets;

/**
 * Created by pc on 1/26/2018.
 */

public class PlayerTarget extends StaticGraphicObject {
    public boolean on;

    public PlayerTarget() {
        super(Assets.get().player, 0, 0);
    }

    @Override
    public void render(Batch batch) {
        if (!on) return;
        sprite.setAlpha(0.5f);
        super.render(batch);
    }
}
