package com.brusi.ggj2018.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Updatable;

import java.util.ArrayList;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    protected Player player = new Player(100, 100);

    protected ArrayList<Updatable> objects = new ArrayList<Updatable>();

    public World()
    {
        objects.add(player);
    }

    void update(float deltaTime)
    {
        for (Updatable object : objects) {
            object.update(deltaTime);
        }
    }
}
