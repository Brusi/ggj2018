package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;
import com.brusi.ggj2018.utils.Controls;
import com.brusi.ggj2018.utils.TouchToPoint;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    protected Player player = new Player(100, 100);

    protected ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();
    private Controls controls;

    public World(Controls controls)
    {
        this.controls = controls;
        AddObject(player);
    }
    
    public void AddObject(Object object)
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
        updateInput();
        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime);
        }
    }

    private void updateInput() {
        controls.update();
        if (controls.getReleased()) {
            Gdx.app.log("DEBUG", "Touch released.");
            Vector2 basePos = controls.getBasePos();
            Vector2 touchPos = controls.getTouchPos();
            float xdiff = basePos.x - touchPos.x;
            float ydiff = basePos.y - touchPos.y;
            player.setPosition(player.position.x + xdiff, player.position.y + ydiff);
        }
    }
}
