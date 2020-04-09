package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Tank {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;

    private int spawnX;
    private int spawnY;
    private int spawnAngle;

    private final int R = 2;
    private final int ROTATION_SPEED = 4;


    private BufferedImage tankImage;
    private BufferedImage bulletImage;
    private BufferedImage healthFullImage;
    private BufferedImage health2Image;
    private BufferedImage health1Image;
    private BufferedImage healthNoImage;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private int hp = 3;
    private boolean destroyed;
    private boolean rotationLock;

    private boolean debug;

    ArrayList<Bullet> bulletList;
    private  Hitbox hitBox;
    private HealthBar healthBar;
    private int noLives;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage tankImage, GameManager game) {

        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.spawnX = x;
        this.spawnY = y;
        this.spawnAngle = angle;
        this.tankImage = tankImage;
        this.angle = angle;
        this.destroyed = false;
        bulletList = game.bulletList;
        hitBox = new Hitbox(this, this.tankImage);
        try{

            bulletImage = ImageIO.read(Tank.class.getClassLoader().getResource("Rocket.gif"));

            healthFullImage = ImageIO.read(Tank.class.getClassLoader().getResource("Health_bar_full.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "resource not found");
        }
        healthBar = new HealthBar(healthFullImage,this.x - 50,this.y - 50);
        this.noLives = 3;
        this.rotationLock = false;

    }
    public int getX(){return x;}
    public int getY(){return  y;}
    public int getVx(){return vx;}
    public int getVy(){return vy;}
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y = y;}
    public int getWidth(){return this.tankImage.getWidth();}
    public int getHeight(){return this.tankImage.getHeight();}
    public void setRotationLock(boolean state){this.rotationLock = state;}

    public void setDebug(){this.debug = true;}
    public Hitbox getHitBox(){return this.hitBox;}

    public void takeDamage() {this.hp -= 1; System.out.println(this.hp);}

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

    BufferedImage getImage(){
        return tankImage;
    }

    public HealthBar getHealthBar(){return this.healthBar;}

    public void update() throws IOException {
        if (this.destroyed == false){
            if (this.UpPressed ) {
                this.moveForwards();
            }

            if (this.DownPressed) {
                this.moveBackwards();
                rotationLock = false;
            }



            if (this.LeftPressed && rotationLock == false) {
                this.rotateLeft();
            }
            if (this.RightPressed && rotationLock == false) {
                this.rotateRight();
            }
            if (this.shootPressed) {
                this.fire();
                untoggleShootPressed();
            }
            vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
            vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

            //if(debug)
            //System.out.println(fSpeed + "  " + bSpeed);

            hitBox.update(this);

            healthBar.updateSprite(hp, this);



            if(hp < 1){
                if(noLives < 1)
                    destroyTank();
                else
                    respawn();
            }
        }

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
    private void fire() throws IOException {
        //System.out.println(x +" " + y );
        Bullet bullet = new Bullet(bulletImage, x+vx*30 ,y+vy*30,vx*2,vy*2,angle);
        bulletList.add(bullet);
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameManager.SCREEN_WIDTH - 88) {
            x = GameManager.SCREEN_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameManager.SCREEN_HEIGHT - 80) {
            y = GameManager.SCREEN_HEIGHT - 80;
        }
    }

    public void pushBack(){
        this.x = x-vx;
        this.y = x-vy;
    }

    private void destroyTank(){
        System.out.println("destroyed");
        destroyed = true;
    }

    private void respawn(){
        System.out.println("respawned       number of lives remaining: " + noLives);
        this.hp = 3;
        this.x = spawnX;
        this.y = spawnY;
        this.angle = spawnAngle;
        noLives--;
    }

    public int getNumberofLivesRemaining(){return noLives;}
    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        g2d.drawRect(x,y,this.tankImage.getWidth(),this.tankImage.getHeight());
        g2d.drawImage(this.tankImage, rotation, null);
    }
}
