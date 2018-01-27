package com.brusi.ggj2018.game.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.objects.GameObject;
import com.brusi.ggj2018.game.objects.Unit;

import java.awt.Color;

/**
 * Created by Asaf on 27/01/2018.
 */

public class FadeOutEffect extends StaticGraphicObject {
    private com.badlogic.gdx.graphics.Color color;
    private float totalTime;
    private float currentTime = 0;
    private boolean mirror;

    public FadeOutEffect(Unit unit, com.badlogic.gdx.graphics.Color color, float time) {
        super(unit.animation.getCurrent(), unit.position.x, unit.position.y);
        this.color = color;
        this.totalTime = time;
        this.mirror = unit.mirror;
    }

    public FadeOutEffect(Unit unit, Sprite sprite, com.badlogic.gdx.graphics.Color color, float time) {
        super(sprite, unit.position.x, unit.position.y);
        this.color = color;
        this.totalTime = time;
        this.mirror = unit.mirror;
    }

    @Override
    public void update(float deltaTime, World world) {
        super.update(deltaTime, world);
        currentTime += deltaTime;
        if (currentTime >= totalTime) {
            world.removeObject(this);
        }
    }

    @Override
    public void renderSprite(Batch batch, Sprite sprite) {
        com.badlogic.gdx.graphics.Color before = sprite.getColor();
        if (null != color) {
            sprite.setColor(color);
        }
        float alpha = 1 - Utils.clamp01(currentTime / totalTime);
        sprite.setAlpha(alpha);
        sprite.setFlip(mirror, false);
        super.renderSprite(batch, sprite);
        if (null != before) {
            sprite.setColor(before);
        }
    }
}
