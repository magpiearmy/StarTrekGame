package com.game.commands;

import com.game.Enterprise;
import com.game.Entity;

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
