package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.graphic.PlayerTarget;
import com.brusi.ggj2018.game.objects.EnemyGenerator;
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
    public Player player = new Player(0, 0);
    final public PlayerTarget playerTarget = new PlayerTarget();

    protected ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();
    public ArrayList<Platform> platforms = new ArrayList<Platform>();

    Controls controls;
    private boolean dead;

    public World(Controls controls)
    {
        this.controls = controls;
        addObject(player);
        addObject(new EnemyGenerator(3, 1, 5));
        objectsToRender.add(playerTarget);
        addObject(new EnemyGenerator(3, 5, 15));
        createPlatforms();
    }

    private void createPlatforms()
    {
        addPlatform(0, -40, 4);
        addPlatform(200, 60, 3);
        addPlatform(-200, 60, 3);
        addPlatform(0, 140, 2);
        addPlatform(350, 180, 2);
        addPlatform(-350, 180, 2);
        addPlatform(-400, -100, 4);
        addPlatform(400, -100, 3);
        addPlatform(300, -200, 4);
        addPlatform(-250, -220, 3);
    }

    private Platform addPlatform(float x, float y, int width) {
        Platform p = new Platform(x, y, width);
        addObject(p);
        platforms.add(p);
        return p;
    }

    private boolean lockObjectCreation = false;
    private ArrayList<Object> awaitingObjects = new ArrayList<Object>();

    public void addObject(Object object)
    {
        if (lockObjectCreation) {
            awaitingObjects.add(object);
            return;
        }
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
        lockObjectCreation = true;
        updateInput();

        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime, this);
        }
        lockObjectCreation = false;
        for (Object awaitingObject : awaitingObjects) {
            addObject(awaitingObject);
        }
        awaitingObjects.clear();
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

    public boolean isDead() {
        return player.dead;
    }
}
