package tankGame;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class Walls{

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image sprite;
    Hitbox hb;

    public Walls(Image sprite,int x, int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = sprite.getWidth(null);
        this.height = sprite.getHeight(null);
        hb = new Hitbox(this);
    }
    public abstract Hitbox getHitbox();
    public abstract void wallHit();
    public abstract void draw(Graphics g, ImageObserver obs);
}
