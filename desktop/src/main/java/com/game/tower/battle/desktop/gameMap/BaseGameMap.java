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

package com.game.tower.battle.desktop.gameMap;

import com.game.tower.battle.desktop.entity.EntityTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 游戏地图
 * @author liuwei
 */
public abstract class BaseGameMap {

    private final static Logger log = LoggerFactory.getLogger(BaseGameMap.class);

    protected final int unitWidth;
    protected final int unitHeight;
    protected final ConcurrentMap<String, EntityTypeEnum> gameMap = new ConcurrentHashMap<>();

    public BaseGameMap(final int unitWidth, final int unitHeight) {
        this.unitWidth = unitWidth;
        this.unitHeight = unitHeight;
    }

    public EntityTypeEnum getEntity(final int x, final int y) {
        final String position = getMapPosition(x, y);
        log.debug(String.format("get entity: %s", position));
        return gameMap.getOrDefault(position, null);
    }

    public boolean placeEntity(final EntityTypeEnum entityType, final int x, final int y) {
        final String position = getMapPosition(x, y);
        if (gameMap.containsKey(position)) {
            return false;
        }
        log.debug(String.format("put entity: %s", position));
        gameMap.put(position, entityType);
        return true;
    }

    public void destroyEntity(final int x, final int y) {
        final String position = getMapPosition(x, y);
        log.debug(String.format("destroy entity: %s", position));
        gameMap.remove(position);
    }

    protected String getMapPosition(final int x, final int y) {
        return x / unitWidth + "::" + y / unitHeight;
    }

    public Position getCenterPosition(final double x, final double y) {
        final double posX = (int) (x / unitWidth) * unitWidth + unitWidth / 2.0;
        final double posY = (int) (y / unitHeight) * unitHeight + unitHeight / 2.0;
        return new Position(posX, posY);
    }
}
