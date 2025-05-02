import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPlayer extends Character {

    private double health;
    private double mana;
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

    public MainPlayer(int health, int priority, int mana, double critMultiplier, double critChance, double hitChance, double attackDamage, String name){
        this.health = health;
        this.mana = mana;
        this.name=  name;
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

    public boolean useAttack(Character target, Attack attack){
        double hitDouble = rand.nextDouble(); // creates random double between 0 and 1 to check whether the attack lands
        if(attack.getChanceToHit() > hitDouble){ // if the attack lands
            double critDouble = rand.nextDouble(); // new random double in order to check whether crit or not
            double damageDone = attack.getDamage(); // variable that tracks the total damage we will do. will be increased if crit, will stay same if not
            if(critChance > critDouble){
                damageDone *= critMultiplier; 
                System.out.println("Critical Hit!");
            }
            target.setHealth(target.getHealth() - damageDone);
            System.out.println("Your attack did " + damageDone + " damage!");
            if(target.getHealth() < 0){
                System.out.println("The target was defeated.");
                return true;
            }
        }
        else {
            System.out.println("The attack missed!"); 
        }
        return false;
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

    public List<Spell> getSpells(){
        return spellList;
    }

    public void addSpell(Spell spell){
        spellList.add(spell);
    }

    public List<Attack> getAttacks(){
        return attackList;
    }

    public void addAttack(Attack attack){
        attackList.add(attack);
    }

    public List<Skill> getSkills(){
        return skillList;
    }

    public void addSkill(Skill skill){
        skillList.add(skill);
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

    public String getName(){
        return name;
    }

    public void setName(String input){
        name = input;
    }

    @Override
    public int compareTo(Character target) {
        return Double.compare(this.getPriority(), target.getPriority());
    }

    public double getMana(){
        return mana;
    }

    public void setMana(double input){
        mana = input;
    }

}
