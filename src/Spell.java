/**
 * Class for the Spells in the game.
 */
public class Spell implements Skill {
    private String name;
    private int manaCost;
    private boolean targetAll;
    private double damage;

    /**
     * Constructor for the Spell object.
     * 
     * @param name
     * @param mana
     * @param dmg       damage
     * @param targetAll whether the spell targets all enemies
     */
    public Spell(String name, int mana, double dmg, boolean targetAll) {
        this.name = name;
        this.manaCost = mana;
        this.damage = dmg;
        this.targetAll = targetAll;
    }

    /**
     * Accessor method for the Spell's name
     * 
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the Spell's mana cost
     * 
     * @return manaCost
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * Accessor method for the Spell's targeting
     * 
     * @return targetAll
     */
    public boolean getTargeting() {
        return targetAll;
    }

    /**
     * Accessor method for the Spell's damage
     * 
     * @return damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Accessor method for the Spell's bonus type--not used!
     * 
     * @return false
     */
    @Override
    public boolean bonusType() {
        return false;
    }


}
