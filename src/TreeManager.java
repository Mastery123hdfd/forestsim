import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class TreeManager {
    private Double nutrient = initnutrient;
    private static double CHANGE = 0.1;
    private ArrayList<Tree> inner = new ArrayList<Tree>();
    public ArrayList<Tree> getInner(){return inner;}
    private double MAXIMUM = Tree.getMX();
    private double LEAFCOST = Tree.getLF();
    private double REPRODUCTIVELIMIT = Tree.getRL();
    private double GROWTHLIMIT = Tree.getGL();
    private double BIOMASSPOOL = Tree.getBP();
    private int stuckCount = 0;
    private int time;
    public static double initnutrient = 200000;
    public TreeManager(Tortoise t){
        addTree(new Tree(300,0,90,this,t));
        System.out.println("Tree Added");
    }
    public void killTree(Tree t){
        nutrient += t.getBiomass();
        inner.remove(t);
    }
    public void birthTree(Tree parentTree){
        Tree offspring = parentTree.offSpring();
        System.out.println(nutrient);
        nutrient -= offspring.getBiomass();
        inner.add(offspring);

    }
    public ArrayList<Tree> getClosest(Tree t){
        ArrayList<Tree> ret = new ArrayList<Tree>();
        for(Tree te : inner){
            if(Math.abs(te.getX()-t.getX())<=20){
                ret.add(te);
            }
        }
        return ret;
    }
    public void addTree(Tree t) {
        nutrient -= t.getBiomass();
        inner.add(t);
    }
    public void growTree(){
        ArrayList<Tree> clone = (ArrayList<Tree>) inner.clone();
        for(Tree t : clone){
            System.out.println("Grow called!");
            t.grow(time,nutrient);
        }
        time++;
        if(nutrient<=0){
            System.out.println("Nutrients consumed, everyone dies, womp womp");
            killAll();
        }
    }
    public void Check(Tortoise tort){
        System.out.println("Check Called");

        if(this.getInner().size() >= MAXIMUM){
            System.out.println("All trees have been overgrown; force restarting sim");
            this.killAll();
            Tree.changeLF(CHANGE);
            Tree.changeBP(100*CHANGE);
            Tree.changeRL(CHANGE);
            Tree.changeGL(-CHANGE);


            this.addTree(new Tree(300,0,90,this,tort));
        }
        if(this.getInner().size() == 0 || stuckCount >= 100){
            System.out.println("All trees have died");
            this.killAll();
            Tree.changeLF(-2*CHANGE);
            Tree.changeBP(-200*CHANGE);
            Tree.changeRL(-2*CHANGE);
            Tree.changeGL(+CHANGE);
            CHANGE/=10;
            this.addTree(new Tree(300,0,90,this,tort));
            stuckCount = 0;
        }
        if(this.getInner().size() <= 7){
            stuckCount++;
            System.out.println("Stagnant");
        } else{
            stuckCount = 0;
        }
        LEAFCOST = Tree.getLF();
        BIOMASSPOOL = Tree.getBP();
        REPRODUCTIVELIMIT = Tree.getRL();
        GROWTHLIMIT = Tree.getGL();
        try {
            FileWriter fw = new FileWriter("Consts.txt");
            fw.write("LEAFCOST: " + LEAFCOST +
                    " REPRODUCTIVELIMIT: " + REPRODUCTIVELIMIT +
                    " GROWTH LIMIT: " + GROWTHLIMIT +
                    " BIOMASS POOL: " + BIOMASSPOOL);
            fw.close();
            System.out.println("Consts.txt successfully written");
        } catch(IOException ioe){
            System.out.println("Failed to write to file Consts.txt");
        }
    }

    public void killAll(){
        for(int i = 0; i < inner.size();i++){
            killTree(inner.get(i));
        }
        nutrient=initnutrient;
    }

    public void consume(double amt) {nutrient -= amt;}

    public double portion() {
        double total = nutrient;
        for(Tree t : inner){
            total+=t.getBiomass();
        }
        return inner.size()>8 ? total/8 : total/inner.size();
    }
}
