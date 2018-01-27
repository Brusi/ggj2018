package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.utils.Animation;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by Asaf on 27/01/2018.
 */

public class SinglePlayParticle extends StaticGraphicObject implements Animation.AnimationCallback {


    public SinglePlayParticle(SpriteContainer[] sprites, float x, float y, float time) {
        super(sprites, x, y);
        animation.play(0, time);
    }

    private boolean done = false;

    public SinglePlayParticle(Array<Sprite> anim, float x, float y, float time) {
        super(new SpriteContainer[]{SpriteContainer.get(anim)}, x, y);
        animation.play(0, time);
    }

    @Override
    public void onStateDone() {
        done = true;
    }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        if (done) {
            world.removeObject(this);
            return;
        }
    }
}
