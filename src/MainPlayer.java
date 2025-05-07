import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for the Main Player of the game.
 */
public class MainPlayer extends Character {

    private double health;
    private int mana;
    private String name;
    private double critMultiplier;
    private double critChance;
    private int priority;
    private double hitChance;
    private double attackDamage;
    private Random rand;
    List<Spell> spellList;
    List<Attack> attackList;
    List<Skill> skillList;

    /**
     * Constructor for the MainPlayer object
     * 
     * @param health
     * @param priority
     * @param mana
     * @param critMultiplier
     * @param critChance
     * @param hitChance
     * @param attackDamage
     * @param name
     */
    public MainPlayer(int health, int priority, int mana, double critMultiplier, double critChance, double hitChance,
        double attackDamage, String name) {
        this.health = health;
        this.mana = mana;
        this.name = name;
        this.critMultiplier = critMultiplier;
        this.critChance = critChance;
        this.priority = priority;
        this.hitChance = hitChance;
        this.attackDamage = attackDamage;
        this.rand = new Random();
        this.spellList = new ArrayList<>();
        this.attackList = new ArrayList<>();
        this.skillList = new ArrayList<>();
    }

    /**
     * Method for the player to attack a given target with a given attack
     * 
     * @param target
     * @param attack
     */
    public boolean useAttack(Character target, Attack attack) {
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
            System.out.println("Your attack did " + damageDone + " damage!");
            if (target.getHealth() <= 0) {
                System.out.println("The target was defeated.");
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("The attack missed!");
        }
        return false;
    }

    /**
     * Method for player spellcasting against a single given Enemy target with a given Spell
     * 
     * @param spell
     * @param enemy
     * @return
     */
    public boolean useSpell(Spell spell, Enemy enemy) {
        if (mana < spell.getManaCost()) {
            System.out.println("Not enough mana. Choose a different attack.");
            return false;
        }
        enemy.setHealth(enemy.getHealth() - spell.getDamage());
        System.out.println("Your attack did " + spell.getDamage() + " damage!");
        if (enemy.getHealth() <= 0) {
            System.out.println("The target was defeated.");
            return true;
        }
        return false;
    }

    /**
     * Method for player spellcasting against all Enemies with a given Spell
     * 
     * @param spell
     * @param enemies
     * @return
     */
    public boolean useSpell(Spell spell, List<Enemy> enemies) {
        boolean flag = false;
        if (mana < spell.getManaCost()) {
            System.out.println("Not enough mana. Choose a different attack.");
            return flag;
        }
        mana -= spell.getManaCost();
        for (Enemy enemy : enemies) {
            enemy.setHealth(enemy.getHealth() - spell.getDamage());
            System.out.println("Your attack did " + spell.getDamage() + " damage!");
            if (enemy.getHealth() <= 0) {
                System.out.println("The target was defeated.");
                enemy.setDead(true);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Accessor method for the player's spells
     * 
     * @return spellList
     */
    public List<Spell> getSpells() {
        return spellList;
    }

    /**
     * Mutator method for the player's spellList, adds a spell to the list
     * 
     * @param spell
     */
    public void addSpell(Spell spell) {
        spellList.add(spell);
    }

    /**
     * Accessor method for the player's attacks
     * 
     * @return attackList
     */
    public List<Attack> getAttacks() {
        return attackList;
    }

    /**
     * Mutator method for the player's attackList, adds an attack to the list
     * 
     * @param attack
     */
    public void addAttack(Attack attack) {
        attackList.add(attack);
    }

    /**
     * Accessor method for the player's health
     * 
     * @return health
     */
    @Override
    public double getHealth() {
        return health;
    }

    /**
     * Mutator method for the player's health, sets it to a given input
     * 
     * @param input
     */
    @Override
    public void setHealth(double input) {
        health = input;
    }

    /**
     * Overriden toString method, returns the player's name
     * 
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Accessor method for the player's priority
     * 
     * @return priority
     */
    @Override
    public double getPriority() {
        return priority;
    }

    /**
     * Accessor method for the player's name
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for the player's name, sets it to the given input
     * 
     * @param input
     */
    public void setName(String input) {
        name = input;
    }

    /**
     * Overriden compareTo method, compares the player's priority to that of a given target's
     */
    @Override
    public int compareTo(Character target) {
        return Double.compare(this.getPriority(), target.getPriority());
    }

    /**
     * Accessor for the player's mana
     * 
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * Mutator for the player's mana, sets it to the given input
     * 
     * @param input
     */
    public void modifyMana(int input) {
        mana += input;
    }

    /**
     * Accessor for the player's critMultiplier
     * 
     * @return critMultiplier
     */
    public double getCritMultiplier() {
        return critMultiplier;
    }

    /**
     * Accessor for the player's critMultiplier, sets it to the given input
     * 
     * @param input
     */
    public void setCritMultiplier(double input) {
        critMultiplier = input;
    }

}
