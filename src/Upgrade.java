/**
 * Class for the Upgrades in the game.
 */
public class Upgrade implements Skill{

    private String name;
    private boolean type; //true if health bonus, false if crit bonus
    
    /**
     * Constructor for the Upgrade object
     * @param name
     * @param type
     */
    public Upgrade(String name, boolean type){
        this.name = name;
        this.type = type;
    }

    /**
     * Accessor method for the Upgrade's name
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Accessor method for the Upgrade's bonus type--true if a health bonus, false if a crit bonus
     * @return bonusType
     */
    @Override
    public boolean bonusType(){
        return type;
    }

}
