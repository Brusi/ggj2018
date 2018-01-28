package com.brusi.ggj2018.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.game.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Asaf on 26/01/2018.
 */

public class EnemyGenerator implements Updatable {

    private int maxEnemies;
    private float enemyEveryTimeMin;
    private float enemyEveryTimeMax;
    private float enemyNextTime;
    private Random rand = new Random();

    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public EnemyGenerator(int maxEnemies, float enemyEveryTimeMin, float enemyEveryTimeMax, float firstEnemyTime) {
        this.maxEnemies = maxEnemies;
        this.enemyEveryTimeMax = enemyEveryTimeMax;
        this.enemyEveryTimeMin = enemyEveryTimeMin;
        enemyNextTime = firstEnemyTime;
    }

    @Override
    public void update(float deltaTime, World world) {
        if (enemyNextTime >= 0) {
            enemyNextTime -= deltaTime;
            if (enemyNextTime <= 0) {
                generateEnemy(world);
                enemyNextTime = -1;
            }
        } else {
            DeactivateClassRemover.filter(enemies);
            if (enemies.size() < maxEnemies) {
                enemyNextTime = enemyEveryTimeMin + rand.nextFloat() * (enemyEveryTimeMax - enemyEveryTimeMin);
            }
        }
    }

    private float dist(Platform platform, World world) {
        return Math.abs(platform.position.y - world.player.position.y);
    }

    public void generateEnemy(final World world) {
        Enemy created = new Enemy(80, 230);
        Collections.sort(world.platforms, new Comparator<Platform>() {
            @Override
            public int compare(Platform platform, Platform t1) {
                return (int)(10000 * (Math.abs(platform.position.y - world.player.bounds.y) - Math.abs(t1.position.y - world.player.bounds.y)));
            }
        });
        int select = Utils.randomInt(5);
        Platform plat = world.platforms.get(select);
        created.targetPlatform = plat;
        Vector2 targetPosition = new Vector2(plat.position.x + Utils.random2Range(plat.bounds.getWidth() * 0.4f),
                plat.position.y);
        created.velocity.x = 400 * (targetPosition.x > world.player.position.y ? -1 : 1);
        created.mirror = created.velocity.x > 0;
        created.setPosition(targetPosition.x - created.velocity.x * 0.5f, targetPosition.y + plat.bounds.height - (Unit.BASE_ACCEL / 2) * 0.25f);
        enemies.add(created);
        world.addObject(created);
    }
}

