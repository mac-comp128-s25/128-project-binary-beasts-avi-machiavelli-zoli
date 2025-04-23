import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTests {
    private MainPlayer player;
    private Enemy enemy;




    @Test
    public void testAttackHits(){
        player = new MainPlayer(100, 100, 0, 0, 0, 1.00, 40, "nevermiss");
        enemy = new Enemy("nevermiss", 100.0, 0.0,  0.0, 0.0, 1.0, 30);
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 60);
        assertEquals(player.getHealth(), 70);
    }

    @Test
    public void testAttackMiss(){
        player = new MainPlayer(100, 100, 0, 0, 0, 0.00, 40, "alwaysmiss");
        enemy = new Enemy("alwaysmiss", 100.0, 0.0,  0.0, 0.0, 0.0, 30);
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 100);
        assertEquals(player.getHealth(), 100);
    }

    @Test
    public void testCritAttack(){
        player = new MainPlayer(100, 100, 0, 1.5, 1, 1, 40, "alwayscrit");
        enemy = new Enemy("alwayscrit", 100.0, 1.5,  1.0, 0.0, 1.0, 30);
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 40);
        assertEquals(player.getHealth(), 55);
    }
    
}
