public class Spell implements Skill{
    private String name;
    private int manaCost;
    private boolean targetAll; 
    private double damage;

    public Spell(String name, int mana, double dmg, boolean targetAll){
        this.name = name;
        this.manaCost = mana;
        this.damage = dmg;
        this.targetAll = targetAll;
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

    @Override
    public boolean bonusType() {
        return false;
    }


}
