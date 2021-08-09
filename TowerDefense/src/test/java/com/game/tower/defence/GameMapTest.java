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

package com.game.tower.defence;

import com.game.tower.defence.entity.EntityTypeEnum;
import com.game.tower.defence.gameMap.BaseGameMap;
import com.game.tower.defence.gameMap.GameMapConfig;
import com.game.tower.defence.gameMap.GameMapManage;
import com.game.tower.defence.gameMap.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author liuwei
 */
public class GameMapTest {

    private BaseGameMap gameMap;

    @BeforeEach
    public void init() {
        final GameMapConfig config = new GameMapConfig();
        config.setWidth(100);
        config.setHeight(100);
        config.setUnitWidth(30);
        config.setUnitHeight(30);
        this.gameMap = new GameMapManage(config);
    }

    @Test
    public void placeTest() {
        Assertions.assertNull(gameMap.getEntity(0, 0));

        Assertions.assertTrue(gameMap.placeEntity(EntityTypeEnum.ENEMY, 0, 0));
        EntityTypeEnum typeEnum = gameMap.getEntity(0, 0);
        Assertions.assertEquals(EntityTypeEnum.ENEMY, typeEnum);
        typeEnum = gameMap.getEntity(10, 20);
        Assertions.assertEquals(EntityTypeEnum.ENEMY, typeEnum);

        Assertions.assertNull(gameMap.getEntity(30, 0));
        Assertions.assertNull(gameMap.getEntity(30, 20));
        Assertions.assertNull(gameMap.getEntity(30, 30));
        Assertions.assertNull(gameMap.getEntity(0, 30));
        Assertions.assertNull(gameMap.getEntity(25, 30));

        Assertions.assertTrue(gameMap.placeEntity(EntityTypeEnum.ENEMY, 90, 0));
        typeEnum = gameMap.getEntity(95, 0);
        Assertions.assertEquals(EntityTypeEnum.ENEMY, typeEnum);
    }

    @Test
    public void destroyTest() {
        gameMap.destroyEntity(0, 0);
        Assertions.assertNull(gameMap.getEntity(0, 0));

        Assertions.assertTrue(gameMap.placeEntity(EntityTypeEnum.ENEMY, 0, 0));

        gameMap.destroyEntity(25, 30);
        Assertions.assertEquals(EntityTypeEnum.ENEMY, gameMap.getEntity(0, 0));

        gameMap.destroyEntity(25, 25);
        Assertions.assertNull(gameMap.getEntity(0, 0));
    }

    @Test
    public void getCenterPosition() {
        Position position = gameMap.getCenterPosition(0, 0);
        Assertions.assertEquals(15.0, position.getX());
        Assertions.assertEquals(15.0, position.getY());

        position = gameMap.getCenterPosition(30, 15);
        Assertions.assertEquals(45.0, position.getX());
        Assertions.assertEquals(15.0, position.getY());
    }
}
