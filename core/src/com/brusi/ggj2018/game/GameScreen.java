package com.brusi.ggj2018.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.brusi.ggj2018.title.TitleScreen;
import com.brusi.ggj2018.utils.Controls;
import com.brusi.ggj2018.utils.EventQueue;
import com.brusi.ggj2018.utils.TouchToPoint;

/**
 * Created by pc on 1/26/2018.
 */

public class GameScreen extends ScreenAdapter implements Screen {
    WorldRenderer worldRenderer = new WorldRenderer();
    com.brusi.ggj2018.ui.UiRenderer uiRenderer = new com.brusi.ggj2018.ui.UiRenderer();
    private Controls controls;
    World world;

    final EventQueue events = new EventQueue();
    boolean canRestart;

    @Override
    public void show() {
        super.show();
        controls = new Controls(TouchToPoint.get(), worldRenderer.cam);
        world = new World(controls);
    }

    @Override
    public void render(float deltaTime) {
        events.update(deltaTime);
        world.update(deltaTime);
        worldRenderer.render(world);
        uiRenderer.render(world);

        updateCheats();
        updateDeath();
    }

    private void updateDeath() {
        if (world.isDead() && events.isEmpty() && !canRestart) {
            events.addEventFromNow(0.3f, new EventQueue.Event() {
                @Override
                public void invoke() {
                    canRestart = true;
                }
            });
        }
        if (canRestart && Gdx.input.isTouched()) {
            restartGame();
        }
    }

    private void updateCheats() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            restartGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new TitleScreen());
        }
    }

    private void restartGame() {
        ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
        uiRenderer.dispose();
    }
}
