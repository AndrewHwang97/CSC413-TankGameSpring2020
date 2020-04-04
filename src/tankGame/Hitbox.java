package tankGame;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hitbox {
    int x;
    int y;
    Rectangle hitbox;
    GameObject obj;

    Hitbox(GameObject obj){
        this.x = obj.x;
        this.y = obj.y;
        hitbox = new Rectangle(x,y,obj.sprite.getWidth(null),obj.sprite.getHeight(null));
    }

    Hitbox(Tank obj, BufferedImage image){
        this.x = obj.getX();
        this.y = obj.getY();
        hitbox = new Rectangle(x,y,image.getWidth(),image.getHeight());
    }

    void update(Tank obj){
        this.hitbox.x = obj.getX();
        this.hitbox.y = obj.getY();
    }

    void update(GameObject obj){
        this.hitbox.x = obj.x;
        this.hitbox.y = obj.y;
    }

    public void disableHitbox(){
        this.hitbox.width = 0;
        this.hitbox.height = 0;
    }
}
