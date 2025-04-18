import java.util.Random;

public class MainPlayer implements Character {

    private double health;
    private double mana;
    private String name;
    private double critMultiplier;
    private double critChance;
    private int priority;
    private double chanceToHit;
    private double attackDamage;
    private Random rand;

    public MainPlayer(int health, int mana, String name, double critMultiplier, double critChance, int priority, double chanceToHit, double attackDamage){
        this.health = health;
        this.mana = mana;
        this.name=  name;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.chanceToHit = chanceToHit;
        this.attackDamage = attackDamage;
        this.rand = new Random();
    }

    @Override
    public boolean attack(Character target) {
        if(!checkIfHit(chanceToHit)){
            return false;
        }
        else{
            
        }
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    private boolean checkIfHit(double chanceToHit){
        double randomDouble = rand.nextDouble();
        if(chanceToHit >= randomDouble){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double input) {
        health=input;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    
    
}
