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

import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.TimeComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

/**
 * @author liuwei
 */
public class GameEntityFactory implements EntityFactory {

    @Spawns(value = "background")
    public Entity createFloor(final SpawnData data) {
        return entityBuilder(data)
                .view(new Rectangle(getAppWidth(), getAppHeight()))
                .build();
    }

    @Spawns(value = "Enemy")
    public Entity createEnemy(final SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypeEnum.ENEMY)
                .viewWithBBox("slime.png")
                .with(new CollidableComponent(true))
                .with(new EnemyComponent())
                .build();
    }

    @Spawns(value = "soldier")
    public Entity createSoldier(final SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypeEnum.SOLDIER)
                .viewWithBBox("sword-wound.png")
//                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .with(new SoldierComponent())
                .collidable()
                .build();
    }

    @Spawns(value = "Tower")
    public Entity createTower(final SpawnData data) {
        final String tower = data.get("tower");
        return entityBuilder(data)
                .type(EntityTypeEnum.SOLDIER)
                .viewWithBBox(tower)
//                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .with(new SoldierComponent())
                .collidable()
                .build();
    }

    @Spawns(value = "BulletOfTower")
    public Entity createBulletOfTower(final SpawnData data) {
        final String bullet = data.get("texturesPath");
        return entityBuilder(data)
                .type(EntityTypeEnum.BULLET_SOLDIER)
                .viewWithBBox(bullet)
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent())
                .build();
    }

    @Spawns(value = "lineBulletOfSolider")
    public Entity createLineBulletOfSolider(final SpawnData data) {
        Point2D dir = data.get("dir");

        var effectComponent = new EffectComponent();

        return entityBuilder(data)
                .type(EntityTypeEnum.BULLET_SOLDIER)
                .viewWithBBox(new Rectangle(1, 15, Color.GRAY))
                .with(new ProjectileComponent(dir, 500))
                .with(new OffscreenCleanComponent())
                .with(new TimeComponent())
                .with(effectComponent)
                .collidable()
                .build();
    }

    @Spawns(value = "ballBulletOfSolider")
    public Entity createBallBulletOfSolider(final SpawnData data) {
        return entityBuilder(data)
                .type(EntityTypeEnum.BULLET_SOLDIER)
                .viewWithBBox(new Circle(5, 5, 5, Color.BLUEVIOLET))
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }
}
