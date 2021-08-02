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
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.util.Duration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author liuwei
 */
@Component
public class StartGame extends GameApplication implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        launch(new String[]{});
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(500);
        gameSettings.setHeight(750);
        gameSettings.setTitle("Basic Game App");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("background");
        spawn("enemy", getAppHeight(), getAppWidth());
        spawn("soldier", getAppHeight(), getAppWidth());
        spawn("lineBulletOfSolider", getAppHeight(), getAppWidth());
        spawn("ballBulletOfSolider", getAppHeight(), getAppWidth());

        com.almasb.fxgl.dsl.FXGL.run(() -> {
            Entity e = getGameWorld().create("enemy", new SpawnData(100, 100));

            spawnWithScale(e, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(2));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityTypeEnum.BULLET_SOLDIER, EntityTypeEnum.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });
    }
}
