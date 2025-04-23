import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import edu.macalester.graphics.Point;

public class Main {

    public Character player;
    public PriorityComparator priorityComparator;

    public Main(){
        player = new MainPlayer(100, 1, 100, 1.5, 10, 1.5, 10, null);
    }

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        ((MainPlayer) player).setName(name);
        System.out.println("This is the story of " + name);
        Deque<Character> encounter = generateEncounter();
        
        while(player.getHealth()>0){
            Character currActor = encounter.poll();
            //System.out.println(currActor.getClass());
            if(currActor.getClass().equals(player.getClass())){
                playerTurn(response);
            }   
            else{
                enemyTurn(currActor);
                System.out.println("enemy");
            }     
            encounter.offer(currActor);
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
            int order = 1;
           List<Attack> attackList = ((MainPlayer) player).getAttacks(); 
           for(Attack attack:attackList){
            System.out.println(order+" "+attack);
            order++;
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
        enemy.attack(player);
    }

    public Deque<Character> generateEncounter(){
        Deque<Character> queue = new ArrayDeque<Character>();; // 
        //we are NOT using a priority queue, just make a list of all the characters and then sort it (either Comparable or Comparator),
        //then add the list to a normal queue
        Character enemy = new Enemy("test",0, 0, 0, 9, 0, 0);
        queue.add(enemy);
        queue.add(player);
        return queue;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
