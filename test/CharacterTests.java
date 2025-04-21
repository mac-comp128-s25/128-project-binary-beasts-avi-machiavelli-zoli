import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTests {
    private MainPlayer player;
    private Enemy enemy;

    @BeforeEach
    public void setup(){
        player = new MainPlayer(100, 100, "nevermiss", 0, 0, 0, 1.00, 40);
        enemy = new Enemy(100.0, 0.0,  0.0, 0.0, 1.0, 30);
    }


    @Test
    public void testAttackHits(){
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 60);
        assertEquals(player.getHealth(), 70);
    }

    @Test
    public void testAttackMiss(){
        player = new MainPlayer(100, 100, "alwaysmiss", 0, 0, 0, 0.00, 40);
        enemy = new Enemy(100.0, 0.0,  0.0, 0.0, 0.0, 30);
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 100);
        assertEquals(player.getHealth(), 100);
    }

    @Test
    public void testCritAttack(){
        player = new MainPlayer(100, 100, "alwayscrit", 1.5, 1, 0, 1.00, 40);
        enemy = new Enemy(100.0, 1.5,  1.0, 0.0, 1.0, 30);
        player.attack(enemy);
        enemy.attack(player);
        assertEquals(enemy.getHealth(), 40);
        assertEquals(player.getHealth(), 55);
    }
    
}
