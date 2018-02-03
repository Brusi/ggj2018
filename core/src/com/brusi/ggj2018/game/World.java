package com.brusi.ggj2018.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brusi.ggj2018.assets.Assets;
import com.brusi.ggj2018.assets.SoundAssets;
import com.brusi.ggj2018.game.graphic.ArrowOnionSkin;
import com.brusi.ggj2018.game.graphic.FadeOutEffect;
import com.brusi.ggj2018.game.graphic.PlayerTarget;
import com.brusi.ggj2018.game.graphic.SinglePlayParticle;
import com.brusi.ggj2018.game.graphic.TeleportParticle;
import com.brusi.ggj2018.game.objects.Arrow;
import com.brusi.ggj2018.game.objects.Enemy;
import com.brusi.ggj2018.game.objects.EnemyGenerator;
import com.brusi.ggj2018.game.objects.Energy;
import com.brusi.ggj2018.game.objects.GameObject;
import com.brusi.ggj2018.game.objects.Platform;
import com.brusi.ggj2018.game.objects.Player;
import com.brusi.ggj2018.game.objects.Renderable;
import com.brusi.ggj2018.game.objects.Updatable;
import com.brusi.ggj2018.utils.Controls;
import com.brusi.ggj2018.utils.EventQueue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pc on 1/26/2018.
 */

public class World {
    public static final float BULLET_TIME_RATIO = 0.2f;
    private final EnemyGenerator enemyGenerator;
    public Player player = new Player(0, 0);
    public Energy energy;
    final public PlayerTarget playerTarget = new PlayerTarget();

    public ArrayList<Updatable> objectsToUpdate = new ArrayList<Updatable>();
    protected ArrayList<Renderable>[] objectsToRender;
    private HashMap<Object, Integer> objectsToLayer = new HashMap<Object, Integer>();
    public ArrayList<Platform> platforms = new ArrayList<Platform>();

    Controls controls;

    float bulletTimeRatio = 1;

    final EventQueue bulletTimeEvents = new EventQueue();

    public float gameTime = 0;
    public int killcount = 0;

    public World(Controls controls)
    {
        objectsToRender = new ArrayList[20];
        for (int i = 0; i < objectsToRender.length; i++) {
            objectsToRender[i] = new ArrayList<Renderable>();
        }
        this.controls = controls;
        enemyGenerator = new EnemyGenerator(9, 1, 5, 2);
        addObject(enemyGenerator);
        addObject(playerTarget);
        int level = Utils.randomInt(4);
        if (level == 0) {
            createPlatforms();
        } else if (level == 1) {
            createPlatforms2();
        } else if (level == 2){
            createPlatforms3();
        } else {
            createPlatforms4();
        }
        addObject(player);
        energy = new Energy(-WorldRenderer.FRUSTUM_WIDTH / 2 + 30, 0, 20, 250);
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

    private void createPlatforms2()
    {
        addPlatform(0, -40, 3);
        addPlatform(0, 60, 4);
        addPlatform(0, -160, 4);
        addPlatform(-200, 0, 2);
        addPlatform(200, 0, 2);
        addPlatform(350, 200, 3);
        addPlatform(-350, 200, 3);
        addPlatform(0, -250, 8);
    }

    private void createPlatforms3()
    {
        addPlatform(0, -40, 6);
        addPlatform(-70, 250, 2);
        addPlatform(-0, 170, 2);
        addPlatform(200, -220, 2);
        addPlatform(250, 250, 2);
        addPlatform(-150, 100, 2);
        addPlatform(400, -180, 2);
        addPlatform(-250, -170, 2);
        addPlatform(350, -80, 2);
        addPlatform(230, -120, 2);
        addPlatform(-290, 60, 2);
        addPlatform(-280, 190, 2);
        addPlatform(-90, -270, 2);
        addPlatform(280, 40, 2);
        addPlatform(230, 120, 2);
    }

    private void createPlatforms4()
    {
        addPlatform(0, 160, 9);
        for (int i = 4; i < 7; i++) {
            addPlatform(i * 60, 80 * (i - 5), 3);
            addPlatform(i * -60, 80 * (i - 5), 3);
        }
        addPlatform(0, 80 * (3 - 5), 7);
        addPlatform(0, 80 * (2 - 5), 6);
    }

    private Platform addPlatform(float x, float y, int width) {
        Platform p = new Platform(x, y, width);
        addObject(p);
        platforms.add(p);
        return p;
    }

    private boolean lockObjectCreation = false;
    private class ObjectToAdd
    {
        public Object obj;
        public int layer;
    }
    private ArrayList<ObjectToAdd> awaitingObjects = new ArrayList<ObjectToAdd>();
    private ArrayList<Object> awaitingRemoveObjects = new ArrayList<Object>();

    public void addObject(Object object)
    {
        addObject(object, 10);
    }

    public void addObject(Object object, int layer)
    {
        if (lockObjectCreation) {
            ObjectToAdd o = new ObjectToAdd();
            o.obj = object;
            o.layer = layer;
            awaitingObjects.add(o);
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
        if (!isDead()) {
            gameTime += deltaTime;
        }
        updateInput(deltaTime);

        updateEnemyCollision();

        //updateParticles(deltaTime);

        for (Updatable object : objectsToUpdate) {
            object.update(deltaTime, this);
        }
        lockObjectCreation = false;
        for (ObjectToAdd awaitingObject : awaitingObjects) {
            addObject(awaitingObject.obj, awaitingObject.layer);
        }
        awaitingObjects.clear();
        for (Object awaitingObject : awaitingRemoveObjects) {
            removeObject(awaitingObject);
        }
        awaitingRemoveObjects.clear();
    }

    private void updateEnemyCollision() {
        for (Enemy enemy1 : enemyGenerator.enemies) {
            for (Enemy enemy2 : enemyGenerator.enemies) {
                if (enemy1 == enemy2 || enemy1.targetPlatform != enemy2.targetPlatform) {
                    continue;
                }
                if (enemy1.bounds.overlaps(enemy2.bounds)) {
                    float avgX = (enemy1.position.x + enemy2.position.x) * 0.5f;
                    float avgVel = (enemy1.velocity.x + enemy2.velocity.x) * 0.5f;
                    if (enemy1.position.x < enemy2.position.x) {
                        enemy1.setPosition(avgX - enemy1.bounds.width / 2, enemy1.position.y);
                        enemy2.setPosition(avgX + enemy2.bounds.width / 2, enemy2.position.y);
                    } else {
                        enemy1.setPosition(avgX + enemy1.bounds.width / 2, enemy1.position.y);
                        enemy2.setPosition(avgX - enemy2.bounds.width / 2, enemy2.position.y);
                    }
                    enemy1.velocity.x = enemy2.velocity.x = avgVel;

                }
            }
        }
    }

    private void updateCheats(float deltaTime) {
        // Cheats are disabled for production!
//        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
//            enemyGenerator.generateEnemy(this);
//        }
    }

    private float getBulletTime(float deltaTime) {
//        if (isDead()) {
//            // Always bullet time on player_death!
//            return 0.1f * deltaTime;
//        }

        if (playerTarget.on) {
            bulletTimeRatio = BULLET_TIME_RATIO;
            if (bulletTimeEvents.isEmpty()) {
                addBulletTimeEvents();
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
                        arrow.getRotation(), arrow.mirror), 8);
            }
        }
    }

    private boolean energyLow = false;

    private void updateInput(float deltaTime) {
        boolean startedBefore = playerTarget.on;
        playerTarget.on = false;
        if (/*!player.grounded || */isDead()) {
            // Ignore input if player is not on the ground.
            return;
        }
        controls.update();
        if (energyLow && controls.isTouched()) {
            return;
        }
        energyLow = energy.energy < Energy.ENERGY_LOW;
        if ((energyLow && !startedBefore) || energy.empty) {
            playerTarget.position.set(player.position);
            return;
        }
        energyLow = false;
        if (controls.getReleased() && startedBefore) {
            Vector2 diff = controls.getDiff();
            if (diff.len() < 15) {
                // Teleport is too short!
                return;
            }
            //createTeleportParticles(player.position, 8, 0.5f, Assets.get().disappear_teleport_particle);
            addObject(new FadeOutEffect(player, Color.BLACK, 0.3f), 9);
            player.setPosition(player.position.x + diff.x, player.position.y + diff.y);
            player.velocity.setZero();
            //createTeleportParticles(player.position, 20, 1, Assets.get().teleport_particle);
            SoundAssets.get().playRandomSound(SoundAssets.get().teleport);
            addObject(new SinglePlayParticle(Assets.get().teleport_effect, player.position.x, player.position.y - 10, 0.3f), 14);
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

    public boolean isDead() {
        return player.dead;
    }
}
