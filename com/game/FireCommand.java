package com.game;

public class FireCommand implements GameCommand {
    private Enterprise enterprise;

    public FireCommand(Entity entity) {
        this.enterprise = (Enterprise)entity;
    }

    @Override
    public void execute() {
        enterprise.fire();
    }
}
