package com.brusi.ggj2018.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.brusi.ggj2018.ggj2018;

/**
 * Created by pc on 1/26/2018.
 */

public class Assets {

    public Sprite bg;

    public Sprite player;
    public Sprite platform;

    public static Assets get() {
        return ((ggj2018)Gdx.app.getApplicationListener()).assets;
    }

    public void init() {
        bg = new Sprite(new Texture("objects/bg.jpg"));

        TextureAtlas atlas = new TextureAtlas("objects/texture.txt");
        player = atlas.createSprite("hero");
        platform = atlas.createSprite("plat");
    }

    public void dispose() {
        // TODO: Dispose sprites??
    }
}
