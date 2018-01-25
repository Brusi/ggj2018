package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;

/**
 * Created by pc on 1/26/2018.
 */

public class Player extends DynamicGameObject {
    public Player(float x, float y) {
        super(x, y, 40, 80);
    }

    public void update(float deltaTime) {
        velocity.y += accel.y * deltaTime;
        setPosition(position.x, position.y + velocity.y * deltaTime);
    }

    public void render(Batch batch) {
        Utils.drawCenter(batch, Assets.get().player, position.x, position.y);
        Assets.get();
    }
}
