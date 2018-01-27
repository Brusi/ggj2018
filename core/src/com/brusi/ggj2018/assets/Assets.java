package com.brusi.ggj2018.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public Sprite arrow_trail;
    public Sprite platform;
    public Sprite platform_left;
    public Sprite platform_right;

    public Sprite bar;
    public Sprite bar_bg;
    public Array<Sprite> teleport_effect;

    public Array<Sprite> teleport_particle;
    public Array<Sprite> disappear_teleport_particle;
    public Array<Sprite> bones;

    public Array<Sprite> player_die;
    public Array<Sprite> player_idle;
    public Array<Sprite> player_land;
    public Array<Sprite> player_teleport;

    public Array<Sprite> enemy_shoot;
    public Array<Sprite> enemy_die;

    public Sprite mana;
    public Sprite mana_low;
    public Sprite mana_bg;

    public BitmapFont timeFont;

    public static Assets get() {
        return ((ggj2018)Gdx.app.getApplicationListener()).assets;
    }

    public void init() {
        bg = new Sprite(new Texture("objects/bg.jpg"));
        bg.setScale(1.12f);
        title = new Sprite(new Texture("objects/opening.jpg"));
        title_die = new Sprite(new Texture("objects/title_die.png"));

        TextureAtlas atlas = new TextureAtlas("objects/texture.txt");
        player = atlas.createSprite("hero");
        playerOutline = atlas.createSprite("hero_outline");
        enemy = atlas.createSprite("bad_guy");
        arrow = atlas.createSprite("arrow");
        arrow_trail = atlas.createSprite("arrow_trail");
        platform = atlas.createSprite("plat");
        platform_left = atlas.createSprite("plat_left_end");
        platform_right = atlas.createSprite("plat_right_end");
        bones = atlas.createSprites("bone");

        player_die = atlas.createSprites("hero_die");
        player_idle = atlas.createSprites("hero_idle");
        player_land = atlas.createSprites("hero_land");
        player_teleport = atlas.createSprites("hero_port");

        enemy_shoot = atlas.createSprites("bad_guy_draw");
        enemy_die = atlas.createSprites("bad_guy_death");

        bar = atlas.createSprite("bar");
        bar_bg = atlas.createSprite("bar_bg");

        teleport_effect = atlas.createSprites("port");

        mana = atlas.createSprite("mana");
        mana_low = atlas.createSprite("mana_empty");
        mana_bg = atlas.createSprite("mana_bg");

        TextureAtlas add_atlas = new TextureAtlas("objects/addeffects.txt");
        teleport_particle = add_atlas.createSprites("teleport_particle");
        disappear_teleport_particle = add_atlas.createSprites("disappear_particle");

        timeFont = new BitmapFont(Gdx.files.internal("fonts/volcano_newquest.fnt"), false);
    }

    public void dispose() {
        // TODO: Dispose sprites??
    }
}
