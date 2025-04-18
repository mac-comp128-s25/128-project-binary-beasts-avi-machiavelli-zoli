import java.util.Random;
import java.util.random.*;

public class Enemy implements Character{
    private double health;
    private double critMultiplier;
    private double critChance;
    private int priority;
    private double chanceToHit;
    private double attackDamage;

    public Enemy(double health, double critMultiplier, double critChance, int priority, double chanceToHit, double attackDamage){
        this.health = health;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.chanceToHit = chanceToHit;
        this.attackDamage = attackDamage;
    }

    public double getHealth(){
        return health;
    }

    public void setHealth(double newHealth){
        health = newHealth;
    }

    public boolean attack(Character ){
        if(checkIfHit(chanceToHit)){
            // here do the actual setting of health and stuff

            return true;
        }
        else {
            System.out.println("The attack missed!"); 
            return false;
        }

    }


    // method decomp to check if an attack hits or not
    private boolean checkIfHit(double chanceToHit){
        Random rand = new Random();
        double randomDouble = rand.nextDouble();
        if(chanceToHit >= randomDouble){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean attack(Character target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public int compareTo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    @Override
    public boolean equals() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'equals'");
    }
}
