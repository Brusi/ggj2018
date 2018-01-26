package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.assets.Assets;

/**
 * Created by pc on 1/26/2018.
 */

public class Enemy extends Unit {

    public Enemy(float x, float y) {
        super(x, y, 40, 76, Assets.get().enemy);
        accel.y = BASE_ACCEL;
    }

}
