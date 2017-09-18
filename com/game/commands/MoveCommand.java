package com.game.commands;

import com.game.Entity;

public class MoveCommand implements GameCommand {

    private final Direction dir;
    private final Entity entity;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public MoveCommand(Direction dir, Entity entity) {
        this.dir = dir;
        this.entity = entity;
    }

    @Override
    public void execute() {
        switch(dir) {
            case UP: entity.setSpeedY(-400); break;
            case DOWN: entity.setSpeedY(400); break;
            case LEFT: entity.setSpeedX(-400); break;
            case RIGHT: entity.setSpeedX(400); break;
        }
    }
}
