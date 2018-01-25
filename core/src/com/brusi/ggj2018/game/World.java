package com.brusi.ggj2018.game;

import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;

import java.util.ArrayList;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    protected Player player = new Player(100, 100);

    protected ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();

    public World()
    {
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

    void update(float deltaTime)
    {
        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime);
        }
    }
}
