import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
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
    private List<Skill> possibleAttacks;
    private List<Skill> possibleSpells;
    private Deque<Character> encounter;

    public Main(){
        player = new MainPlayer(100, 10, 100, 1.5, 10, 1.5, 10, null);
        enemyList = new ArrayList<>();
        possibleAttacks = new ArrayList<>();
        possibleSpells = new ArrayList<>();
        skillTree = new TreeMap<>();
        skillSetup();
    }

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        ((MainPlayer) player).setName(name);
        System.out.println("This is the story of " + name);

        //System.out.println("Choose your attacks! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
        encounter = generateEncounter();
        
        while(player.getHealth()>0){
            Character currActor = encounter.poll();
            if(currActor.getClass().equals(player.getClass())){
                playerTurn(response);
                encounter.offer(currActor);
            }   
            else{
                if(enemyTurn(currActor)){
                    encounter.offer(currActor);
                }
            }     
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
                int attackResponse = playerResponse(((MainPlayer)player).attackList.size(), "Choose your attack!");
                int enemyResponse = chooseEnemy(input);
                if(player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1))){
                    enemyList.get(enemyResponse-1).setDead(true);
                }
            }
        else if(numResponse==2){
            int order = 1;
            List<Spell> spellList = ((MainPlayer) player).getSpells();
            for(Spell spell:spellList){
                System.out.println(order+" "+spell.getName());
                order++;
            }
            int spellChoice = playerResponse(spellList.size(), "Choose a spell!");
            if(spellList.get(spellChoice-1).getTargeting() == true){
                ((MainPlayer)player).useSpell(spellList.get(spellChoice-1), enemyList);
            }
            else{
                int target = chooseEnemy(input);
                ((MainPlayer)player).useSpell(spellList.get(spellChoice-1),enemyList.get(target-1));
            }
        }
    }

    public boolean enemyTurn(Character enemy){
        if(((Enemy) enemy).getDead()){
            List<Enemy> newEnemyList = new ArrayList<Enemy>();
            for(Enemy oldEnemy: enemyList){
                if(!(oldEnemy == enemy)){
                    newEnemyList.add(oldEnemy);
                }
            }
            enemyList = newEnemyList;
            return false;
        }
        else{
            System.out.println("Enemy " + enemy.getName() + " attacks!");
            ((Enemy)enemy).attack(player);
            return true;
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
            try{
                output = response.nextInt();
            }
            catch(java.util.InputMismatchException e){
                System.out.println("That's not a number!");
                response.next();
            }
            
            if(output>numAnswers || output<0){
                System.out.println("Please enter a valid number");
            }
        }
        return output;
    }

    public void skillSetup(){
        //treemap--it has significantly less depth, but it will be an easy middle implementation that we can make more complicated later
        //String, List<Node> of the Upgrade Type (attack, spell, upgrade) and the list of available upgrades

        //Define and add all intial attacks to the attackList
        Skill regular = new Attack("Regular Attack", 4, 0.75);
        possibleAttacks.add(regular);
        Skill trueCrit = new Attack("True Crit", 4, 1.00);
        possibleAttacks.add(trueCrit);
        Skill terribleSlash = new Attack("Terrible Slash", 1, 0.5);
        possibleAttacks.add(terribleSlash);

        //Define and add all intial spells to the spellList
        Skill thunderclap = new Spell("Thunderclap", 1, 2, true);
        possibleSpells.add(thunderclap);

        //possibleAttacks.forEach((x) -> {((MainPlayer) player).getAttacks().add((Attack) x);});
        //possibleSpells.forEach((x) -> {((MainPlayer) player).getSpells().add((Spell) x);});

        //Define and add all other attacks to the attackList
        Skill fellingBlow = new Attack("Felling Blow", 8, 0.6);
        possibleAttacks.add(fellingBlow);
        Skill giantsBlade = new Attack("Giant's Blade", 6, 0.7);
        possibleAttacks.add(giantsBlade);
        Skill bladeOfMidnight = new Attack("Blade of Midnight", 4, 0.90);
        possibleAttacks.add(bladeOfMidnight);
        Skill strikeOfTheWind = new Attack("Strike of the Wind", 3, 0.95);
        possibleAttacks.add(strikeOfTheWind);
        

        //Define and add all other spells to the spellList
        Skill holyFlame = new Spell("Holy Flame", 1, 4, false);
        possibleSpells.add(holyFlame);
        Skill shatter = new Spell("Shatter", 2, 3, true);
        possibleSpells.add(shatter);
        Skill scorchingRay = new Spell("Scorching Ray", 2, 5, false);
        possibleSpells.add(scorchingRay);
        Skill phantasmalKiller = new Spell("Phantasmal Killer", 3, 4, true);
        possibleSpells.add(phantasmalKiller);
        Skill psychicStrike = new Spell("Psychic Strike", 3, 6, false);
        possibleSpells.add(psychicStrike);
        Skill heavenlySwarm = new Spell("Heavenly Swarm", 4, 5, true);
        possibleSpells.add(heavenlySwarm);
        Skill heavensCurse = new Spell("Heaven's Curse", 4, 7, false);
        possibleSpells.add(heavensCurse);
        Skill fireball = new Spell("Fireball", 5, 6, true);
        possibleSpells.add(fireball);
        Skill obliteration = new Spell("Obliteration", 5, 8, false);
        possibleSpells.add(obliteration);
        Skill powerWordKill = new Spell("Power Word: Kill", 6, 100, true);
        possibleSpells.add(powerWordKill);

        possibleAttacks.forEach((x) -> {((MainPlayer) player).getAttacks().add((Attack) x);});
        possibleSpells.forEach((x) -> {((MainPlayer) player).getSpells().add((Spell) x);});

        
        //TODO define all possible attacks and spells up here

        skillTree.put("Attacks", possibleAttacks);
        skillTree.put("Spells", possibleSpells); 
        //upgrades
    }

    public void addSkill(){
        //if the skill is already in the player's list of abilities, then don't allow them to take it
        //otherwise, add the skill to the proper list (attack/spell), and add it to the greater list of all skills
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
