import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    private Character player;
    private PriorityComparator priorityComparator;
    private List<Enemy> enemyList;

    public Main(){
        player = new MainPlayer(100, 1, 100, 1.5, 10, 1.5, 10, null);
        enemyList = new ArrayList<>();
    }

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        ((MainPlayer) player).setName(name);
        System.out.println("This is the story of " + name);
        ((MainPlayer) player).addAttack(new Attack("Regular", 4, 1.00));
        ((MainPlayer) player).addAttack(new Attack("Regular2", 1, 0.5));

        //System.out.println("Choose your attacks! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
        Deque<Character> encounter = generateEncounter();
        
        while(player.getHealth()>0){
            Character currActor = encounter.poll();
            //System.out.println(currActor.getClass());
            if(currActor.getClass().equals(player.getClass())){
                playerTurn(response);
            }   
            else{
                enemyTurn(currActor);
            }     
            encounter.offer(currActor);
        }
        System.out.println("You died! Game over!");
    }

    public void playerTurn(Scanner input){
        for(Enemy enemy: enemyList){
            System.out.println(enemy.getName() +" has "+ enemy.getHealth() + " health");
        }
        int numResponse = 3;
        while(numResponse>2 || numResponse<0){
            System.out.println("Choose your action! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
            numResponse = input.nextInt();
            if(numResponse>2 || numResponse<0){
                System.out.println("Please enter a valid number");
            }
        }
        if(numResponse==1){
            int order = 1;
            List<Attack> attackList = ((MainPlayer) player).getAttacks(); 
            System.out.println("Choose your attack!");
            for(Attack attack:attackList){
            System.out.println(order+" "+attack.getName());
            order++;
            }
            int attackResponse = 3; //one greater than the number of available attacks, get list length
            while(attackResponse>2 || attackResponse<0){
                attackResponse = input.nextInt();
                if(numResponse>2 || numResponse<0){
                    System.out.println("Please enter a valid number");
                }
            }
            if(attackResponse==1){
                System.out.println("Choose which enemy you would like to attack:");
                int enemyNum = 1;
                for(Enemy enemy: enemyList){
                    System.out.println(enemyNum + " " + enemy.getName() +" has "+ enemy.getHealth() + " health");
                    enemyNum++;
                }
                int enemyResponse = enemyList.size()+1;
                while(enemyResponse>enemyList.size() || enemyResponse<0){
                    enemyResponse = input.nextInt();
                    if(enemyResponse>enemyList.size() || numResponse<0){
                        System.out.println("Please enter a valid number");
                    }
                }
                player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1));
            }
            else{
                player.useAttack(enemyList.get(0), attackList.get(attackResponse-1));
            }
        }
        else if(numResponse==2){
            int order = 1;
            List<Spell> spellList = ((MainPlayer) player).getSpells();
            for(Spell spell:spellList){
                System.out.println(order+" "+spell);
                order++;
            }
        }
    }

    public void enemyTurn(Character enemy){
        System.out.println("Enemy " + enemy.getName() + " attacks!");
        ((Enemy)enemy).attack(player);
    }

    public Deque<Character> generateEncounter(){
        Deque<Character> queue = new ArrayDeque<Character>();; // 
        //we are NOT using a priority queue, just make a list of all the characters and then sort it (either Comparable or Comparator),
        //then add the list to a normal queue
        Character enemy = new Enemy("test",10, 0, 0, 9, 0, 0);
        enemyList.add((Enemy)enemy);
        queue.add(enemy);
        queue.add(player);
        return queue;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
