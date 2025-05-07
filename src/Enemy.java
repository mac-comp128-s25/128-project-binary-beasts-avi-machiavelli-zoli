import java.util.Random;

/**
 * Class for the Enemies in the game.
 */
public class Enemy extends Character {
    private double health;
    private double critMultiplier;
    private double critChance;
    private double priority;
    private double hitChance;
    private double attackDamage;
    private String name;
    private boolean dead;

    /**
     * Constructor for the Enemy object
     * 
     * @param name
     * @param health
     * @param critMultiplier
     * @param critChance
     * @param priority
     * @param hitChance
     * @param attackDamage
     */
    public Enemy(String name, double health, double critMultiplier, double critChance, double priority,
        double hitChance, double attackDamage) {
        this.name = name;
        this.health = health;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.hitChance = hitChance;
        this.attackDamage = attackDamage;
        this.dead = false;
    }

    /**
     * Accessor method for the enemy's health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Mutator method for the enemy's health
     * 
     * @param newHealth
     */
    public void setHealth(double newHealth) {
        health = newHealth;
    }

    /**
     * Accessor method for the enemy's name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the enemy's status
     * 
     * @return whether the enemy is dead (true) or alive (false)
     */
    public boolean getDead() {
        return dead;
    }

    /**
     * Mutator method for the enemy's dead/alive status
     * 
     * @param status
     */
    public void setDead(boolean status) {
        dead = status;
    }

    /**
     * Method for the enemy to attack a target
     * 
     * @param target the Character to be targeted
     * @return the success of the attack, whether it hits/misses
     */
    public boolean attack(Character target) {
        Random rand = new Random();
        double hitDouble = rand.nextDouble(); // creates random double between 0 and 1 to check whether the attack lands
        if (hitChance > hitDouble) { // if the attack lands
            double critDouble = rand.nextDouble(); // new random double in order to check whether crit or not
            double damageDone = attackDamage; // variable that tracks the total damage we will do. will be increased if
                                              // crit, will stay same if not
            if (critChance > critDouble) {
                damageDone *= critMultiplier;
                System.out.println("Critical Hit!");
            }
            target.setHealth(target.getHealth() - damageDone);
            System.out.println("The attack did " + damageDone + " damage!");
            if (target.getHealth() < 0) {
                System.out.println(target.getName() + " was defeated.");
            }
            return true;
        } else {
            System.out.println("The attack missed!");
            return false;
        }
    }

    /**
     * Accessor method for the enemy's priority
     * 
     * @return priority
     */
    public double getPriority() {
        return priority;
    }

    /**
     * Overriden compareTo method. Compares two characters based on their priority
     */
    @Override
    public int compareTo(Character target) {
        return Double.compare(this.getPriority(), target.getPriority());
    }

    /**
     * Method for the enemy to attack a target with a given attack.
     * 
     * @param target the Character to be targeted by the attack
     * @param attack the Attack that the enemy uses
     */
    @Override
    public boolean useAttack(Character target, Attack attack) {
        Random rand = new Random();
        double hitDouble = rand.nextDouble(); // creates random double between 0 and 1 to check whether the attack lands
        if (attack.getChanceToHit() > hitDouble) { // if the attack lands
            double critDouble = rand.nextDouble(); // new random double in order to check whether crit or not
            double damageDone = attack.getDamage(); // variable that tracks the total damage we will do. will be
                                                    // increased if crit, will stay same if not
            if (critChance > critDouble) {
                damageDone *= critMultiplier;
                System.out.println("Critical Hit!");
            }
            target.setHealth(target.getHealth() - damageDone);
            System.out.println("The enemy attack did " + damageDone + " damage!");
            if (target.getHealth() < 0) {
                System.out.println("The target was defeated.");
                return true;
            }
        } else {
            System.out.println("The attack missed!");
        }
        return false;
    }
}
