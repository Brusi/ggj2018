package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by pc on 1/26/2018.
 */

public class Arrow extends Unit {

    public Enemy shooter;

    // When set, arrow is "stuck" on player in this offset.
    private Vector2 playerOffset;
    private final Vector2 hotSpot = new Vector2();

    public Arrow(float x, float y) {
        super(x, y, 48, 6, Assets.get().arrow);
        accel.y = BASE_ACCEL;
        SoundAssets.get().playRandomSound(SoundAssets.get().enemy_shoot);
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
        if (playerOffset != null) {
            position.set(world.player.position).add(playerOffset);
            return;
        }
        super.update(deltaTime, world);
        checkHit(world);
    }

    private void checkHit(World world) {
        updateHotSpot();
        for (Updatable obj : world.objectsToUpdate) {
            if (obj instanceof Unit && !(obj instanceof Arrow) && obj != shooter) {
                Unit u = (Unit) obj;
                if (!u.active) continue;
                float radius = Math.min(u.bounds.width / 2, u.bounds.height / 2) * 0.85f;
                if (Math.abs(u.position.x - hotSpot.x) < 0.85 * (u.bounds.width / 2) && Math.abs(u.position.y - hotSpot.y) < 0.85 * (u.bounds.height / 2)) {
                    //if (u == world.player) continue;
                    if (u == world.player) {
                        killPlayer(world);
                        this.playerOffset = position.cpy().sub(u.position);
                    } else if (u.grounded) {
                        u.kill();
                        u.mirror = !this.mirror;
                        if (u.timeAlive > 1 && u.playerHidesInside(world.player)) {
                            killPlayer(world);
                        }
                        world.removeObject(this);
                    }
                    return;
                }
            }
        }
    }

    private void killPlayer(World world) {
        boolean wasDead = world.player.dead;
        world.player.kill();
        if (!wasDead) {
            world.player.playDie();
        }
    }

    private void updateHotSpot() {
        float half_length = bounds.width / 2;
        float rotation_rad = Utils.degToRad(getRotation());
        hotSpot.set(position.x + (mirror ? 1 : -1) * half_length * (float) Math.cos(rotation_rad),
                    position.y + (mirror ? 1 : -1) * half_length * (float)Math.sin(rotation_rad));
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
