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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 游戏地图
 * @author liuwei
 */
public class GameMap {

    private final static Logger log = LoggerFactory.getLogger(GameMap.class);

    private final int unitWidth;
    private final int unitHeight;
    private final ConcurrentMap<String, EntityTypeEnum> gameMap = new ConcurrentHashMap<>();

    public GameMap(final int unitWidth, final int unitHeight) {
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
    }

    public EntityTypeEnum getEntity(final int x, final int y) {
        final String position = getMapPosition(x, y);
        log.info(String.format("get entity: %s", position));
        return gameMap.getOrDefault(position, null);
    }

    public boolean placeEntity(final EntityTypeEnum entityType, final int x, final int y) {
        final String position = getMapPosition(x, y);
        if (gameMap.containsKey(position)) {
            return false;
        }
        log.info(String.format("put entity: %s", position));
        gameMap.put(position, entityType);
        return true;
    }

    public void destroyEntity(final int x, final int y) {
        final String position = getMapPosition(x, y);
        log.info(String.format("destroy entity: %s", position));
        gameMap.remove(position);
    }

    private String getMapPosition(final int x, final int y) {
        return x / unitWidth + "::" + y / unitHeight;
    }
}
