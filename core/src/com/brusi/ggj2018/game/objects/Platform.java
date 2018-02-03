package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;

/**
 * Created by pc on 1/26/2018.
 */

public class Platform extends GameObject implements Renderable {
    public Platform(float x, float y, int width) {
        super(x, y, 80 * width, 18);
    }

    @Override
    public void render(Batch batch) {
        float x = bounds.getX() + 40;
        while (x < bounds.getX() + bounds.getWidth() - 10)
        {
            Sprite s = Assets.get().platform;
            if (x <= bounds.getX() + 40) {
                s = Assets.get().platform_left;
            } else if (x >= bounds.getX() + bounds.getWidth() - 90) {
                s = Assets.get().platform_right;
            }
            Utils.drawCenter(batch, s, x, position.y);
            x += 80;
        }
    }

    public boolean collide(GameObject object) {
        return ((object.position.x + object.bounds.width / 3 >= this.bounds.getX() &&  object.position.x + object.bounds.width / 3 <= this.bounds.getX() + this.bounds.getWidth()) ||
                (object.position.x - object.bounds.width / 3 >= this.bounds.getX() &&  object.position.x - object.bounds.width / 3 <= this.bounds.getX() + this.bounds.getWidth())) &&
                object.bounds.getY() <= this.bounds.getY() + this.bounds.getHeight() && object.bounds.getY() >= this.bounds.getY() + this.bounds.getHeight() - 20;
    }
}
