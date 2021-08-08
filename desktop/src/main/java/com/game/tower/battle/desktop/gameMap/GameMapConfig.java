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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuwei
 */
@Configuration
@ConfigurationProperties(prefix = "game.map")
public class GameMapConfig {

    private int width;
    private int height;
    private int unitWidth;
    private int unitHeight;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getUnitWidth() {
        return unitWidth;
    }

    public void setUnitWidth(int unitWidth) {
        this.unitWidth = unitWidth;
    }

    public int getUnitHeight() {
        return unitHeight;
    }

    public void setUnitHeight(int unitHeight) {
        this.unitHeight = unitHeight;
    }
}
