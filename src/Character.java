public interface Character{
    
    public boolean attack(Character target);

    public double getHealth();

    public void setHealth(double num);

    public double getPriority();

    // we must override the compareTo and equals methods for these objects so we can give certain characters certain priorities
    
}
