import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.jar.Manifest;

public class Main {

    private MainPlayer player;
    private PriorityComparator priorityComparator = new PriorityComparator();
    private List<Enemy> enemyList;
    private TreeMap<String, List<Skill>> skillTree;
    private List<Skill> possibleAttacks;
    private List<Skill> possibleSpells;
    private List<Skill> possibleUpgrades;

    private Deque<Character> encounter;

    public Main(){
        player = new MainPlayer(100, 10, 100, 1.5, 10, 1.5, 10, null);
        enemyList = new ArrayList<>();
        possibleAttacks = new ArrayList<>();
        possibleSpells = new ArrayList<>();
        possibleUpgrades = new ArrayList<>();
        skillTree = new TreeMap<>();
        skillSetup();
    }

    public void mainGame(){
        Scanner response = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = response.nextLine();
        player.setName(name);
        System.out.println("This is the story of " + name);
        initializeStartingSkills();
        addSkills(response, 3);

        encounter = generateEncounter(2,1);
        int enemyCount = 2;
        double difficulty = 1;
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
            if(enemyList.size()<=0){
                difficulty+=1;
                if(difficulty%2==1){
                    enemyCount+=1;
                }
                addSkills(response, 1);
                encounter = generateEncounter(enemyCount,difficulty);
            }     
        }
        System.out.println("You died! Game over!");
    }

    public void playerTurn(Scanner input){
        System.out.println("You have " + player.getHealth() + " health remaining.");
        for(Enemy enemy: enemyList){
            System.out.println(enemy.getName() +" has "+ enemy.getHealth() + " health");
        }
        int numResponse = playerResponse(2, "Choose your action! \n1. Attack \n2. Spell \nType the number of the action you would like to take");
        if(numResponse==1){
            int order = 1;
            List<Attack> attackList =  player.getAttacks(); 
            for(Attack attack:attackList){
            System.out.println(order+" "+attack.getName()+": deals "+((Attack)attack).getDamage()+" damage, and has a " + ((Attack)attack).getChanceToHit()+ " chance to hit");
            order++;
            }
                int attackResponse = playerResponse(player.attackList.size(), "Choose your attack!");
                int enemyResponse = chooseEnemy(input);
                if(player.useAttack(enemyList.get(enemyResponse-1), attackList.get(attackResponse-1))){
                    enemyList.get(enemyResponse-1).setDead(true);
                }
            }
        else if(numResponse==2){
            int order = 1;
            System.out.println("You have "+player.getMana() + " mana");
            List<Spell> spellList = player.getSpells();
            for(Spell spell:spellList){
                System.out.println(order+" "+spell.getName()+": costs "+((Spell)spell).getManaCost()+" mana, deals "+((Spell)spell).getDamage()+" damage, and targets all enemies:" +((Spell)spell).getTargeting());
                order++;
            }
            int spellChoice = playerResponse(spellList.size(), "Choose a spell!");
            if(spellList.get(spellChoice-1).getTargeting() == true){
                player.useSpell(spellList.get(spellChoice-1), enemyList);
                    
            }
            else{
                int target = chooseEnemy(input);
                if(player.useSpell(spellList.get(spellChoice-1),enemyList.get(target-1))){
                    enemyList.get(target-1).setDead(true);
                }
            }
        }
        player.modifyMana(1);
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

    public Deque<Character> generateEncounter(int enemyCount, double difficulty){
        System.out.println("Combat begins!");
        Deque<Character> queue = new ArrayDeque<Character>();; 
        List<Character> encounterList = new ArrayList<>();
        encounterList.add(player);

        for(int i = 0; i<enemyCount;i++){
            Character enemyCharacter = new Enemy("Enemy " + (i+1),10*difficulty, difficulty, .1*difficulty, 10-(difficulty/2), difficulty/5, difficulty);
            enemyList.add((Enemy)enemyCharacter);
            encounterList.add((Enemy)enemyCharacter);
        }

        // Character enemy1 = new Enemy("test1",10, 0, 0, 9, 0, 0);
        // Character enemy2 = new Enemy("test2",10, 0, 0, 9, 0, 0);
        // enemyList.add((Enemy)enemy1);
        // enemyList.add((Enemy)enemy2);
        // queue.add(enemy1);
        // encounterList.add(enemy2);

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
        //Define and add all intial attacks to the player's attackList
        Skill regular = new Attack("Regular Attack", 4, 0.75);
        player.getAttacks().add((Attack)regular);
        Skill trueCrit = new Attack("True Critical", 2, 1.00);
        player.getAttacks().add((Attack)trueCrit);
        Skill terribleSlash = new Attack("Terrible Slash", 1, 0.5);
        player.getAttacks().add((Attack)terribleSlash);

        //Define and add all intial spells to the player's spellList
        Skill thunderclap = new Spell("Thunderclap", 1, 2, true);
        player.getSpells().add((Spell)thunderclap);

        //Define and add all other attacks to the possibleAttacks list
        Skill fellingBlow = new Attack("Felling Blow", 8, 0.6);
        possibleAttacks.add(fellingBlow);
        Skill giantsBlade = new Attack("Giant's Blade", 6, 0.7);
        possibleAttacks.add(giantsBlade);
        Skill bladeOfMidnight = new Attack("Blade of Midnight", 4, 0.90);
        possibleAttacks.add(bladeOfMidnight);
        Skill strikeOfTheWind = new Attack("Strike of the Wind", 3, 0.95);
        possibleAttacks.add(strikeOfTheWind);

        //Define and add all other spells to the possibleSpells list
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
        Skill powerWordKill = new Spell("Power Word: Kill", 11, 100, true);
        possibleSpells.add(powerWordKill);

        //Define and add all upgrades to the possibleUpgrades list
        Skill healthBonus = new Upgrade("Health Bonus x 1.5", true);
        possibleUpgrades.add(healthBonus);
        Skill critBonus = new Upgrade("Crit Bonus x 1.1", false);
        possibleUpgrades.add(critBonus);

        skillTree.put("Attacks", possibleAttacks);
        skillTree.put("Spells", possibleSpells); 
        skillTree.put("Upgrades", possibleUpgrades); 
    }

    public void initializeStartingSkills(){
        List<Attack> attackList = player.getAttacks();
        int order =1; 
        System.out.println("Your starting attacks are:");
        for(Attack attack:attackList){
            System.out.println(order+" "+attack.getName()+": deals "+((Attack)attack).getDamage()+" damage, and has a " + ((Attack)attack).getChanceToHit()+ " chance to hit");
            order++;
        }
        List<Spell> spellList = player.getSpells();
        order =1; 
        System.out.println("Your starting spells are:");
        for(Spell spell:spellList){
            System.out.println(order+" "+spell.getName()+": costs "+((Spell)spell).getManaCost()+" mana, deals "+((Spell)spell).getDamage()+" damage, and targets all enemies:" +((Spell)spell).getTargeting());
            order++;
        }
    }

    public void addSkills(Scanner scanner, int abilityNum){
        while(abilityNum>0){
            System.out.println("You have "+ player.getAttacks().size() + " attacks, and " + player.getSpells().size() + " spells.");
            System.out.println("You have " + abilityNum + " ability choices remaining. Choose the type of your ability!");
            int abilityOrder = 1;
            for(String key : skillTree.keySet()){
                System.out.println(abilityOrder+" "+key);
                abilityOrder++;
            }
            int numResponse = playerResponse(skillTree.keySet().size(), "Type the number of the ability type.");
            if(numResponse == 1){
                int order = 1;
                for(Skill attack : possibleAttacks){
                    System.out.println(order+" "+attack.getName()+": deals "+((Attack)attack).getDamage()+" damage, and has a " + ((Attack)attack).getChanceToHit()+ " chance to hit"); 
                    order++;
                }
                int attackChoice = playerResponse(possibleAttacks.size(), "Choose an attack!");
                player.getAttacks().add((Attack) possibleAttacks.get(attackChoice-1));
                possibleAttacks.remove(attackChoice-1);
                abilityNum--;
            }
            if(numResponse == 2){
                int order = 1;
                for(Skill spell : possibleSpells){
                    System.out.println(order+" "+spell.getName()+": costs "+((Spell)spell).getManaCost()+" mana, deals "+((Spell)spell).getDamage()+" damage, and targets all enemies:" +((Spell)spell).getTargeting());
                    order++;
                }
                int spellChoice = playerResponse(possibleSpells.size(), "Choose a spell!");
                player.getSpells().add((Spell) possibleSpells.get(spellChoice-1));
                possibleSpells.remove(spellChoice-1);
                abilityNum--;
            }
            if(numResponse ==3){
                int order = 1;
                for(Skill upgrade : possibleUpgrades){
                    System.out.println(order+" "+upgrade.getName());
                    order++;
                }
                int upgradeChoice = playerResponse(possibleUpgrades.size(), "Choose an upgrade!");
                if(possibleUpgrades.get(upgradeChoice-1).bonusType()){
                    player.setHealth(player.getHealth() *1.5);
                } else {
                    player.setCritMultiplier(player.getCritMultiplier() * 1.1);
                }
                abilityNum--;
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.mainGame();
    }
}
