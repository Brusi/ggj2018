package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

/**
 * Created by Asaf on 26/01/2018.
 */

public class Energy extends GameObject implements Renderable, Updatable {

    public float energy = 0.5f;
    public static float ENERGY_LOW = 0.3f;

    public boolean empty;

    private static Color colors[] = new Color[] {Color.BLUE, Color.RED};

    public Energy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(float deltaTime, World world) {
        empty = energy <= 0;
        energy += Math.min(1 - energy, 0.5 * deltaTime);
    }

    @Override
    public void render(Batch batch) {
        Sprite bg = Assets.get().bar_bg;
        bg.setSize(bounds.width, bounds.height);
        bg.setPosition(position.x - bounds.width / 2, position.y - bounds.height / 2);
        bg.draw(batch);
        Sprite color = Assets.get().bar;
        color.setColor(colors[energy > ENERGY_LOW ? 0 : 1]);
        color.setSize(bounds.width - 12, energy * (bounds.height - 12));
        color.setPosition(position.x - (bounds.width - 12) / 2, position.y - (bounds.height - 12) / 2);
        color.draw(batch);
        //Utils.drawCenter(batch, Assets.get().bar_bg, position.x, position.y);
    }

    public void updateTouch(float deltaTime) {
        energy -= Math.min(2.5f * deltaTime, energy);
    }
}
