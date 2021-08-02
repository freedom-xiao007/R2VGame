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
import javafx.geometry.Point2D;
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

    private Entity solider;

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
    }

    @Override
    protected void initGame() {
        getSettings().setGlobalSoundVolume(0.1);

        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("background");
        solider = spawn("soldier", -100.0 , -100.0);

        com.almasb.fxgl.dsl.FXGL.run(() -> {
            Entity enemy = getGameWorld().create("enemy", new SpawnData(100, 100));
            spawnWithScale(enemy, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());

            solider.getComponent(SoldierComponent.class).attack();
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
