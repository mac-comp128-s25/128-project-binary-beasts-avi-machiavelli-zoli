/**
 * Class for the Attacks used in the game.
 */
public class Attack implements Skill{
    private String name;
    private double damage;
    private double chanceToHit;
    
    /**
     * Constructor for the Attack object
     * @param name 
     * @param damage
     * @param chanceToHit
     */
    public Attack(String name, double damage, double chanceToHit){
        this.name = name;
        this.damage = damage;
        this.chanceToHit = chanceToHit;
    }

    /**
     * Accessor for the attack's damage
     * @return damage
     */
    public double getDamage(){
        return damage;
    }

    /**
     * Accessor for the attack's chance to hit
     * @return chance to hit
     */
    public double getChanceToHit(){
        return chanceToHit;
    }

    /**
     * Accessor for the attack's name
     * @return name
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     * Accessor for the attack's bonus type--not applicable
     * @return false
     */
    @Override
    public boolean bonusType() {
        return false;
    }
}
