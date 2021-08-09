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

package com.game.tower.defence.gameMap;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author liuwei
 */
public class GameMapManage extends BaseGameMap {

    private final static Logger log = LoggerFactory.getLogger(GameMapManage.class);

    private final int width;
    private final int height;

    public GameMapManage(final GameMapConfig config) {
        super(config.getUnitWidth(), config.getUnitHeight());
        this.width = config.getWidth();
        this.height = config.getHeight();
    }

    public void init() {
        FXGL.spawn("background");
        for (int i = 1; i < height / unitHeight + 1; i++) {
            FXGL.spawn("HorizontalLine", 0, i * unitHeight);
        }
        for (int i = 1; i < width / unitWidth + 1; i++) {
            FXGL.spawn("VerticalLine", i * unitWidth, 0);
        }
    }

    public void placeTower() {
        final double x = getInput().getMouseXWorld();
        final double y = getInput().getMouseYWorld();
        final Position position = getCenterPosition(x, y);
        log.debug(String.format("place： %f, %f", position.getX() - 25, position.getY() - 25));
        final SpawnData spawnData = new SpawnData(position.getX() - 12, position.getY() - 14).put("tower", "arrow_tower.png");
        spawn("Tower", spawnData);
    }
}
