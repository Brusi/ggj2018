package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.game.World;

/**
 * Created by Asaf on 26/01/2018.
 */

public interface Updatable {
    void update(float deltaTime, World world);
}
