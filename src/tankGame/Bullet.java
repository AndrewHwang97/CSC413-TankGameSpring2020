package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.awt.geom.AffineTransform;

import static javax.imageio.ImageIO.read;
public class Bullet extends GameObject{

    private int xSpeed;
    private int ySpeed;
    private int angle;

    boolean destroy = false;

    public Bullet(Image sprite,int x, int y, int xSpeed, int ySpeed,int angle)  {
        super(sprite, x, y);

        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
    }

    public void update(int w, int h){
        if(y < h-88 && y > 0 && x > 0 && x < w-88 && destroy == false){
            x = x + xSpeed;
            y = y + ySpeed;
            //System.out.println("Bullet x: " + x + "Bullet y: " + y);
        }
        else{
            this.destroy = true;
        }
    }

    public void draw(Graphics g, ImageObserver obs){
        if(!destroy){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.sprite.getWidth(null) / 2.0, this.sprite.getHeight(null) / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.sprite,rotation,obs);

        }
    }
}
