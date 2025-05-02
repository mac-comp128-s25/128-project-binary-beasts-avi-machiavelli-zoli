import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    private Character player;
    private PriorityComparator priorityComparator;
    private List<Enemy> enemyList;
    private Deque<Character> encounter;

    public Main(){
        player = new MainPlayer(100, 10, 100, 1.5, 10, 1.5, 10, null);
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
        encounter = generateEncounter();
        
        while(player.getHealth()>0){
            Character currActor = encounter.poll();
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
        int numResponse = playerResponse(2, "Choose your action! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
        if(numResponse==1){
            int order = 1;
            List<Attack> attackList = ((MainPlayer) player).getAttacks(); 
            for(Attack attack:attackList){
            System.out.println(order+" "+attack.getName());
            order++;
            }
                int attackResponse = playerResponse(2, "Choose your attack!");
                int enemyResponse = chooseEnemy(input);
                player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1));
            }
        else if(numResponse==2){
            int order = 1;
            List<Spell> spellList = ((MainPlayer) player).getSpells();
            for(Spell spell:spellList){
                System.out.println(order+" "+spell);
                order++;
            }
            int spellChoice = playerResponse(spellList.size(), "Choose a spell!");
            if(spellList.get(-1).getTargeting() == true){

            }
            else{
                int target = chooseEnemy(input);
                ((MainPlayer)player).useSpell(spellList.get(spellChoice-1),enemyList.get(target-1));
            }
        }
    }

    public void enemyTurn(Character enemy){
        if(((Enemy) enemy).getDead()){
            encounter.poll();
            List<Enemy> newEnemyList = new ArrayList<Enemy>();
            for(Enemy oldEnemy: enemyList){
                if(!(oldEnemy == enemy)){
                    newEnemyList.add(oldEnemy);
                }
            }
            enemyList = newEnemyList;
        }
        else{
            System.out.println("Enemy " + enemy.getName() + " attacks!");
            ((Enemy)enemy).attack(player);
        }
    }

    public int chooseEnemy(Scanner input){
        System.out.println("Choose which enemy you would like to attack:");
        int enemyNum = 1;
        for(Enemy enemy: enemyList){
            System.out.println(enemyNum + " " + enemy.getName() +" has "+ enemy.getHealth() + " health");
            enemyNum++;
        }
        int enemyResponse = enemyList.size()+1;
        while(enemyResponse>enemyList.size() || enemyResponse<0){
            enemyResponse = input.nextInt();
            if(enemyResponse>enemyList.size() || enemyResponse<0){
                System.out.println("Please enter a valid number");
            }
        }
        return enemyResponse;
    }

    public Deque<Character> generateEncounter(){
        Deque<Character> queue = new ArrayDeque<Character>();; 
        List<Character> encounterList = new ArrayList<>();
        encounterList.add(player);

        Character enemy1 = new Enemy("test1",10, 0, 0, 9, 0, 0);
        Character enemy2 = new Enemy("test2",10, 0, 0, 9, 0, 0);
        enemyList.add((Enemy)enemy1);
        enemyList.add((Enemy)enemy2);
        queue.add(enemy1);
        encounterList.add(enemy2);

        Collections.sort(encounterList, new PriorityComparator());
        queue.addAll(encounterList);

        return queue;
    }

    public int playerResponse(int numAnswers, String preamble){
        Scanner response = new Scanner(System.in);
        int output = 0;
        while(output>numAnswers || output<1){
            System.out.println(preamble);
            output = response.nextInt();
            if(output>numAnswers || output<0){
                System.out.println("Please enter a valid number");
            }
        }
        return output;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
