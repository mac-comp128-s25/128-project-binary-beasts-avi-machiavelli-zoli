import org.junit.jupiter.api.BeforeEach;

public class CharacterTests {
    private MainPlayer player;
    private Enemy enemy;

    @BeforeEach
    public void setup(){
        player = new MainPlayer(100, 100, "testplayer", 0, 0, 0, 0, 0);
    }
}
