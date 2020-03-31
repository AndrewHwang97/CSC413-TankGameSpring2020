package tankGame;

import java.awt.*;

public class Collisions {

    Rectangle hbTank1;
    Rectangle hbTank2;
    Rectangle hbOther;
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
            System.out.println("collide");
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
        }

        if(hbOther.intersects(hbTank2)){
            bullet.setDestroy(true);
            bullet.disableHitbox();
            System.out.println("boom");
            t2.takeDamage();
        }
    }

}
