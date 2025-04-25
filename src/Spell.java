public class Spell {
    private String name;
    private int manaCost;
    private boolean targetAll; 
    private double damage;

    public Spell(String name, int mana, double dmg, boolean target){
        this.name = name;
        this.manaCost = mana;
        this.damage = dmg;
        this.targetAll = target;
    }

    public String getName(){
        return name;
    }

    public int getManaCost(){
        return manaCost;
    }

    public boolean getTargeting(){
        return targetAll;
    }

    public double getDamage(){
        return damage;
    }


}
