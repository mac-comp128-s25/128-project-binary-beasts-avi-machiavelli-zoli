public class Upgrade implements Skill{

    private String name;
    private int type; //1 if health bonus, 0 if crit bonus, -1 if mana bonus
    
    public Upgrade(String name, int type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public int bonusType(){
        return type;
    }

}
