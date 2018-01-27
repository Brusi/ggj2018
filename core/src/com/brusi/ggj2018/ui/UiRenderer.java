package com.brusi.ggj2018.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.WorldRenderer;

import sun.java2d.pipe.ShapeSpanIterator;

/**
 * Created by pc on 1/26/2018.
 */

public class UiRenderer {
    public static final float TEXT_SCALE = 0.8f;
    private static float UI_Y = WorldRenderer.FRUSTUM_HEIGHT / 2 - 38f;
    private static float UI_KILLS_X = WorldRenderer.FRUSTUM_WIDTH / 2 - 82f;
    private static float UI_TIME_X = WorldRenderer.FRUSTUM_WIDTH / 2 - 212f;

    private Batch batch = new SpriteBatch();
    private ShapeRenderer shapesRenderer = new ShapeRenderer();
    public OrthographicCamera cam = new OrthographicCamera(WorldRenderer.FRUSTUM_WIDTH, WorldRenderer.FRUSTUM_HEIGHT);

    TextUi time = new TextUi(Assets.get().timeFont, UI_TIME_X - 50, UI_Y - 17, Align.left);
    TextUi kills = new TextUi(Assets.get().timeFont, UI_KILLS_X, UI_Y - 17, Align.center);

    public UiRenderer() {
        batch.setProjectionMatrix(cam.projection);
        shapesRenderer.setProjectionMatrix(cam.projection);

        time.setScale(TEXT_SCALE);
        kills.setScale(TEXT_SCALE);
    }

    public void render(World world) {
        batch.begin();
        if (world.isDead()) {
            Utils.drawCenter(batch, Assets.get().title_die, 0, 0);
        }

        Utils.drawCenter(batch, Assets.get().time_bg, UI_TIME_X, UI_Y);
        Utils.drawCenter(batch, Assets.get().kills_bg, UI_KILLS_X, UI_Y);

        time.setText(Utils.getTimeText(world.gameTime));
        time.render(batch);

        kills.setText(""+world.killcount);
        kills.render(batch);
        batch.end();

        world.energy.render(shapesRenderer, batch);
    }

    public void dispose() {
        batch.dispose();
    }
}
