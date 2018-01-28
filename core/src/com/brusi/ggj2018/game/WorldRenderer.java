package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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

    final Vector2 camTarget = new Vector2();

    public WorldRenderer() {
        batch.setProjectionMatrix(cam.combined);
    }

    public void render(World world) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        updateCamera(world);

        batch.begin();
        Utils.drawCenter(batch, Assets.get().bg, cam.position.x * 0.8f, cam.position.y * 0.8f);
        for (int i = 0; i < world.objectsToRender.length; i++) {
            for (Renderable object : world.objectsToRender[i]) {
                object.render(batch);
            }
        }
        batch.end();
    }

    private void updateCamera(World world) {
        camTarget.set(world.controls.isTouched() ? world.playerTarget.position : world.player.position);
        camTarget.x = Utils.clamp(camTarget.x, -1000, 1000);
        camTarget.y = Utils.clamp(camTarget.y, -1000, 1000);
        cam.position.x = cam.position.x * 0.9f + camTarget.x / 5 * 0.1f;
        cam.position.y = cam.position.y * 0.9f + camTarget.y / 5 * 0.1f;
        cam.update();

        batch.setProjectionMatrix(cam.combined);
    }

    public void dispose() {
        batch.dispose();
    }
}
