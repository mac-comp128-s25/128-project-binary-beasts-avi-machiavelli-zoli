public class Upgrade implements Skill{

    private String name;
    private boolean type; //true if health, false if crit bonus
    
    public Upgrade(String name, boolean type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean healthBonus(){
        return type;
    }

}
