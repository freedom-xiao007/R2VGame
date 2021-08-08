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

package com.game.tower.battle.desktop.entity.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.game.tower.battle.desktop.StartGame;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * @author liuwei
 */
public class EnemyComponent extends Component {

    private List<Point2D> waypoints;
    private Point2D nextWaypoint;

    private double speed;

    @Override
    public void onAdded() {
        waypoints = ((StartGame) FXGL.getApp()).getWaypoints();

        nextWaypoint = waypoints.remove(0);
    }

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60 * 2;

        Point2D velocity = nextWaypoint.subtract(entity.getPosition())
                .normalize()
                .multiply(speed);

        entity.translate(velocity);

        if (nextWaypoint.distance(entity.getPosition()) < speed) {
            entity.setPosition(nextWaypoint);

            if (!waypoints.isEmpty()) {
                nextWaypoint = waypoints.remove(0);
            } else {

                FXGL.getEventBus().fireEvent(new EnemyReachedGoalEvent());
            }
        }
    }
}
