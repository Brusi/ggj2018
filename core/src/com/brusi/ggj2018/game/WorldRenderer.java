package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by pc on 1/26/2018.
 */

public class WorldRenderer {
    static final public float FRUSTUM_HEIGHT = 640;
    static final public float FRUSTUM_WIDTH = FRUSTUM_HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

    private Batch batch = new SpriteBatch();
    private OrthographicCamera cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

    public WorldRenderer() {
        batch.setProjectionMatrix(cam.combined);
    }

    public void render(World world) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        world.player.render(batch);
        batch.end();
    }
}
