package tankrotationexample;

import java.awt.Rectangle;

public class Collisions {

    public Collisions(){
        //idk what to put here
    }

    public void TankvTank(Tank p1, Tank p2){
        Rectangle p1HitBox = p1.getHitBox();
        Rectangle p2HitBox = p2.getHitBox();

        if(p1HitBox.intersects(p2HitBox)){
            System.out.println("There is a collision here");
        }
    }
}
