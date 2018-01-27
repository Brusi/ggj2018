package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;
import com.brusi.ggj2018.utils.Animation;
import com.brusi.ggj2018.utils.SpriteContainer;

/**
 * Created by pc on 1/26/2018.
 */

public class StaticGraphicObject implements Renderable, Updatable, Animation.AnimationRenderer {
    public final Vector2 position;

    public Animation animation;

    public StaticGraphicObject(Sprite sprite, float x, float y) {
        this.position = new Vector2(x,y);
        this.animation = new Animation(new SpriteContainer[] {SpriteContainer.get(sprite)}, this);
    }

    @Override
    public void render(Batch batch) {
        animation.render(batch);
    }

    @Override
    public void renderSprite(Batch batch, Sprite sprite) {
        Utils.drawCenter(batch, sprite, position.x, position.y);
    }

    @Override
    public void update(float deltaTime, World world) {
        animation.update(deltaTime);
    }
}
