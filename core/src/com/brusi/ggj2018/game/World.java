package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.game.graphic.ArrowOnionSkin;
import com.brusi.ggj2018.game.graphic.BoneParticle;
import com.brusi.ggj2018.game.graphic.Particle;
import com.brusi.ggj2018.game.graphic.PlayerTarget;
import com.brusi.ggj2018.game.graphic.TeleportParticle;
import com.brusi.ggj2018.game.objects.Arrow;
import com.brusi.ggj2018.game.objects.EnemyGenerator;
import com.brusi.ggj2018.game.objects.Energy;
import com.brusi.ggj2018.game.objects.Platform;
import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;
import com.brusi.ggj2018.utils.Controls;
import com.brusi.ggj2018.utils.EventQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    private final EnemyGenerator enemyGenerator;
    public Player player = new Player(0, 0);
    public Energy energy = new Energy(-WorldRenderer.FRUSTUM_WIDTH / 2 + 30, 0, 20, 250);
    final public PlayerTarget playerTarget = new PlayerTarget();

    public ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable>[] objectsToRender;
    private HashMap<Object, Integer> objectsToLayer = new HashMap<Object, Integer>();
    public ArrayList<Platform> platforms = new ArrayList<Platform>();
    //public ArrayList<Particle> particles = new ArrayList<Particle>();

    Controls controls;

    float bulletTimeRatio = 1;

    final EventQueue bulletTimeEvents = new EventQueue();

    float gameTime = 0;

    public World(Controls controls)
    {
        objectsToRender = new ArrayList[20];
        for (int i = 0; i < objectsToRender.length; i++) {
            objectsToRender[i] = new ArrayList<Renderable>();
        }
        this.controls = controls;
        enemyGenerator = new EnemyGenerator(9, 1, 6, 2);
        addObject(enemyGenerator);
        addObject(playerTarget);
        createPlatforms();
        addObject(player);
        addObject(energy, 15);
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
    private ArrayList<Object> awaitingRemoveObjects = new ArrayList<Object>();

    public void addObject(Object object)
    {
        addObject(object, 10);
    }

    public void addObject(Object object, int layer)
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
            objectsToRender[layer].add((Renderable)object);
            objectsToLayer.put(object, layer);
        }
    }

    public void removeObject(Object object) {
        if (lockObjectCreation) {
            awaitingRemoveObjects.add(object);
            return;
        }
        if (object instanceof Updatable)
        {
            objectsToUpdate.remove((Updatable)object);
        }
        if (object instanceof Renderable)
        {
            if (objectsToLayer.containsKey(object)) {
                int layer = objectsToLayer.get(object);
                objectsToRender[layer].remove((Renderable) object);
            }
        }
    }

    void update(float deltaTime) {
        lockObjectCreation = true;
        updateCheats(deltaTime);

        bulletTimeEvents.update(deltaTime);

        deltaTime = getBulletTime(deltaTime);
        gameTime += deltaTime;
        updateInput(deltaTime);

        //updateParticles(deltaTime);

        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime, this);
        }
        lockObjectCreation = false;
        for (Object awaitingObject : awaitingObjects) {
            addObject(awaitingObject);
        }
        awaitingObjects.clear();
        for (Object awaitingObject : awaitingRemoveObjects) {
            removeObject(awaitingObject);
        }
        awaitingRemoveObjects.clear();
    }

    private void updateCheats(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            enemyGenerator.generateEnemy(this);
        }
    }

    private float getBulletTime(float deltaTime) {
        if (isDead()) {
            // Always bullet time on death!
            return 0.1f * deltaTime;
        }

        if (playerTarget.on) {
            bulletTimeRatio = (bulletTimeRatio * 0.8f + 0.2f * 0.2f);
            if (bulletTimeEvents.isEmpty()) {
                bulletTimeEvents.addEventFromNow(0.3f, new EventQueue.Event() {
                    @Override
                    public void invoke() {
                        addBulletTimeEvents();
                    }
                });
            }
        } else {
            bulletTimeEvents.clear();
            bulletTimeRatio = (bulletTimeRatio * 0.8f + 0.2f);
        }
        return deltaTime * bulletTimeRatio;
    }

    private void addBulletTimeEvents() {
        for (Updatable obj : objectsToUpdate) {
            if (obj instanceof  Arrow) {
                Arrow arrow = (Arrow)obj;
                addObject(new ArrowOnionSkin(arrow.position.x, arrow.position.y,
                        arrow.getRotation(), arrow.mirror));
            }
        }
    }

    /*private void updateParticles(float deltaTime) {
        for (Particle p : particles) {
            p.update(deltaTime);
        }
        for (Iterator<Particle> it = particles.iterator(); it.hasNext();) {
            Particle p = it.next();
            if (!p.active) {
                it.remove();
            }
        }
    }*/

    private boolean energyLow = false;

    private void updateInput(float deltaTime) {
        boolean startedBefore = playerTarget.on;
        playerTarget.on = false;
        if (!player.grounded || isDead()) {
            // Ignore input if player is not on the ground.
            return;
        }
        controls.update();
        if (energyLow && controls.isTouched()) {
            return;
        }
        energyLow = energy.energy < Energy.ENERGY_LOW;
        if ((energyLow && !startedBefore) || energy.empty) {
            return;
        }
        energyLow = false;
        if (controls.getReleased() && startedBefore) {
            Vector2 diff = controls.getDiff();
            if (diff.len() < 30) {
                // Teleport is too short!
                return;
            }
            createTeleportParticles(player.position, 8, 0.5f, Assets.get().disappear_teleport_particle);
            player.setPosition(player.position.x + diff.x, player.position.y + diff.y);
            createTeleportParticles(player.position, 20, 1, Assets.get().teleport_particle);
        }
        if (controls.isTouched()) {
            energy.updateTouch(deltaTime);
            Vector2 diff = controls.getDiff();
            playerTarget.on = true;
            playerTarget.position.set(player.position.x + diff.x, player.position.y + diff.y);
            player.mirror = diff.x < 0;
            playerTarget.mirror = player.mirror;
        }
    }

    private void createTeleportParticles(Vector2 position, int number, float time, Array<Sprite> sprites) {
        Gdx.app.log("DEBUG", "Add teleport particles.");
        for (int i=0; i < number; i++) {
            addObject(new TeleportParticle(position.x, position.y, time, sprites));
        }
    }

    public boolean isDead() {
        return player.dead;
    }

    public void addBoneParticles(float x, float y) {
        for (int i=0; i < 5; i++) {
            addObject(new BoneParticle(x, y));
        }
    }
}
