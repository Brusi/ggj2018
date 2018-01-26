package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.graphic.PlayerTarget;
import com.brusi.ggj2018.game.objects.Platform;
import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;
import com.brusi.ggj2018.utils.Controls;

import java.util.ArrayList;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    protected Player player = new Player(100, 100);
    final public PlayerTarget playerTarget = new PlayerTarget();

    protected ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();
    public ArrayList<Platform> platforms = new ArrayList<Platform>();

    Controls controls;

    public World(Controls controls)
    {
        this.controls = controls;
        addObject(player);
        objectsToRender.add(playerTarget);
        createPlatforms();
    }

    private void createPlatforms()
    {
        Platform p = new Platform(100, -100, 3);
        addObject(p);
        platforms.add(p);
    }

    public void addObject(Object object)
    {
        if (object instanceof Updatable)
        {
            objectsToUpdate.add((Updatable)object);
        }
        if (object instanceof Renderable)
        {
            objectsToRender.add((Renderable)object);
        }
    }

    void update(float deltaTime) {
        updateCheats();
        updateInput();

        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime, this);
        }
    }

    private void updateCheats() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.setPosition(100, 100);
            player.velocity.setZero();
        }
    }

    private void updateInput() {
        playerTarget.on = false;
        if (!player.grounded) {
            // Ignore input if player is not on the ground.
            return;
        }
        controls.update();
        if (controls.getReleased()) {
            Vector2 diff = controls.getDiff();
            player.setPosition(player.position.x + diff.x, player.position.y + diff.y);
        }
        if (controls.isTouched()) {
            Vector2 diff = controls.getDiff();
            playerTarget.on = true;
            playerTarget.position.set(player.position.x + diff.x, player.position.y + diff.y);
        }
    }
}
