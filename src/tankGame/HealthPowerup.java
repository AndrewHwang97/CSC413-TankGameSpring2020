package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class HealthPowerup extends GameObject {
    Image sprite;
    int x;
    int y;
    protected int width;
    protected int height;
    Hitbox hb;
    boolean isDestoy;

    public HealthPowerup(Image sprite, int x, int y){
        super(sprite,x,y);
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        hb = new Hitbox(this);
        this.isDestoy = false;
    }
    public Hitbox getHitbox(){return this.hb;}

    public void activate(Tank tank){
        tank.setHp(5);
    }

    public void destroy(){
        isDestoy = true;
        hb.disableHitbox();
    }

    public void draw(Graphics g, ImageObserver obs){
        if(!isDestoy) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.sprite, this.x, this.y, obs);
        }
    }

}
