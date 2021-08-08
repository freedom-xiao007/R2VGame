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

package com.game.tower.battle.desktop.entity.tower;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.game.tower.battle.desktop.entity.EntityTypeEnum;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 * @author liuwei
 */
public class TowerComponent extends Component {

    private LocalTimer shootTimer;

    @Override
    public void onAdded() {
        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {

        if (shootTimer.elapsed(Duration.seconds(0.5))) {
            FXGL.getGameWorld()
                    .getClosestEntity(entity, e -> e.isType(EntityTypeEnum.ENEMY))
                    .ifPresent(nearestEnemy -> {
                        shoot(nearestEnemy);
                        shootTimer.capture();
                    });
        }
    }

    private void shoot(Entity enemy) {
        Point2D position = getEntity().getPosition();

        Point2D direction = enemy.getPosition().subtract(position);

        Entity bullet = FXGL.spawn("BulletOfTower", new SpawnData(position).put("texturesPath", "arrow_attack.png"));
        bullet.addComponent(new ProjectileComponent(direction, 5 * 60));
    }
}
