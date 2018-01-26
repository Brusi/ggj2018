package com.brusi.ggj2018.game.objects;

import com.brusi.ggj2018.game.World;

import java.util.ArrayList;
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

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public EnemyGenerator(int maxEnemies, float enemyEveryTimeMin, float enemyEveryTimeMax) {
        this.maxEnemies = maxEnemies;
        this.enemyEveryTimeMax = enemyEveryTimeMax;
        this.enemyEveryTimeMin = enemyEveryTimeMin;
        enemyNextTime = 0;
    }

    @Override
    public void update(float deltaTime, World world) {
        if (enemyNextTime > 0) {
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

    private void generateEnemy(World world) {
        Enemy created = new Enemy(80, 200);
        enemies.add(created);
        world.addObject(created);
    }
}

