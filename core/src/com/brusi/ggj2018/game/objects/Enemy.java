package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class Enemy extends Unit {

    public Platform targetPlatform = null;

    public Enemy(float x, float y) {
        super(x, y, 40, 76, Assets.get().enemy);
        accel.y = BASE_ACCEL;
    }

    @Override
    protected boolean collidePlatform(World world) {
        if (null == targetPlatform) return super.collidePlatform(world);
        if (targetPlatform.collide(this)) {
            velocity.y = 0;
            setPosition(position.x, targetPlatform.bounds.getY() + targetPlatform.bounds.getHeight() + bounds.getHeight() / 2);
            targetPlatform = null;
            return true;
        }
        return false;
    }

    private float nextShoot = Utils.randomRange(0.3f, 4f);

    @Override
    public void update(float deltaTime, World world) {
        if (dead) {
            active = false;
            world.removeObject(this);
            return;
        }
        super.update(deltaTime, world);
        if (grounded) {
            nextShoot -= deltaTime;
            if (nextShoot <= 0) {
                shoot(world);
                nextShoot = Utils.randomRange(0.5f, 4f);
            }
        }
    }

    private void shoot(World world) {
        mirror = world.player.position.x > position.x;
        Arrow arrow = new Arrow(position.x, position.y);
        arrow.velocity.x = Math.signum(world.player.position.x - position.x) * 300 + Utils.random2Range(30);
        arrow.mirror = mirror;
        float t = Math.abs(world.player.position.x - position.x) / 300;
        float v =  t == 0 ? 0 : (world.player.position.y - position.y - t * t * (-10)/2) / t;
        v = Math.min(v, 300);
        v = Math.max(v, -300);
        arrow.velocity.y = v + Utils.random2Range(15);
        arrow.shooter = this;
        world.addObject(arrow);
    }
}
