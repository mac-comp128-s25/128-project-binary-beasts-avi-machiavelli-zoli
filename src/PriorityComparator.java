import java.util.Comparator;

public class PriorityComparator implements Comparator<Character>{

    @Override
    public int compare(Character character, Character target) {
        return Double.compare(character.getPriority(), target.getPriority());
    }
    
}
