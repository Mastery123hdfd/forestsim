import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Tree{
    private Random rand = new Random();
    private double x,y,angle,trunkL,trunkW;
    private double[] prefs;
    private int branch=1;
    public static int Season = 0;
    private static double BLUEPOWER=23;
    private static double REDPOWER=21;
    private static double GREENPOWER=6;
    private static double REPRODUCTIVELIMIT=90000;
    private static double BIOMASSPOOL=180;
    private static double LEAFCOST=9;
    private static double GROWTHLIMIT=30;
    private static final int MAXIMUM=45;
    private double surv=10;
    private Color leaf;
    private ArrayList<double[]> leaved = new ArrayList<double[]>();
    public static double getRL(){return REPRODUCTIVELIMIT;}
    public static double getBP(){return BIOMASSPOOL;}
    public static double getLF(){return LEAFCOST;}
    public static double getGL(){return GROWTHLIMIT;}
    public static double getMX(){return MAXIMUM;}
    public static void changeRL(double ch){REPRODUCTIVELIMIT+=ch;}
    public static void changeBP(double ch){BIOMASSPOOL+=ch;}
    public static void changeLF(double ch){LEAFCOST+=ch;}
    public static void changeGL(double ch){GROWTHLIMIT+=ch;}
    private Tortoise tort;
    private double biomass;
    public double getX(){return x;}
    public double getY(){return y;}
    public double getAngle(){return angle;}
    public double getLen(){return trunkL;}
    public double getWid(){return trunkW;}
    public double getBiomass(){return biomass;}
    public int getDepth(){return branch;}
    private TreeManager tm;
    public TreeManager getTM(){return tm;}

    private ArrayList<double[]> points = new ArrayList<double[]>();
    public ArrayList<double[]> getPoints(){return points;}
    public void addPoint(double x, double y,double angle, double leafed, double treeL, double treeRad, int depth){
        points.add(new double[]{x,y,angle,leafed,treeL,treeRad,depth});
        System.out.println("Add Point: \nx: " + x +"\ny: "+ y + "\nangle: " + angle + "\nleafed: " + leafed +"\ntreeL: "+ treeL+"\ntreeRad: " + treeRad+"\nDpeth: "+depth);
    }
    public Tree(double x, double y, double angle, TreeManager tm, Tortoise tort, double[] prefs) {
        this.tort = tort;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.prefs = prefs;
        this.trunkL = prefs[5];
        this.trunkW = prefs[6];
        this.tm = tm;
        this.leaf = new Color((int)prefs[4]);
        this.addPoint(x,y,angle,0,trunkL,trunkW,1);
        biomass = trunkL*trunkW;
    }
    public Tree(double x, double y, double angle, TreeManager tm, Tortoise tort) {
        this(x,y,angle,tm,tort,new double[]{25,25,30,30, new Color(57, 160, 57).getRGB(),100,10,10000});
        this.surv = 120;
    }

    public Tree offSpring() {
        int dx = rand.nextInt(-50,50);
        double dthet = rand.nextDouble(-8,8);
        double[] newprefs = prefs.clone();
        //Mutation'
        double d1 = 2*rand.nextDouble(-prefs[0],(prefs[0]>50 ? 100-prefs[0] : 25))/prefs[0];
        double d2 = 2*rand.nextDouble(-prefs[1],(prefs[1]>50 ? 100-prefs[1] : 25))/prefs[1];
        double d3 = 2*rand.nextDouble(-prefs[2],(prefs[2]>50 ? 100-prefs[2] : 25))/prefs[2];
        double d4 = 2*rand.nextDouble(-prefs[3],(prefs[3]>50 ? 100-prefs[3] : 25))/prefs[3];

        Color prefLeaf = new Color((int)prefs[4]);
        int dr = rand.nextInt(-prefLeaf.getRed(),(prefLeaf.getRed()>50 ? prefLeaf.getRed() : 25))/(prefLeaf.getRed()+1);
        int db = rand.nextInt(-prefLeaf.getBlue(),(prefLeaf.getBlue()>50 ? prefLeaf.getBlue() : 25))/(prefLeaf.getBlue()+1);
        int dg = rand.nextInt(-prefLeaf.getGreen(),(prefLeaf.getGreen() > 50 ? prefLeaf.getGreen() : 25))/(prefLeaf.getGreen()+1);
        Color newLeaf = new Color(prefLeaf.getRed()+dr,prefLeaf.getGreen()+dg,prefLeaf.getBlue()+db);
        double newColor = newLeaf.getRGB();

        double lenMut = rand.nextDouble(0.9,1.1);
        double radMut = rand.nextDouble(0.96,1.06);

        newprefs[0]+=d1;
        newprefs[1]+=d2;
        newprefs[2]+=d3;
        newprefs[3]+=d4;
        newprefs[4]=newColor;
        newprefs[5]*=lenMut;
        newprefs[6]*=radMut;

        return new Tree(x+dx,y,angle+dthet,tm,tort,newprefs);
    }
    public void grow(int time, Double nutrient){
        calcSurv(time,nutrient);
        System.out.println("Survey printed!");
        if(surv <= 0){
            tm.killTree(this);
            System.out.println("Tree did not survive, bau bau");
        }
        if(surv >= REPRODUCTIVELIMIT){
            System.out.println("Tree reproduced successfully");
            int offs = rand.nextInt(1,3);
            System.out.println("Tree reproduced " + offs+ " times");
            for(int i = 0; i < offs; i++){
                tm.birthTree(this);
                System.out.println("Tree birthed");
            }
        }

        if(Season == 0){
            System.out.println("Summer Season!");
            for(int i = 0; i < points.size(); i++){
                if(points.get(i)[3] != 1.0){
                    double leafval = rand.nextDouble();
                    if(leafval < prefs[0]/100){
                        points.get(i)[3] = 1.0;
                        System.out.println("Leaf removed");
                    }
                }
            }
        }
        if(Season == 1){
            System.out.println("Fall Season!");
            for(int i = 0; i < points.size(); i++){
                if(points.get(i)[3] == 1.0){
                    double leafval = rand.nextDouble();
                    if(leafval < prefs[1]/100){
                        points.get(i)[3] = 0.0;
                        System.out.println("Leaf regenerated");
                    }
                }
            }
        }
        if(Season == 2){
            System.out.println("Winter Season");
            for(int i = 0; i < points.size(); i++){
                if(points.get(i)[3] == 1.0){
                    double leafval = rand.nextDouble();
                    if(leafval < prefs[2]/100){
                        points.get(i)[3] = 0.0;
                        System.out.println("Leaf removed");
                    }
                }
            }
        }
        if(Season == 3){
            System.out.println("Spring Season!");
            for(int i = 0; i < points.size(); i++){
                if(points.get(i)[3] != 1.0){
                    double leafval = rand.nextDouble();
                    if(leafval < prefs[3]/100){
                        points.get(i)[3] = 1.0;
                        System.out.println("Leaf regenerated");
                    }
                }
            }
        }
        System.out.println("Branch: " + branch);
        for(int i = 0; i < points.size(); i++){
            double[] dp = points.get(i);
            if(biomass < nutrient+prefs[7]) {
                System.out.println("E!");
                for(double d : dp){
                    System.out.println(d);
                }
                if (dp[6] == branch) {
                    System.out.println("Branch Called!");
                    double newbiomass =tort.drawTreeBranches(dp[0], dp[1], dp[2], 2, dp[4], dp[5], this);
                    nutrient-=newbiomass;
                    biomass+=newbiomass;
                }
            }
            if(dp[3] == 1.0){
                System.out.println("Leaf Proccesing Called");
                leaved.add(dp);
            }
        }
        branch++;
        for(double[] leaves : leaved){
            System.out.println("Leaf Maker Called!");
            tort.setPenColor(leaf);
            System.out.println(leaf);
            tort.filledCircle(leaves[0],leaves[1],10);
            tort.setPenColor(Color.BLACK);
        }
        System.out.println("Tree Status Report:\nSurvival Survey: " + surv + "\nBranch: " + branch + "\nColor: " + leaf + "\nBiomass: " +this.getBiomass()+"\nWidth: " + this.getWid()+"\nLen: "+this.getLen());

    }

    /*
    * USING PREFS
    * 0 - leaf Fall; percent lost
    * 1 - leaf Winter; percent lost
    * 2 - leaf Spring; percent lost -> is
    * 3 - leaf Summer; percent lost -> is
    * 4 - leaf Color at Birth
    * 5 - trunk Length
    * 6 - trunk Width
    * 7 - nutrient buffer before growth stop
    * */



    private void calcSurv(int time, Double nutrient){
        for(double[] dp : points){
            if(dp[3] == 1){
                surv+=dp[1];
            }
        }
        System.out.println("Leaf Survival Survey calculated: " + surv);
        Season = (time/5)%4;
        System.out.println("Season calculated: " + Season);
        int Sunlight = 0;
        if(Season == 0){ //summer
            int newGreen = 0;
            if(leaf.getGreen() <180){
                newGreen = leaf.getGreen()+1;
            }
            leaf = new Color(leaf.getRed(), (newGreen >= 255 ? 255 : (newGreen < 0 ? 0 : newGreen)), leaf.getBlue());
            Sunlight = 100;
        } else if(Season == 1){ //fall
            int newGreen = 0;
            if(leaf.getGreen() > 21){
                newGreen = leaf.getGreen()-1;
            }
            leaf = new Color(leaf.getRed(), (newGreen >= 255 ? 255 : (newGreen < 0 ? 0 : newGreen)), leaf.getBlue());
            Sunlight = 25;
        } else if(Season ==2){ //winter
            int newGreen = 0;
            if(leaf.getGreen() > 10){
                newGreen = leaf.getGreen()-2;
            }
            leaf = new Color(leaf.getRed(), (newGreen >= 255 ? 255 : (newGreen < 0 ? 0 : newGreen)), leaf.getBlue());
            Sunlight = 5;
        } else if(Season ==3){ //spring
            int newGreen = 0;
            if(leaf.getGreen() <200){
                newGreen = leaf.getGreen()+2;
            }
            leaf = new Color(leaf.getRed(), (newGreen >= 255 ? 255 : (newGreen < 0 ? 0 : newGreen)), leaf.getBlue());
            Sunlight = 75;
        }

        int green = leaf.getGreen();
        int red = leaf.getRed();
        int blue = leaf.getBlue();
        int redPower = green+blue;
        int greenPower = red+blue;
        int bluePower = red+green;
        for(Tree closest : tm.getClosest(this)){
            surv-=biomass/closest.getX();
        }
        surv += (Sunlight*greenPower*GREENPOWER + Sunlight*redPower*REDPOWER + Sunlight*bluePower*BLUEPOWER)*leaved.size()/100; //Green gives power tho!
        for(double[] db : points){
            if(db[3] == 1){
                surv-=LEAFCOST;
            }
        }
        surv+=GROWTHLIMIT-BIOMASSPOOL*biomass/nutrient; //Theoretical carrying limit is 25 trees, then
    }


}
