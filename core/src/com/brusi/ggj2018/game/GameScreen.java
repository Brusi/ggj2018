package com.brusi.ggj2018.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.brusi.ggj2018.utils.Controls;
import com.brusi.ggj2018.utils.TouchToPoint;

/**
 * Created by pc on 1/26/2018.
 */

public class GameScreen extends ScreenAdapter implements Screen {
    WorldRenderer worldRenderer = new WorldRenderer();
    private Controls controls;
    World world;

    @Override
    public void show() {
        super.show();
        controls = new Controls(TouchToPoint.get(), worldRenderer.cam);
        world = new World(controls);
    }

    @Override
    public void render(float deltaTime) {
        world.update(deltaTime);
        worldRenderer.render(world);
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
