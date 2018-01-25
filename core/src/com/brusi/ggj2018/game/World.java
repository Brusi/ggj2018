package com.brusi.ggj2018.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.brusi.ggj2018.game.objects.Player;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    protected Player player = new Player(100, 100);

    void update(float deltaTime) {
        player.update(deltaTime);
    }
}
