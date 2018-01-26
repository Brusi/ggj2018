package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.graphic.Particle;
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
    public OrthographicCamera cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

    public WorldRenderer() {
        batch.setProjectionMatrix(cam.combined);
    }

    public void render(World world) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        Utils.drawCenter(batch, Assets.get().bg, 0, 0);
        for (Renderable object : world.objectsToRender) {
            object.render(batch);
        }
        for (Particle p : world.particles) {
            Gdx.app.log("DEBUG", "Rendering particle at " + p.position);
            p.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
