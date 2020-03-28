package tankrotationexample;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Bullet extends GameObject{
    private int xSpeed;
    private int ySpeed;
    boolean destroy = false;


    public Bullet(Image sprite, int x, int y, int xSpeed, int ySpeed){
        super(sprite,x,y);
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update(int w, int h){
        if(y < h-88 && y > 0 && x > 0 && x < w-88 && destroy == false){
            x = x + xSpeed;
            y = y + ySpeed;
            System.out.println("Bullet x: " + x + "Bullet y: " + y);
        }
        else{
            this.destroy = true;
        }
    }

    public void draw(Graphics g, ImageObserver obs){
        if(!destroy){
            g.drawImage(sprite,x,y,obs);
        }
    }
}
