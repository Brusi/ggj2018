package com.brusi.ggj2018.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

/**
 * Created by pc on 1/26/2018.
 */

public class GameScreen extends ScreenAdapter implements Screen {
    World world = new World();
    WorldRenderer worldRenderer = new WorldRenderer(world);

    @Override
    public void render(float deltaTime) {
        world.update(deltaTime);
        worldRenderer.render(world);
    }
}
