package tankGame;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

public class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image sprite;
    //protected Rectangle hitBox;
    Hitbox hb;

    public GameObject(Image sprite,int x, int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = sprite.getWidth(null);
        this.height = sprite.getHeight(null);
        hb = new Hitbox(this);
    }

    public int getX(){
        return this.x;
    }
    public int getY(){return this.y;}
    public Hitbox getHitbox(){return hb;}

    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public void draw(Graphics g, ImageObserver obs){
        g.drawImage(sprite,x,y,obs);
    }
}
