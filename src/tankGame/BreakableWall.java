package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class BreakableWall extends Walls  {
    int x;
    int y;
    int width;
    int height;
    int hp;
    Image sprite;
    Hitbox hb;
    boolean destroy;
    public BreakableWall(Image sprite, int x, int y){
        super(sprite,x,y);
        this.x = x;
        this.y = y;
        hp = 1;
        destroy = false;

        this.sprite = sprite;
        this.width = sprite.getWidth(null);
        this.height = sprite.getHeight(null);

        hb = new Hitbox(this);

    }
    public Hitbox getHitbox(){return hb;}
    public void wallHit(){
        hp--;
        if(hp < 1){
            destroy = true;
            hb.disableHitbox();
        }
    }

    public void draw(Graphics g, ImageObserver obs){
        if(!destroy){
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.sprite,x,y,obs);

        }
    }
}
