package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.objects.Updatable;

/**
 * Created by pc on 1/26/2018.
 */

public class Particle extends StaticGraphicObject {
    public static final int GRAVITY = -1000;
    protected final Vector2 vel;
    protected float rotation;

    public boolean active = true;

    public Particle(Sprite sprite, float x, float y, Vector2 vel) {
        super(sprite, x, y);
        this.vel = new Vector2(vel);
    }

    public void update(float deltaTime) {
        this.position.x += vel.x * deltaTime;
        this.position.y += vel.y * deltaTime;

        if (Utils.outOfBounds(this.position)) {
            this.active = false;
        }
    }

    @Override
    public void render(Batch batch) {
        sprite.setRotation(rotation);
        super.render(batch);
    }
}
