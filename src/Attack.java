public class Attack implements Skill{
    private String name;
    private double damage;
    private double chanceToHit;
    

    public Attack(String name, double damage, double chanceToHit){
        this.name = name;
        this.damage = damage;
        this.chanceToHit = chanceToHit;
    }

    public double getDamage(){
        return damage;
    }

    public double getChanceToHit(){
        return chanceToHit;
    }

    public String getName(){
        return name;
    }
}
