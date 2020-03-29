package tankrotationexample;

import java.awt.Rectangle;

public class HitBox {
    int x;
    int y;
    int width;
    int height;
    GameObject obj;

    public HitBox(GameObject obj){
        this.x = obj.x;
        this.y = obj.y;
        this.width = obj.width;
        this.height = obj.height;
    }

    public HitBox(Tank obj){
        this.x = obj.getX();
        this.y = obj.getY();
        this.width = obj.getWidth();
        this.height = obj.getHeight();
    }

    public Rectangle box(){
        Rectangle rect = new Rectangle(x,y,width,height);
        return rect;
    }
}
