package com.brusi.ggj2018.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.brusi.ggj2018.ggj2018;

/**
 * Created by pc on 1/26/2018.
 */

public class Assets {

    public Sprite bg;
    public Sprite title;
    public Sprite title_die;

    public Sprite player;
    public Sprite playerOutline;
    public Sprite enemy;
    public Sprite arrow;
    public Sprite platform;
    public Sprite platform_left;
    public Sprite platform_right;

    public Array<Sprite> teleport_particle;

    public static Assets get() {
        return ((ggj2018)Gdx.app.getApplicationListener()).assets;
    }

    public void init() {
        bg = new Sprite(new Texture("objects/bg.jpg"));
        title = new Sprite(new Texture("objects/title.png"));
        title_die = new Sprite(new Texture("objects/title_die.png"));

        TextureAtlas atlas = new TextureAtlas("objects/texture.txt");
        player = atlas.createSprite("hero");
        playerOutline = atlas.createSprite("hero_outline");
        enemy = atlas.createSprite("bad_guy");
        arrow = atlas.createSprite("arrow");
        platform = atlas.createSprite("plat");
        platform_left = atlas.createSprite("plat_left_end");
        platform_right = atlas.createSprite("plat_right_end");

        TextureAtlas add_atlas = new TextureAtlas("objects/addeffects.txt");
        teleport_particle = add_atlas.createSprites("teleport_particle");
    }

    public void dispose() {
        // TODO: Dispose sprites??
    }
}
