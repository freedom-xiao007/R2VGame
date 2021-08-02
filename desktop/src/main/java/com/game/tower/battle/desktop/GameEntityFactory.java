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

import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
        return entityBuilder()
                .view(new Rectangle(getAppWidth(), getAppHeight()))
                .build();
    }

    @Spawns(value = "enemy")
    public Entity createEnemy(final SpawnData data) {
        return entityBuilder()
                .type(EntityTypeEnum.ENEMY)
                .viewWithBBox("slime.png")
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }

    @Spawns(value = "soldier")
    public Entity createSoldier(final SpawnData data) {
        return entityBuilder()
                .type(EntityTypeEnum.SOLDIER)
                .viewWithBBox("sword-wound.png")
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }

    @Spawns(value = "lineBulletOfSolider")
    public Entity createLineBulletOfSolider(final SpawnData data) {
        return entityBuilder()
                .type(EntityTypeEnum.BULLET_SOLDIER)
                .viewWithBBox(new Rectangle(1, 15, Color.GRAY))
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }

    @Spawns(value = "ballBulletOfSolider")
    public Entity createBallBulletOfSolider(final SpawnData data) {
        return entityBuilder()
                .type(EntityTypeEnum.BULLET_SOLDIER)
                .viewWithBBox(new Circle(5, 5, 5, Color.BLUEVIOLET))
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }
}
