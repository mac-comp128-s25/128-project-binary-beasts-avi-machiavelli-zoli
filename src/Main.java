import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public MainPlayer player;
    public PriorityComparator priorityComparator;

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        System.out.println("This is the story of " + name);
        player = new MainPlayer(100, 100, name, 1.5, 10, 2, .5, 10);
        while(player.getHealth()>0){
            PriorityQueue<Character> encounter = generateEncounter();
            
        }
        System.out.println("You died! Game over!");
    }

    public void playerTurn(Scanner input){
        int numResponse = 3;
        while(numResponse>2 || numResponse<0){
            System.out.println("Choose your action! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
            numResponse = input.nextInt();
            if(numResponse>2 || numResponse<0){
                System.out.println("Please enter a valid number");
            }
        }
        if(numResponse==1){
            
        }
        else if(numResponse==2){
            
        }
    }

    public void enemyTurn(Enemy enemy){

    }

    public PriorityQueue<Character> generateEncounter(){
        PriorityQueue<Character> queue = new PriorityQueue<>(priorityComparator);
        Enemy enemy = new Enemy(0, 0, 0, 1, 0, 0);
        queue.add(enemy);
        queue.add(player);
        return queue;
    }

    public static void main(String[] args) {
        
    }
}
