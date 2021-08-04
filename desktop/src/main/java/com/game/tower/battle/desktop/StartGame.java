/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.game.tower.battle.desktop;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author liuwei
 */
@Component
public class StartGame extends GameApplication implements ApplicationRunner {

    private Entity solider;
    private int levelEnemies = 10;
    private Point2D enemySpawnPoint = new Point2D(50, 0);

    private List<Point2D> waypoints = new ArrayList<>();

    public List<Point2D> getWaypoints() {
        return new ArrayList<>(waypoints);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        launch(new String[]{});
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(500);
        settings.setHeight(750);
        settings.setTitle("Basic Game App");
        settings.setTitle("Asteroids");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("numEnemies", levelEnemies);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());

        spawn("background");

        // TODO: read this from external level data
        waypoints.addAll(Arrays.asList(
                new Point2D(700, 0),
                new Point2D(700, 300),
                new Point2D(50, 300),
                new Point2D(50, 450),
                new Point2D(700, 500)
        ));

        BooleanProperty enemiesLeft = new SimpleBooleanProperty();
        enemiesLeft.bind(getip("numEnemies").greaterThan(0));

        getGameTimer().runAtIntervalWhile(this::spawnEnemy, Duration.seconds(1), enemiesLeft);

        getEventBus().addEventHandler(EnemyKilledEvent.ANY, this::onEnemyKilled);
        getEventBus().addEventHandler(EnemyReachedGoalEvent.ANY, e -> gameOver());
    }

    private void spawnEnemy() {
        inc("numEnemies", -1);

        spawn("Enemy", enemySpawnPoint.getX(), enemySpawnPoint.getY());
    }

    private void onEnemyKilled(EnemyKilledEvent event) {
        levelEnemies--;

        if (levelEnemies == 0) {
            gameOver();
        }

        Entity enemy = event.getEnemy();
        Point2D position = enemy.getPosition();

        Text xMark = getUIFactoryService().newText("X", Color.RED, 24);
        xMark.setTranslateX(position.getX());
        xMark.setTranslateY(position.getY() + 20);

        getGameScene().addGameView(new GameView(xMark, 1000));
    }

    private void gameOver() {
        showMessage("Demo Over. Thanks for playing!", getGameController()::exit);
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityTypeEnum.BULLET_SOLDIER, EntityTypeEnum.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new UserAction("Place Tower") {
            private final Rectangle2D worldBounds = new Rectangle2D(0, 0, getAppWidth(), getAppHeight());

            @Override
            protected void onActionBegin() {
                if (worldBounds.contains(input.getMousePositionWorld())) {
                    placeTower();
                }
            }
        }, MouseButton.PRIMARY);
    }

    private void placeTower() {
        spawn("Tower",
                new SpawnData(getInput().getMouseXWorld(), getInput().getMouseYWorld()).put("tower", "arrow_tower.png")
        );
    }
}
