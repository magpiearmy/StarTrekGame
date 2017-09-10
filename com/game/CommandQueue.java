package com.game;

import java.util.ArrayList;
import java.util.List;

public class CommandQueue {
    private List<GameCommand> queue = new ArrayList<>();

    public void addCommand(GameCommand command) {
        queue.add(command);
    }

    public GameCommand getNextCommand() {
        GameCommand command = null;
        final int idx = queue.size() - 1;
        if (idx > 0) {
            command = queue.get(queue.size() - 1);
            queue.remove(idx);
        }
        return command;
    }
}
