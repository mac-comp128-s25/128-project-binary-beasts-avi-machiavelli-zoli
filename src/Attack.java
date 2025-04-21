public class Attack {
    private double damage;
    private double chanceToHit;

    public Attack(double damage, double chanceToHit){
        this.damage = damage;
        this.chanceToHit = chanceToHit;
    }

    public double getDamage(){
        return damage;
    }

    public double getChanceToHit(){
        return chanceToHit;
    }
}
