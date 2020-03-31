package tankGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class GameManager extends JPanel {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private Tank tankOne;
    private Tank tankTwo;
    public static ArrayList<Bullet> bulletList;
    private  Collisions collisions;

    public static void main(String[] args){
        GameManager gameManager = new GameManager();
        gameManager.init();
        try{

            while(true){
                gameManager.tankOne.update();

                gameManager.tankTwo.update();
                if(bulletList.size() > 0){
                    for (int i = 0; i < bulletList.size(); i++){
                        bulletList.get(i).update(GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);
                        gameManager.collisions.checkCollisions(bulletList.get(i),gameManager.tankOne,gameManager.tankTwo);
                    }
                }
                gameManager.collisions.checkCollisions(gameManager.tankOne, gameManager.tankTwo);

                gameManager.repaint();
                //  System.out.println(tankExample.t1);
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException | IOException ignored){
            System.out.println(ignored);
        }
    }

    private void init() {
        this.jFrame = new JFrame("Tank Game");
        this.world = new BufferedImage(GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tankImage = null;
        bulletList = new ArrayList<Bullet>();
        collisions = new Collisions();

        try {

            /*
             * There is a subtle difference between using class
             * loaders and File objects to read in resources for your
             * tank games. First, File objects when given to input readers
             * will use your project's working directory as the base path for
             * finding the file. Class loaders will use the class path of the project
             * as the base path for finding files. For Intellij, this will be in the out
             * folder. if you expand the out folder, the expand the production folder, you
             * will find a folder that has the same name as your project. This is the folder
             * where your class path points to by default. Resources, will need to be stored
             * in here for class loaders to load resources correctly.
             *
             * Also one important thing to keep in mind, Java input readers given File objects
             * cannot be used to access file in jar files. So when building your jar, if you want
             * all files to be stored inside the jar, you'll need to class loaders and not File
             * objects.
             *
             */
            //Using class loaders to read in resources
            tankImage = read(GameManager.class.getClassLoader().getResource("tank1.png"));
            //Using file objects to read in resources.
            //tankImage = read(new File("tank1.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //TODO: add tank initializer here
        tankOne = new Tank(200, 200, 0, 0, 0, tankImage,this);
        tankTwo = new Tank(800, 200, 0, 0, 180, tankImage,this);

        //TODO: add tank controls here
        TankControl tankOneControl = new TankControl(tankOne, KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER);

        TankControl tankTwoControl = new TankControl(tankTwo, KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE);

        this.jFrame.setLayout(new BorderLayout());
        this.jFrame.add(this);
        this.jFrame.addKeyListener(tankOneControl);
        this.jFrame.addKeyListener(tankTwoControl);
        this.jFrame.setSize(GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT + 30);
        this.jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        buffer = world.createGraphics();
        buffer.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);

        if(this.bulletList.size() > 0){
            for (int i = 0; i < this.bulletList.size(); i++){
                this.bulletList.get(i).draw(buffer,null);
            }
        }
        this.tankOne.drawImage(buffer);
        this.tankOne.getHealthBar().draw(buffer,null);
        this.tankTwo.drawImage(buffer);
        this.tankTwo.getHealthBar().draw(buffer,null);

        g2.drawImage(world,0,0,null);

    }

}
