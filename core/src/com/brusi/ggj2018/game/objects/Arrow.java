package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class Arrow extends Unit {

    public Enemy shooter;

    private boolean stuck = false;

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

    @Override
    public void update(float deltaTime, World world) {
        if (!stuck) {
            super.update(deltaTime, world);
            Vector2 p = new Vector2(position.x + (mirror ? 1 : -1) * bounds.width / 2, position.y);
            for (Updatable obj : world.objectsToUpdate) {
                if (obj instanceof Unit && !(obj instanceof Arrow) && obj != shooter) {
                    Unit u = (Unit) obj;
                    if (!u.active) continue;
                    float radius = Math.min(u.bounds.width / 2, u.bounds.height / 2) * 0.85f;
                    if (Math.abs(u.position.x - p.x) < 0.85 * (u.bounds.width / 2) && Math.abs(u.position.y - p.y) < 0.85 * (u.bounds.height / 2)) {
                        //if (u == world.player) continue;
                        boolean wasDead = u.dead;
                        u.dead = true;
                        if (u == world.player) {
                            stuck = true;
                            if (!wasDead) {
                                world.player.playDie();
                            }
                        } else {
                            world.removeObject(this);
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected float getDamping() {
        return 0;
    }

    @Override
    public float getRotation() {
        return (mirror ? 180 : 0) - (Utils.vec2deg(velocity.x, velocity.y) + 90);
    }
}
