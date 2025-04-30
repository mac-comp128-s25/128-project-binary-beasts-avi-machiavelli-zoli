import java.util.ArrayList;
import java.util.List;

public class SkillNode {

    private List<Attack> attackList;
    private ArrayList<Spell> spellList;
    
    public SkillNode(List<Attack> attacks){
        this.attackList = attacks;
    }

    public SkillNode(ArrayList<Spell> spells) {
        this.spellList = spells;
    }

    public List<Attack> getAttacks(){
        return attackList;
    }

    public List<Spell> getSpells(){
        return spellList;
    }

}
