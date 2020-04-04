package tankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;
import java.util.ArrayList;

public class Map_Standard {
    BufferedImage map;
    ArrayList<Wall> BreakableWalls;

    int BWL_Start_Pixel_X = 373;
    int BWL_Start_pixel_Y = 281;
    int BWR_Start_pixel_X = 786;
    int BWR_Start_pixel_Y = 281;

    Image wallImg;

    public Map_Standard(){
        try{
            map = ImageIO.read(Map_Standard.class.getClassLoader().getResource("BackgroundTest.png"));
            wallImg = ImageIO.read(Wall.class.getClassLoader().getResource("Wall2.png"));
        }catch (Exception e){
            System.out.println("couldnt get map");
        }
        BreakableWalls = new ArrayList<Wall>();

        for(int i = 0 ; i < 5; i++){
            BreakableWalls.add(new Wall(wallImg, BWL_Start_Pixel_X,BWL_Start_pixel_Y));
            BWL_Start_pixel_Y += 64;
        }
        for(int i = 0 ; i < 5; i++){
            BreakableWalls.add(new Wall(wallImg, BWR_Start_pixel_X,BWR_Start_pixel_Y));
            BWR_Start_pixel_Y += 64;
        }
    }

    public ArrayList<Wall> getBreakableWallsLeft(){return BreakableWalls;}

    public void draw(Graphics g, ImageObserver obs){

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.map,0,0,obs);
        for(int i = 0; i < BreakableWalls.size(); i++){
            BreakableWalls.get(i).draw(g2d,null);
        }


    }
}
