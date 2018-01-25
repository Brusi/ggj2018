package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;

import java.util.ArrayList;

/**
 * Created by pc on 1/26/2018.
 */

public class WorldRenderer {
    static final public float FRUSTUM_HEIGHT = 640;
    static final public float FRUSTUM_WIDTH = FRUSTUM_HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

    private Batch batch = new SpriteBatch();
    private OrthographicCamera cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

    private ArrayList<Renderable> objects = new ArrayList<Renderable>();

    public WorldRenderer(World world) {
        batch.setProjectionMatrix(cam.combined);
        objects.add(world.player);
    }

    public void render(World world) {
        batch.begin();
        for (Renderable object : objects) {
            object.render(batch);
        }
        batch.end();
    }
}
