package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class HealthBar extends GameObject {

    Image sprite;
    int x;
    int y;
    Image healthFullImage;
    Image health2Image;
    Image health1Image;
    Image healthNoImage;
    Image healthShieldFullImage;
    Image healthShieldHalfImage;
    public HealthBar(Image sprite,int x, int y){
        super(sprite, x, y);

        try{
            healthFullImage = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_full.png"));
            health2Image = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_2.png"));
            health1Image = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_1.png"));
            healthNoImage = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_no.png"));
            healthShieldFullImage = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_shield_full.png"));
            healthShieldHalfImage = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_shield_half.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "resource not found");
        }
        this.sprite = healthFullImage;
    }

    void updateSprite(int noBars, Tank obj){
        switch (noBars){
            case 5: {
                this.sprite = healthShieldFullImage;
                break;
            }
            case 4: {
                this.sprite = healthShieldHalfImage;
                break;
            }
            case 3: {
                this.sprite = healthFullImage;
                break;
            }
            case 2: {
                this.sprite = health2Image;
                break;
            }
            case 1: {
                this.sprite = health1Image;
                break;
            }
            default:{
                this.sprite = healthNoImage;
            }
        }

        this.x = obj.getX();
        this.y = obj.getY() - 50;
    }

    public void draw(Graphics g, ImageObserver obs){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, this.x, this.y ,obs);
    }
}
