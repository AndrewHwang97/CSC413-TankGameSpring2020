package tankGame;

import java.awt.*;

public class Collisions {

    Rectangle hbTank1;
    Rectangle hbTank2;
    Rectangle hbOther;
    Rectangle hbWall;
    SoundManager soundManager = new SoundManager();
    public Collisions(){

    }

    /**
     * This function detects whether a tank has collided with another tank
     * if a tank collides with another tank, we want to push them back
     * @param t1 Tank one
     * @param t2 Tank two
     */
    public void checkCollisions(Tank t1, Tank t2){
        hbTank1 = t1.getHitBox().hitbox;
        hbTank2 = t2.getHitBox().hitbox;

        if(hbTank1.intersects(hbTank2)){
            t1.setX(t1.getX() - t1.getVx());
            t1.setY(t1.getY()-t1.getVy());

        }

        if(hbTank2.intersects(hbTank1)){
            t2.setX(t2.getX() - t2.getVx());
            t2.setY(t2.getY()-t2.getVy());
        }

    }

    /**
     * this function detects whether a bullet hits either tank
     * if a bullet hits a tank, we destroy the bullet and do damage to the tank
     * @param bullet tank bullet
     * @param t1 tank 1
     * @param t2 tank 2
     */
    public void checkCollisions(Bullet bullet, Tank t1, Tank t2){
        hbTank1 = t1.getHitBox().hitbox;
        hbTank2 = t2.getHitBox().hitbox;
        hbOther = bullet.getHitbox().hitbox;

        if(hbOther.intersects(hbTank1)){
            System.out.println("boom");
            bullet.setDestroy(true);
            bullet.disableHitbox();
            t1.takeDamage();
            soundManager.playSound("Sounds/Explosion_small.wav");
        }

        if(hbOther.intersects(hbTank2)){
            bullet.setDestroy(true);
            bullet.disableHitbox();
            System.out.println("boom");
            t2.takeDamage();
            soundManager.playSound("Sounds/Explosion_small.wav");
        }
    }

    public void checkCollisions(Bullet bullet, Walls wall){
        hbOther = bullet.getHitbox().hitbox;
        hbWall = wall.getHitbox().hitbox;

        if(hbOther.intersects(hbWall)){
            System.out.println("WALL HIT");
            wall.wallHit();
            bullet.setDestroy(true);
            bullet.disableHitbox();
            soundManager.playSound("Sounds/Explosion_small.wav");
        }

    }
    public void checkCollisions(Tank t1,Tank t2 ,Walls wall){
        hbTank1 = t1.getHitBox().hitbox;
        hbTank2 = t2.getHitBox().hitbox;
        hbWall = wall.getHitbox().hitbox;

        if(hbTank1.intersects(hbWall)){
            t1.setX(t1.getX() - t1.getVx());
            t1.setY(t1.getY()-t1.getVy());
            t1.setRotationLock(true);
        }

        if(hbTank2.intersects(hbWall)){
            t2.setX(t2.getX() - t2.getVx());
            t2.setY(t2.getY()-t2.getVy());
            t2.setRotationLock(true);
        }

    }

    public void checkCollisions (Tank t1, Tank t2, HealthPowerup powerup){
        hbTank1 = t1.getHitBox().hitbox;
        hbTank2 = t2.getHitBox().hitbox;
        hbOther = powerup.getHitbox().hitbox;

        if(hbTank1.intersects(hbOther)){
            powerup.activate(t1);
            powerup.destroy();
        }
        if(hbTank2.intersects(hbOther)){
            powerup.activate(t2);
            powerup.destroy();
        }
    }

}
