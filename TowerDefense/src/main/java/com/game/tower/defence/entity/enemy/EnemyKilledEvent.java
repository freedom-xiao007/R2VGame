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

package com.game.tower.defence.entity.enemy;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Occurs when an enemy is killed.
 *
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class EnemyKilledEvent extends Event {

    public static final EventType<EnemyKilledEvent> ANY
            = new EventType<>(Event.ANY, "ENEMY_KILLED");

    private Entity enemy;

    /**
     * @return killed enemy
     */
    public Entity getEnemy() {
        return enemy;
    }

    public EnemyKilledEvent(Entity enemy) {
        super(ANY);
        this.enemy = enemy;
    }
}
