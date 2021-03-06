package com.game.input;

import com.game.*;
import com.game.commands.CommandQueue;
import com.game.commands.FireCommand;
import com.game.commands.MoveCommand;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.game.commands.MoveCommand.*;

public class GameKeyListener implements KeyListener {
    private World world;
    private CommandQueue queue;
    private boolean released = true;

    public GameKeyListener(CommandQueue queue) {
        world = World.getInstance();
        this.queue = queue;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Enterprise enterprise = (Enterprise) world.getEnterprise();

        if (released) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    queue.addCommand(new MoveCommand(Direction.UP, enterprise));
                    break;
                case KeyEvent.VK_DOWN:
                    queue.addCommand(new MoveCommand(Direction.DOWN, enterprise));
                    break;
                case KeyEvent.VK_SPACE:
                    queue.addCommand(new FireCommand(enterprise));
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Entity enterprise = world.getEnterprise();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                enterprise.setSpeedY(0);
                released = true;
                break;
        }
    }
}
