package tankGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.imageio.ImageIO.read;

public class GameManager extends JPanel {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int WORLD_WIDTH = 2010;
    public static final int WORLD_HEIGHT = 2010;
    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame jFrame;
    private Tank tankOne;
    private Tank tankTwo;
    public static ArrayList<Bullet> bulletList;
    private  Collisions collisions;
    private SoundManager soundManager;
    BufferedImage worldImgTest;
    ArrayList<Walls> walls;
    ArrayList<HealthPowerup> powerups;
    private boolean gameOver = false;
    private boolean running = true;

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
                        for(int j=0; j < gameManager.walls.size(); j++){
                            gameManager.collisions.checkCollisions(bulletList.get(i), gameManager.walls.get(j));
                        }
                    }
                }
                gameManager.collisions.checkCollisions(gameManager.tankOne, gameManager.tankTwo);

                for(int j=0; j < gameManager.walls.size(); j++){
                    gameManager.collisions.checkCollisions(gameManager.tankOne, gameManager.tankTwo,gameManager.walls.get(j));
                }

                for(int i = 0; i < gameManager.powerups.size(); i++){
                    gameManager.collisions.checkCollisions(gameManager.tankOne,gameManager.tankTwo, gameManager.powerups.get(i));
                }

                gameManager.repaint();

                if(gameManager.tankOne.getDestroyed() == true || gameManager.tankTwo.getDestroyed() == true){
                    gameManager.gameOver = true;
                    if(gameManager.tankOne.getEndPressed()|| gameManager.tankTwo.getEndPressed()){
                        System.exit(1);
                    }

                }
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException | IOException ignored){
            System.out.println(ignored);
        }
    }

    private void init() {
        this.jFrame = new JFrame("Tank Game");

        this.world = new BufferedImage(GameManager.WORLD_WIDTH, GameManager.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage tankImage = null;
        BufferedImage tankImage2 = null;
        BufferedImage breakWall = null;
        BufferedImage unBreakWall = null;
        BufferedImage powerup = null;
        bulletList = new ArrayList<Bullet>();
        collisions = new Collisions();
        worldImgTest = null;
        walls = new ArrayList<Walls>();
        powerups = new ArrayList<HealthPowerup>();
        soundManager = new SoundManager();
        soundManager.playSound("Sounds/Music.wav");


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
            tankImage2 = read(GameManager.class.getClassLoader().getResource("tank2.png"));
            unBreakWall = read(GameManager.class.getClassLoader().getResource("unBreakableWall.gif"));
            breakWall = read(GameManager.class.getClassLoader().getResource("BreakableWall2.gif"));
            worldImgTest = read(GameManager.class.getClassLoader().getResource("bg.png"));
            powerup = read(GameManager.class.getClassLoader().getResource("Shield2.png"));



            InputStreamReader isr = new InputStreamReader(GameManager.class.getClassLoader().getResourceAsStream("maps/map1"));
            BufferedReader mapReader = new BufferedReader(isr);

            String row = mapReader.readLine();
            if(row == null){
                throw new IOException("no new data on ifle");
            }
            String[] mapInfo = row.split("\t");
            int numCols = Integer.parseInt(mapInfo[0]);
            int numRows = Integer.parseInt(mapInfo[1]);

            for(int currRow = 0; currRow < numRows; currRow++){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int currCol = 0; currCol < numCols; currCol++){
                    switch (mapInfo[currCol]){
                        case "2":
                            BreakableWall br = new BreakableWall(breakWall,currCol*30,currRow*30);
                            this.walls.add(br);
                            break;
                        case "3":
                            HealthPowerup pu = new HealthPowerup(powerup,currCol*30,currRow*30);
                            this.powerups.add(pu);
                            break;
                        case "9":
                            UnbreakableWall ubr = new UnbreakableWall(unBreakWall,currCol*30,currRow*30);
                            this.walls.add(ubr);
                            break;
                    }
                }
            }



            //Using file objects to read in resources.
            //tankImage = read(new File("tank1.png"));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //TODO: add tank initializer here
        tankOne = new Tank(300, 500, 0, 0, 0, tankImage,this);
        tankTwo = new Tank(1500, 500, 0, 0, 180, tankImage2,this);


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

        Font font = new Font("Verdana",Font.BOLD,250);


        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(font);
        super.paintComponent(g2);
        buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
        buffer.drawImage(worldImgTest,0,0,null);
        this.walls.forEach(wall -> wall.draw(buffer,null));
        this.powerups.forEach(HealthPowerup -> HealthPowerup.draw(buffer,null));

        if(this.bulletList.size() > 0){
            for (int i = 0; i < this.bulletList.size(); i++){
                this.bulletList.get(i).draw(buffer,null);
            }
        }
        this.tankOne.drawImage(buffer);
        this.tankOne.getHealthBar().draw(buffer,null);
        this.tankTwo.drawImage(buffer);
        this.tankTwo.getHealthBar().draw(buffer,null);

        BufferedImage leftHalf =  world.getSubimage(this.tankOne.getCamerax() ,this.tankOne.getCameray()  ,GameManager.SCREEN_WIDTH/2, GameManager.SCREEN_HEIGHT);
        BufferedImage rightHalf =  world.getSubimage(this.tankTwo.getCamerax() ,this.tankTwo.getCameray(),GameManager.SCREEN_WIDTH/2, GameManager.SCREEN_HEIGHT);
        BufferedImage miniMap = world.getSubimage(0 ,0,GameManager.WORLD_WIDTH ,GameManager.WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameManager.SCREEN_WIDTH/2,0,null);
        g2.scale(.10,.10);
        g2.drawImage(miniMap,SCREEN_WIDTH  * 4 + (SCREEN_WIDTH / 4),SCREEN_HEIGHT,null);
        g2.drawString("PLAYER 1 LIVES: " + this.tankOne.getNumberofLivesRemaining() , SCREEN_WIDTH,800);
        g2.drawString("PLAYER 2 LIVES: " + this.tankTwo.getNumberofLivesRemaining() , SCREEN_WIDTH * 7,800);
        if(gameOver){
            Font endfont = new Font("Verdana",Font.BOLD,500);
            g2.setFont(endfont);
            g2.drawString("GAME OVER", SCREEN_WIDTH * 4, SCREEN_HEIGHT * 4);
        }

    }

}
