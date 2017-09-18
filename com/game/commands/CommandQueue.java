package com.game.commands;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {
    private List<GameCommand> queue = new ArrayList<>();

    public void addCommand(GameCommand command) {
        queue.add(command);
    }

    public GameCommand getNextCommand() {
        return queue.isEmpty() ? null : queue.remove(0);
    }
}
