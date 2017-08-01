package com.game;

import java.awt.*;

public class Turret extends Entity {

    private Enterprise owner;

    public Turret(StarTrek ref, int x, int y, int width, int height, int health, Enterprise owner) {
        super(ref, x, y, width, height, health);
        this.owner = owner;
        world.addEntity(this);
    }

    public void fire() {
        world.addEntity(buildBullet());
    }

    public void move(long delta) {
        setSpeedX(owner.speedX);
        setSpeedY(owner.speedY);
        super.move(delta);
    }

    private Entity buildBullet() {
        Entity bullet = new Bullet(world, this, pos.x+w, pos.y+h/2, 30, 6, Side.PLAYER);
        bullet.setSpeedX(400);
        return bullet;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
