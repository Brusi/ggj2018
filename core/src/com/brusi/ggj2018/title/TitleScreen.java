package com.brusi.ggj2018.title;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.game.GameScreen;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.WorldRenderer;

/**
 * Created by pc on 1/26/2018.
 */

public class TitleScreen extends ScreenAdapter implements Screen {

    private Batch batch;
    public OrthographicCamera cam;


    @Override
    public void show() {
        batch = new SpriteBatch();
        cam = new OrthographicCamera(WorldRenderer.FRUSTUM_WIDTH,
                WorldRenderer.FRUSTUM_HEIGHT);
        batch.setProjectionMatrix(cam.combined);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        Utils.drawCenter(batch, Assets.get().title, 0, 0);
        batch.end();

        if (Gdx.input.isTouched()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            SoundAssets.get().playGameMusics();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
