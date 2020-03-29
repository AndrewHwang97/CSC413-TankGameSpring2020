package tankrotationexample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Tank {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private final int R = 2;
    private final int ROTATION_SPEED = 4;


    private BufferedImage tankImage;
    private BufferedImage bulletImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;


    public ArrayList<Bullet> bulletList;
    //HitBox hb;
    Rectangle hitBox;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImage,TankRotationExample game) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.tankImage = tankImage;
        this.angle = angle;
        this.bulletList = game.bulletList;
        //this.hb = new HitBox(this);  //creates a hitbox object around the tank;
        hitBox = new Rectangle(this.x,this.y,this.tankImage.getWidth(), this.tankImage.getHeight());
        try{
            bulletImage = ImageIO.read(Tank.class.getClassLoader().getResource("Rocket.gif"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "resource not found");
        }

    }

    public int getX(){return x;}
    public int getY(){return  y;}
    public int getWidth(){return this.tankImage.getWidth();}
    public int getHeight(){return this.tankImage.getHeight();}

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed(){this.shootPressed = true;}

    void untoggleShootPressed(){this.shootPressed = false;}

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }



    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.shootPressed) {
            this.fire();
            untoggleShootPressed();
        }
        //if the tank shoots, update each bullet
        if(bulletList.size() > 0){
            for (int i = 0; i < bulletList.size(); i++){
                bulletList.get(i).update(TankRotationExample.SCREEN_WIDTH, TankRotationExample.SCREEN_HEIGHT);
                bulletList.get(i).collision(this);
            }
        }
        updateHitBox();
        //System.out.println("tankx: " + this.x + "tanky: " + this.y + "hbx: " + hitBox.x + "hby: " + hitBox.y);



    }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    //fires the bullet. adds the bullet to the arraylist
    //TODO: the bullet only shoots in x direction, find a way to manage direction for shooting
    //
    private void fire(){
        Bullet bullet = new Bullet(bulletImage,x + 80 ,y,1,0);
        bulletList.add(bullet);
    }

    public void updateHitBox(){
        hitBox.x = this.x;
        hitBox.y = this.y;
    }
    public Rectangle getHitBox(){
        return hitBox;
    }



    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TankRotationExample.SCREEN_WIDTH - 88) {
            x = TankRotationExample.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TankRotationExample.SCREEN_HEIGHT - 80) {
            y = TankRotationExample.SCREEN_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImage, rotation, null);
    }



}
