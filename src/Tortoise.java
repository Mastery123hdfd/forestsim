import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Random;
import java.util.Stack;

public class Tortoise extends Turtle{
    private Random rand = new Random();
    public Tortoise(){super();}
    public void ellipse(double centerX, double centerY, double width, double length){StdDraw.ellipse(centerX, centerY, width/2, length/2);}
    public void rect(double centerx, double centerY, double w, double l){StdDraw.rectangle(centerx,centerY,w/2,l/2);}
    public void circle(double centerx, double centery, double radius){StdDraw.circle(centerx,centery,radius);}
    public void filledCircle(double centerx, double centery, double radius){StdDraw.filledCircle(centerx,centery,radius);}
    public void polygon(int sidelength, int sidecount){
        double dtheta = 360/sidecount;
        for(int i = 0; i < sidecount; i++){
            goForward(sidelength);
            turnLeft(dtheta);
        }
    }
    public void filledStar(int sidelength, int pointcount, int sidedepth){
        double angle = (double)360/pointcount;
        //Dictionary<Integer, Integer> points = new Dictionary<Integer, Integer>();
    }
    public void clear(){StdDraw.clear();}
    public void lineStar(int sideLength, int pointcount, double turnAngle){
        turnLeft(turnAngle);
        double angle = (double)360/pointcount;
        for(int i = 0; i < pointcount; i++){
            goForward(sideLength);
            turnLeft(180);
            goForward(sideLength);
            turnLeft(angle+180);
        }
    }
    /*public char drawSTri(double len, int depth){
        if(depth != 0){
            drawSTri(len, depth-1);
        } else{

        }
    }*/
    private String genSTri(int depth){
        if(depth == 0){
            System.out.println("A");
            return "A";
        }
        String s = genSTri(depth-1);
        String ret = "";
        for(char c : s.toCharArray()){
            if(c=='A'){
                ret+="B-A-B";
            }
            else if (c== 'B'){
                ret += "A+B+A";
            }else{ret+=c;}
        }
        System.out.println(ret);
        return ret;
    }
    public void drawSTri(int depth, double len){

        setX(0);
        setY(0);
        setAngle(0);
        String sTri = genSTri((depth));
        for(char c : sTri.toCharArray()){
            pause(20);
            if(c == 'A'){
                goForward(len);
            } else if (c== '-'){
                turnLeft(-60);
            } else if (c== 'B'){
                goForward(len);
            } else if (c == '+'){
                turnLeft(60);
            }
        }
    }
    private String dragonString(int d){
        if(d==0){
            return "F";
        } else{
            String ret = "";
            String prev = dragonString(d-1);
            for(char c : prev.toCharArray()){
                if(c=='F'){
                    ret+="F+G";
                } else if (c== 'G'){
                    ret += "F-G";
                } else{
                    ret+=c;
                }
            }
            System.out.println(ret);
            return ret;
        }
    }
    public void drawDragon(int d, double len){
        String drawString = dragonString(d);
        for(char c : drawString.toCharArray()){
            pause(20);
            if(c=='F' || c =='G'){
                this.goForward(len);
            } else if(c=='+'){
                this.turnLeft(90);
            } else if(c=='-'){
                this.turnLeft(-90);
            }
        }
    }
    public void drawPlant(int d, double l){
        this.setX(25);
        this.setY(0);
        Stack<double[]> s = new Stack<double[]>();
        String gen = genPlant(d);
        for(char c : gen.toCharArray()){
            pause(10);
            if(c=='['){
                s.push(new double[]{this.getX(),this.getY(),this.getAngle()});
            }
            else if(c==']'){
                double[] db = s.pop();
                this.setX(db[0]);
                this.setY(db[1]);
                this.setAngle(db[2]);
            }
            else if(c=='F'){
                goForward(l);
            }
            else if(c=='-'){
                turnLeft(-25);
            }
            else if(c=='+'){
                turnLeft(25);
            }
        }
    }
    private String genPlant(int d){
        if(d == 0){
            return "-X";
        }
        else{
            String prev = genPlant(d-1);
            String ret = "";
            for(char c : prev.toCharArray()){
                if(c=='X'){
                    ret+="F+[[X]-X]-F[-FX]+X";
                } else if (c=='F'){
                    ret+="FF";
                } else{
                    ret+=c;
                }
            }
            return ret;
        }
    }
    public void drawSeg(int d, double l){
        if(d<=0){
            goForward(l);
        }
        else {

            this.drawSeg(d - 1, l / 3);
            this.turnLeft(-60);
            this.drawSeg(d - 1, l / 3);
            this.turnLeft(120);
            this.drawSeg(d - 1, l / 3);
            this.turnLeft(-60);
            this.drawSeg(d - 1, l / 3);

        }
    }
    public void drawTriangle(int d, float l,double x, double y){
        if(d == 0){
            setX(x);
            setY(y);
            goForward(l);
            turnLeft(120);
            goForward(l);
            turnLeft(120);
            goForward(l);
            turnLeft(120);
        }else{
            drawTriangle(d-1,l/2,x,y);
            drawTriangle(d-1,l/2,x+l/2,y);
            drawTriangle(d-1,l/2,x+l/4,y+l*Math.sqrt(3)/4);
        }
    }
    public void drawTree(double x, double y, double angle, int depth,double trunkL,double penRad, Tree t){
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
        this.setPenRadius(penRad);
        this.goForward(trunkL);
        drawTreeBranches(this.getX(),this.getY(),90,depth,rand.nextDouble(trunkL*0.1)+0.9*trunkL,penRad*0.9, t);
    }
    public void drawTreeNoTree(double x, double y, double angle, int depth, double trunkL, double penRad){
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
        this.setPenRadius(penRad);
        this.goForward(trunkL);
        drawTreeBranchesNoTree(this.getX(),this.getY(),90,depth,rand.nextDouble(trunkL*0.1)+0.9*trunkL,penRad*0.9);
    }
    public void drawTrees(TreeManager tm){
        for(Tree t : tm.getInner()){
            drawTree(t);
        }
    }
    private void drawTree(Tree tree){
        ArrayList<double[]> tips = new ArrayList<>();
        for(double[] points : tree.getPoints()){
            System.out.println("X: " + points[0] + " Y: " + points[1] + " Angle: " + points[2] + " Radius " + points[5] + " Length " + points[4]);
            setPenColor(Color.BLACK);
            setX(points[0]);
            setY(points[1]);
            setAngle(points[2]);
            setPenRadius(points[5]);
            goForward(points[4]);
            if(points[3] == 1.0) tips.add(new double[]{getX(), getY()});
        }
        setPenColor(tree.getLeaf());
        for(double[] tip : tips){
            filledCircle(tip[0], tip[1], 10);
        }
        setPenColor(Color.BLACK);
    }
    public double drawTreeBranches(double x, double y, double angle, int depth,double branchL,double penRad, Tree tree){

        System.out.println("Draw Tree Branches called!!");
        if(depth == 0){
            return 0;
        } else{
            this.setX(x);
            this.setY(y);
            this.setAngle(angle);
            this.setPenRadius(penRad);
            this.goForward(branchL);
            tree.addPoint(x, y, angle, 1, branchL, penRad, depth);
            double val = branchL*penRad*2;
            System.out.println("Draw Report: X: "+x+" Y: " + y + " Angle: " + angle+ " BranchL: " + branchL + " Radius: " + penRad);

            int branches = rand.nextInt(4);
            for(int i = 0; i < branches; i ++){
                System.out.println("Branched!!");
                double randAngle = angle+rand.nextDouble(-60,60);
                double randBranch=branchL*(rand.nextDouble(0.9,1.1));
                double randRad = penRad*(rand.nextDouble(0.6,0.9));
                val+=drawTreeBranches(this.getX(),this.getY(),randAngle,depth-1,randBranch,randRad,tree);
                this.setX(x);
                this.setY(y);
                this.setAngle(angle);
                System.out.println("X: "+x+" Y: " + y + " Angle: " + randAngle+ " BranchL: " + randBranch + " Radius: " + randRad);
            }
            return val;
        }
    }
    public double drawTreeBranchesNoTree(double x, double y, double angle, int depth,double branchL,double penRad){
        if(depth == 0){
            return 0;
        } else{
            this.setX(x);
            this.setY(y);
            this.setAngle(angle);
            this.setPenRadius(penRad);
            this.goForward(branchL);
            double val = branchL*penRad*2;

            int branches = rand.nextInt(4);
            for(int i = 0; i < branches; i ++){
                System.out.println("Branched!");
                double randAngle = angle+rand.nextDouble(-60,60);
                double randBranch = rand.nextDouble(0,6)+branchL-6;
                double randRad = penRad*(rand.nextDouble(0,0.9)+0.1);
                val+=drawTreeBranchesNoTree(this.getX(),this.getY(),randAngle,depth-1,randBranch,randRad);
                this.setX(x);
                this.setY(y);
                this.setAngle(angle);
                System.out.println("X: "+x+" Y: " + y + " Angle: " + randAngle+ " BranchL: " + randBranch + " Radius: " + randRad);
            }
            return val;
        }
    }

}