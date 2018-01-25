package com.brusi.ggj2018.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.brusi.ggj2018.ggj2018;

/**
 * Created by pc on 1/26/2018.
 */

public class Assets {

    public Sprite player;

    public static Assets get() {
        return ((ggj2018)Gdx.app.getApplicationListener()).assets;
    }

    public void init() {
        // TODO: Initialize sprites.
        player = new Sprite(new Texture("objects/player.png"));
    }

    public void dispose() {
        // TODO: Dispose sprites??
        player.getTexture().dispose();
    }
}
