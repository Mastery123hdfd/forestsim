import java.awt.*;
import java.util.Random;
import java.util.random.*;

public class MainRunner {
    static boolean conf = false;
    static Tortoise t = new Tortoise();
    static Random r = new Random();
    public static void main(String[] args) throws InterruptedException {

        //t.drawTree(230,230,75,4,100,10);
        //t.show();



        TreeManager tm = new TreeManager(t);
        System.out.println("Tree manager successfully initialized");

        while(true){
            System.out.println("Entering LOOP");
            tm.growTree();
            System.out.println("Grow process successful");
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledRectangle(300,300,300,300);
            System.out.println("Sky Made");
            StdDraw.setPenColor(StdDraw.YELLOW);
            StdDraw.filledCircle(600,600,100);
            if(Tree.Season == 0){
                StdDraw.setPenColor(new Color(72, 169, 51));
            } else if (Tree.Season == 1){
                StdDraw.setPenColor(new Color(154, 155, 81));
            } else if(Tree.Season ==2){
                StdDraw.setPenColor(StdDraw.WHITE);
            } else if(Tree.Season == 3){
                StdDraw.setPenColor(new Color(114, 173, 107, 190));
            }
            System.out.println("Lower Grass Printed");

            StdDraw.filledRectangle(300,0,300,20);
            StdDraw.setPenColor(Color.BLACK);

            t.drawTrees(tm);

            t.show();
            System.out.println("Results displayed");
            Thread.sleep(2000);
            System.out.println("Wait complete");
            t.clear();
            tm.Check(t);
            System.out.println("Results cleared; Redoing LOOP");
            //break;
        }


        /*
        int num = 0;
        t.setPenRadius(0.1);
        t.drawTriangle(1,300,0,0);
        t.setX(300);
        t.setY(300);
        t.setAngle(90);
        while(true){
            if(num >= 8){
                num = 0;
            } else{
                double origx = t.getX();
                double origy = t.getY();
                System.out.println(num);
                t.drawSeg(num,300);
                t.turnLeft(120);
                t.drawSeg(num,300);
                t.turnLeft(120);
                t.drawSeg(num,300);
                t.turnLeft(120);
                t.show();
                num++;
                t.pause(1000);
                t.clear();
                t.setX(origx);
                t.setY(origy);
            }
        }
        /*

        t.setPenColor(StdDraw.RED);
        t.drawSTri(10,0.5);
        t.setAngle(90);
        t.setPenColor(StdDraw.GREEN);
        t.drawPlant(7,2.1);
        t.setAngle(0);
        t.setPenColor(StdDraw.BOOK_BLUE);
        t.drawDragon(15,5);
        t.setAngle(0);
        t.show();
        r.setSeed(-93481034882150835l);
        t.polygon(1,360);
        t.circle(400,300,100);
        while(true){
            drawStars(250);
            t.show();
            t.pause(1);
            conf = true;
            t.clear();
        }
        */

    }
    private static void drawStars(int stars){
        for(int i = 0; i < stars; i ++){
            t.setX(r.nextInt(50,550));
            t.setY(r.nextInt(50,550));
            t.setPenColor(new Color(r.nextInt(0,255),r.nextInt(0,255),r.nextInt(0,255),r.nextInt(0,255)));
            t.setPenRadius(r.nextInt(0,20));
            t.lineStar(r.nextInt(50,(int)(625-t.getX())),r.nextInt(0,15),r.nextInt(0,360));
        }
    }
    /*public void run(){
        while(true){
            drawStars(250);
            t.show();
            t.pause(1);
            while(!conf){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            t.clear();
            conf = false;
        }
    }*/
}
