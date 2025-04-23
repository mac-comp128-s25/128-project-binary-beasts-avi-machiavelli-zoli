public abstract class Character implements Comparable{
    
    public abstract boolean attack(Character target);

    public abstract double getHealth();

    public abstract void setHealth(double num);

    public abstract double getPriority();

    public abstract String getName();

    public abstract int compareTo(Object o);
    
    
}
