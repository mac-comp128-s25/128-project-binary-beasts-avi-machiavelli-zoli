import java.util.Comparator;

/**
 * Priority Comparator for the encounter Queue
 */
public class PriorityComparator implements Comparator<Character>{

    /**
     * Overrides the Comparator compare method to compare the priority of two Characters
     */
    @Override
    public int compare(Character character, Character target) {
        return Double.compare(character.getPriority(), target.getPriority());
    }
    
}
