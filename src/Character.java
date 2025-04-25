public abstract class Character implements Comparable<Character>{
    
    public abstract boolean useAttack(Character target, Attack attack); //remove this

    public abstract double getHealth();

    public abstract void setHealth(double num);

    public abstract double getPriority();

    public abstract String getName();

    public abstract int compareTo(Character character);
    
    
}
