package com.game;

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
            case UP: entity.setSpeedY(-200); break;
            case DOWN: entity.setSpeedY(200); break;
            case LEFT: entity.setSpeedX(-200); break;
            case RIGHT: entity.setSpeedX(200); break;
        }
    }
}
