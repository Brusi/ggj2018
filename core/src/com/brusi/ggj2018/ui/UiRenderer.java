package com.brusi.ggj2018.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.WorldRenderer;

/**
 * Created by pc on 1/26/2018.
 */

public class UiRenderer {
    private Batch batch = new SpriteBatch();
    public OrthographicCamera cam = new OrthographicCamera(WorldRenderer.FRUSTUM_WIDTH, WorldRenderer.FRUSTUM_HEIGHT);

    TextUi time = new TextUi(Assets.get().timeFont, -WorldRenderer.FRUSTUM_WIDTH / 2, WorldRenderer.FRUSTUM_HEIGHT / 2 - 30, Align.left);

    public UiRenderer() {
        batch.setProjectionMatrix(cam.projection);
    }

    public void render(World world) {
        batch.begin();
        if (world.isDead()) {
            Utils.drawCenter(batch, Assets.get().title_die, 0, 0);
        }

        time.setText(Utils.getTimeText(world.gameTime));
        time.render(batch);

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
