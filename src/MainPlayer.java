import java.util.List;
import java.util.Random;

public class MainPlayer implements Character {

    private double health;
    private double mana;
    private String name;
    private double critMultiplier;
    private double critChance;
    private int priority;
    private double hitChance;
    private double attackDamage;
    private Random rand;

    public MainPlayer(int health, int mana, String name, double critMultiplier, double critChance, int priority, double hitChance, double attackDamage){
        this.health = health;
        this.mana = mana;
        this.name=  name;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.hitChance = hitChance;
        this.attackDamage = attackDamage;
        this.rand = new Random();
    }

    public boolean attack(Character target){
        double hitDouble = rand.nextDouble(); // creates random double between 0 and 1 to check whether the attack lands
        if(hitChance > hitDouble){ // if the attack lands
            double critDouble = rand.nextDouble(); // new random double in order to check whether crit or not
            double damageDone = attackDamage; // variable that tracks the total damage we will do. will be increased if crit, will stay same if not
            if(critChance > critDouble){
                damageDone *= critMultiplier; 
                System.out.println("Critical Hit!");
            }
            target.setHealth(target.getHealth() - damageDone);
            System.out.println("Your attack did " + damageDone + " damage!");
            if(target.getHealth() < 0){
                System.out.println("The target was defeated.");
            }
            return true;
        }
        else {
            System.out.println("The attack missed!"); 
            return false;
        }
    }

    public void useSpell(Spell spell, Enemy enemy){
        if(mana < spell.getManaCost()){
            System.out.println("Not enough mana. Choose a different attack.");
            return;
        }
        enemy.setHealth(enemy.getHealth()-spell.getDamage());
        System.out.println("Your attack did " + spell.getDamage() + " damage!");
            if(enemy.getHealth() < 0){
                System.out.println("The target was defeated.");
            }
    }

    public void useSpell(Spell spell, List<Enemy> enemies){
        if(mana < spell.getManaCost()){
            System.out.println("Not enough mana. Choose a different attack.");
            return;
        }
        mana -= spell.getManaCost();
        for(Enemy enemy : enemies){
            enemy.setHealth(enemy.getHealth()-spell.getDamage());
            System.out.println("Your attack did " + spell.getDamage() + " damage!");
            if(enemy.getHealth() < 0){
                System.out.println("The target was defeated.");
            }
        }
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double input) {
        health = input;
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
