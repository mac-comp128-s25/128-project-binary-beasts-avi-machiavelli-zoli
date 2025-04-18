public class Spell {
    private int manaCost;
    private boolean targetAll; 
    private double damage;

    public Spell(int mana, double dmg, boolean target){
        this.manaCost = mana;
        this.damage = dmg;
        this.targetAll = target;
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
