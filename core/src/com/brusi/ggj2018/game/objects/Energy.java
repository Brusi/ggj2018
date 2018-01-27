package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;
import com.brusi.ggj2018.game.WorldRenderer;
import com.brusi.ggj2018.utils.BatchUtils;

/**
 * Created by Asaf on 26/01/2018.
 */

public class Energy extends GameObject implements Updatable {

    public static final float X_POSITION = -WorldRenderer.FRUSTUM_WIDTH / 2 + 50;
    public static final float Y_POSITION = 0;
    public float energy = 0.5f;
    public static float ENERGY_LOW = 0.3f;

    public boolean empty;

    private static Color colors[] = new Color[] {Color.BLUE, Color.RED};

    public Energy(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void update(float deltaTime, World world) {
        empty = energy <= 0;
        energy += Math.min(1 - energy, 0.5 * deltaTime);
    }

//    public void render(Batch batch) {
//        Sprite bg = Assets.get().bar_bg;
//        bg.setSize(bounds.width, bounds.height);
//        bg.setPosition(position.x - bounds.width / 2, position.y - bounds.height / 2);
//        bg.draw(batch);
//        Sprite color = Assets.get().bar;
//        color.setColor(colors[energy > ENERGY_LOW ? 0 : 1]);
//        color.setSize(bounds.width - 12, energy * (bounds.height - 12));
//        color.setPosition(position.x - (bounds.width - 12) / 2, position.y - (bounds.height - 12) / 2);
//        color.draw(batch);
//        //Utils.drawCenter(batch, Assets.get().bar_bg, position.x, position.y);
//        render(shapeRenderer, batch);
//    }

//    @Override
    public void render(ShapeRenderer renderer, Batch batch) {
        BatchUtils.setBlendFuncNormal(batch);
        batch.begin();
        Utils.drawCenter(batch, Assets.get().mana_bg, X_POSITION, Y_POSITION);
        batch.end();

        drawColor(renderer, batch);
    }

    private void drawColor(ShapeRenderer renderer, Batch batch)
    {
        //2. clear our depth buffer with 1.0
        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        //3. set the function to LESS
        Gdx.gl.glDepthFunc(GL20.GL_LESS);

        //4. enable depth writing
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        //5. Enable depth writing, disable RGBA color writing
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);

        ///////////// Draw mask shape(s)
        renderMask(renderer);

        ///////////// Draw sprite(s) to be masked
        BatchUtils.setBlendFuncNormal(batch);
        batch.begin();

        //8. Enable RGBA color writing
        //   (SpriteBatch.begin() will disable depth mask)
        Gdx.gl.glColorMask(true, true, true, true);

        //9. Make sure testing is enabled.
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        //10. Now depth discards pixels outside our masked shapes
        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);

        //push to the batch
         Utils.drawCenter(batch, Assets.get().mana, X_POSITION, 0);
        //end/flush your batch
        batch.end();

        Gdx.gl.glDepthFunc(GL20.GL_ALWAYS);


        BatchUtils.setBlendFuncNormal(batch);
    }

    private void renderMask(ShapeRenderer shapes) {
        // 6. render your primitive shapes
        shapes.begin(ShapeRenderer.ShapeType.Filled);

        shapes.setColor(1f, 0f, 0f, 0.5f);
        // shapes.circle(200, 200, 100);
        shapes.setColor(0f, 1f, 0f, 0.5f);
        // shapes.rect(200, 200, 100, 100);

        shapes.rect(X_POSITION - 50, Y_POSITION - Assets.get().mana.getHeight() / 2, 100, Assets.get().mana.getHeight() * energy);
        shapes.end();

    }

    public void updateTouch(float deltaTime) {
        energy -= Math.min(2.5f * deltaTime, energy);
    }
}
