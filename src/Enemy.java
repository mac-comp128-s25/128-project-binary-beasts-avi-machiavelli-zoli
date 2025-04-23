import java.util.Random;
import java.util.random.*;

public class Enemy extends Character{
    private double health;
    private double critMultiplier;
    private double critChance;
    private double priority;
    private double hitChance;
    private double attackDamage;
    private String name;

    public Enemy(String name, double health, double critMultiplier, double critChance, double priority, double hitChance, double attackDamage){
        this.name = name;
        this.health = health;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.hitChance = hitChance;
        this.attackDamage = attackDamage;
    }

    public double getHealth(){
        return health;
    }

    public void setHealth(double newHealth){
        health = newHealth;
    }

    public String getName(){
        return name;
    }

    
    public boolean attack(Character target){
        Random rand = new Random();
        double hitDouble = rand.nextDouble(); // creates random double between 0 and 1 to check whether the attack lands
        if(hitChance > hitDouble){ // if the attack lands
            double critDouble = rand.nextDouble(); // new random double in order to check whether crit or not
            double damageDone = attackDamage; // variable that tracks the total damage we will do. will be increased if crit, will stay same if not
            if(critChance > critDouble){
                damageDone *= critMultiplier; 
                System.out.println("Critical Hit!");
            }
            target.setHealth(target.getHealth() - damageDone);
            System.out.println("Your attack did " + damageDone + "Damage!");
            if(target.getHealth() < 0){
                System.out.println(target.getName() + " was defeated.");
            }
            return true;
        }
        else {
            System.out.println("The attack missed!"); 
            return false;
        }
    }

    public double getPriority(){
        return priority;
    }

    // @Override
    // public int compareTo(Character character) {
    //     return Double.compare(this.getPriority(), character.getPriority());
    // }

    @Override
    public int compareTo(Object o) {
        return Double.compare(this.getPriority(), ((Character) o).getPriority());
    }
}
