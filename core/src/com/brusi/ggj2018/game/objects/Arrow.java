package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class Arrow extends Unit {

    public Arrow(float x, float y) {
        super(x, y, 48, 6, Assets.get().arrow);
        accel.y = BASE_ACCEL;
    }

    @Override
    protected float getGAccel() {
        return -10;
    }

    @Override
    protected boolean collidePlatform(World world) {
        return false;
    }
}
