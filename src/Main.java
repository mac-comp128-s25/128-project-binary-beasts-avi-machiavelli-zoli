import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private Character player;
    private PriorityComparator priorityComparator = new PriorityComparator();
    private List<Enemy> enemyList;
    private TreeMap<String, List<Skill>> skillTree;
    private List<Attack> possibleAttacks;
    private List<Spell> possibleSpells;

    public Main(){
        player = new MainPlayer(100, 10, 100, 1.5, 10, 1.5, 10, null);
        enemyList = new ArrayList<>();
        possibleAttacks = new ArrayList<>();
        possibleSpells = new ArrayList<>();
    }

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        ((MainPlayer) player).setName(name);
        System.out.println("This is the story of " + name);
        ((MainPlayer) player).addAttack(new Attack("Regular", 4, 1.00));
        ((MainPlayer) player).addAttack(new Attack("Regular2", 1, 0.5));
        ((MainPlayer) player).addSpell(null);

        //System.out.println("Choose your attacks! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
        Deque<Character> encounter = generateEncounter();
        
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
            if(attackResponse==1){
                int enemyResponse = chooseEnemy(input);
                player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1));
            }
            else{
                int enemyResponse = chooseEnemy(input);
                player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1));
            }
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
        System.out.println("Enemy " + enemy.getName() + " attacks!");
        ((Enemy)enemy).attack(player);
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

        Character enemy = new Enemy("test",10, 0, 0, 9, 0, 0);
        enemyList.add((Enemy)enemy);
        encounterList.add(enemy);

        Collections.sort(encounterList, priorityComparator);
        queue.addAll(encounterList);

        return queue;
    }

    public int playerResponse(int numAnswers, String preamble){
        Scanner response = new Scanner(System.in);
        int output = 0;
        while(output>numAnswers || output<1){
            System.out.println(preamble);
            try{
                output = response.nextInt();
            }
            catch(java.util.InputMismatchException e){
                System.out.println("That's not a number!");
            }
            
            if(output>numAnswers || output<0){
                System.out.println("Please enter a valid number");
            }
        }
        return output;
    }

    public void graphSetup(){
        //treemap--it has significantly less depth, but it will be an easy middle implementation that we can make more complicated later
        //String, List<Node> of the Upgrade Type (attack, spell, upgrade) and the list of available upgrades
        skillTree = new TreeMap<>();
        //SkillNode attackNode = new SkillNode(possibleAttacks);
        //SkillNode spellNode = new SkillNode(possibleSpells);
        //skillTree.put("Attacks", possibleAttacks);
        //skillTree.put("Spells", possibleSpells); WHY
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
