package pokedex;

import pokedex.pokemon.Type;
import pokedex.pokemon.Move;
import pokedex.pokemon.Pokemon;
import pokedex.pokemon.BattleReadyPokemon;
import pokedex.pokemon.Team;
import pokedex.pokemon.pokemoncomparators.DecreasingStrengthComparator;
import pokedex.util.Reader;
import pokedex.util.Writer;

import java.io.File;

import java.io.IOException;
import java.util.*;

/**
 * The Main class for the Pokemon tracker program. Handles the main menu loop and the displaying of data.
 */
public class Main {

    /**
     * Placeholder. Actual file will be put here in main function, so that it can be used for exporting data as well.
     */

    private static File file = null;
    /**
     * Store list of Pokemon in the program.
     */
    private static final ArrayList<Pokemon> pokemon = new ArrayList<>();

    /**
     * Store list of teams in the program.
     */
    private static final ArrayList<Team> teams = new ArrayList<>();

    /**
     * Store list of moves in the program.
     */
    private static final ArrayList<Move> moves = new ArrayList<>();

    /**
     * Store list of individual instances of Pokemon.
     */
    private static final ArrayList<BattleReadyPokemon> battlePokemon = new ArrayList<>();

    /***
     *  The main function for the program. Reads the CSV File, and starts the main menu loop.
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 2) {
            System.err.println("Only one argument for this program is allowed!");
            System.err.println("Usage: Main <csv file> (CSV File is optional)");
            System.exit(1);

        } else if (args.length == 1) {

            file = new File(args[0]);

            if (file.exists() && file.isFile()) { // if file exists, read data from it
                ArrayList<Object> dataFromCSV = Reader.readData(file);
                addDataToProgram(dataFromCSV);
            }
        }

        run();
    }

    /***
     * The run function for the program. Runs the main menu loop.
     */
    static void run() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Pokedex Program by Amin Elnasri.");

        while (true) { // main menu loop; exit when user enters 16
            System.out.println("""
                     Select an option by entering a number from 1-16
                     Add Data:                  Output General
                     1) Add a Pokemon Team      7) View a list of Pokemon
                     2) Add a Pokemon           8) View a list of Pokemon teams
                     3) Add a Pokemon Move      9) View a list of Pokemon moves
                     4) Add A Battle Pokemon    10) Search for a Pokemon
                     5) Change A Team           11) Search for a Move
                     6) Change A Team Name      12) View Battle-Ready Pokemon

                    Output Special:
                    13) View 10 Strongest Pokemon
                    14) View 10 Weakest Pokemon
                    15) View Pokemon of x type
                    16) Create a Random Team
                    17) Extra Information
                    18) Import Data from CSV
                    19) Export Data to CSV
                    20) Exit Program
                     """);

            String selection = scanner.next();

            // make sure user entered a valid menu option (number between 1-16)
            if (!validateNum(selection,20)) {
                System.out.println("Invalid input. Please enter a number between 1-20.");
                continue;
            }

            if (selection.equals("1")) {
                addPokemonTeam(scanner);
            }
            if (selection.equals("2")) {
                addPokemon(scanner);
            }
            if (selection.equals("3")) {
                addMove(scanner);
            }
            if (selection.equals("4")) {
                addBattlePokemon(scanner);
            }
            if (selection.equals("5")) {
                 changeTeam(scanner);
            }
            if (selection.equals("6")) {
                changeTeamName(scanner);
            }
            if (selection.equals("7")) {
                showList(pokemon,scanner);
            }
            if (selection.equals("8")) {
                showTeams(scanner);
            }
            if (selection.equals("9")) {
                showMoves(moves,scanner);
            }
            if (selection.equals("10")) {
                searchForPokemon(scanner);
            }
            if (selection.equals("11")) {
                searchForMove(scanner);
            }
            if (selection.equals("12")) {
                showBattleList(scanner);
            }

            if (selection.equals("13")) {
                viewTenStrongest(scanner);
            }
            if (selection.equals("14")) {
                viewTenWeakest(scanner);
            }
            if (selection.equals("15")) {
                viewPokemonOfTypeX(scanner);
            }
            if (selection.equals("16")) {
                createRandomTeam(scanner);
            }
            if (selection.equals("17")) {
                extraInformation(scanner);
            }
            if (selection.equals("18")) {
                importDataFromCSV(scanner);
            }
            if (selection.equals("19")) {
                exportDataToCSV(scanner);
            }

            if (selection.equals("20")) {
                System.out.println("Exiting program.");
                System.exit(1);
            }
        }

    }

    private static void changeTeam(Scanner scanner) {
        System.out.print("Team to change: ");
        String teamName = getStringFromUser(scanner);

        Team team = getTeam(teamName);

        if (team == null) {
            System.out.println("No team with this name found! Returning to menu.");
            return;
        }

        System.out.println("Pokemon to replace in team: ");
        String pokemonToReplaceName = getStringFromUser(scanner);
        BattleReadyPokemon pokemonToReplace = getBRP(pokemonToReplaceName);

        if (!team.contains(pokemonToReplaceName)) {
            System.out.println("This pokemon doesn't exist in the team. Returning to menu.");
            return;
        }

        System.out.println("Nickname of new pokemon to swap '" + pokemonToReplaceName + "' with: ");
        String nickname = getStringFromUser(scanner);
        BattleReadyPokemon newPokemon = getBRP(nickname);

        if (newPokemon == null) {
            System.out.println("Pokemon with that nickname doesn't exist. Returning to menu.");
            return;
        }

        team.swapPokemon(pokemonToReplace,newPokemon);
        System.out.println("'" + pokemonToReplaceName + "' swapped with '" + nickname + "' in team '" + teamName + "'.");
        System.out.println("Enter anything to return to the menu.");
        scanner.nextLine();
    }

    /**
     * Get team with a given name.
     * @param teamName Team name of team to retrieve.
     * @return The team with the name teamName.
     */
    private static Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.name().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Get a battle pokemon given it's nickname.
     * @param nickname The nickname of the BattleReadyPokemon to find.
     * @return The BattleReadyPokemon with the nickname 'nickname'.
     */
    private static BattleReadyPokemon getBRP(String nickname) {
        for (BattleReadyPokemon battleReadyPokemon : battlePokemon) {
            if (battleReadyPokemon.nickname().equals(nickname)) {
                return battleReadyPokemon;
            }
        }
        return null;
    }


    /***
     * Check if a string can be parsed into an integer ranging from 1 to a given value.
     * @param selection The string to be parsed as an integer.
     * @param maximum The maximum value the integer version of the string can be.
     * @return A boolean , true if selection can be parsed as an integer that's between 1 and maximum, false otherwise.
     */
    public static boolean validateNum(String selection, int maximum) { // Validate a string is a number from 0 to a given val
        try {
            int s = Integer.parseInt(selection);
            return !(s < 1 || s > maximum); // return false if the menu option is out of bounds
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Add a Pokemon team to the program.
     * @param scanner The scanner object to collect input.
     */
    private static void addPokemonTeam(Scanner scanner) {
        scanner.nextLine();
        System.out.print("New Team Name: ");

        String teamName = "";
        // keep prompting user until a valid team name has been inputted
        while (Team.nameTaken(teamName) || teamName.equals("")) {
            teamName = getStringFromUser(scanner);
            if (Team.nameTaken(teamName)) {
                System.out.println("Team with name " + teamName + " already exists!");
            }
        }

        ArrayList<BattleReadyPokemon> newTeam = getPokemonTeamFromUser(scanner);

        if (newTeam.size() == 0) {
            System.out.println("No pokemon were selected. Returning to menu.");
            return;
        }

        Team team = new Team(teamName);
        team.addPokemonList(newTeam);
        teams.add(team);

        StringBuilder successMessage = new StringBuilder("Team " + team.name() + " consisting of ");

        // create a success message based off num of Pokemon in team.
        if (newTeam.size() == 1) {
            successMessage.append(newTeam.get(0).name()).append(" has been added.");
        }
        else {
            for (int i = 0; i < newTeam.size(); i++) {
                if (i != newTeam.size()-1) {
                    successMessage.append(newTeam.get(i).name()).append(", ");
                }  else {
                    successMessage.append("and ").append(newTeam.get(i).name()).append(" has been added.");
                }
            }
        }


        System.out.println(successMessage);
        System.out.println("(press Enter go back to the menu)");
        scanner.nextLine();

    }

    /**
     * Get a list of 6 or less BattleReadyPokemon from the user.
     * @param scanner The scanner object to collect input.
     * @return An ArrayList of 6 or less BattleReadyPokemon.
     */
    private static ArrayList<BattleReadyPokemon> getPokemonTeamFromUser(Scanner scanner) {
        ArrayList<BattleReadyPokemon> newTeam = new ArrayList<>();
        ArrayList<BattleReadyPokemon> availableBRP = new ArrayList<>(battlePokemon); // make clone of global
        // battlePokemon arraylist; we will be removing pokemon from this list as we add them to the team.

        String pokemonToAdd = "";
        int pokemonNumToAdd = 1;
        // while team isnt full, theres enough pokemon left, and user hasnt entered C
        while (!pokemonToAdd.equals("C") && newTeam.size() < 6 && availableBRP.size() > 0) {

            // display non-chosen BattleReadyPokemon to user
            for (int i = 1; i < availableBRP.size()+1; i++) {
                System.out.println(i + ") " + availableBRP.get(i-1).name());
            }

            System.out.print("Add Pokemon " + pokemonNumToAdd + " to team (enter an index in the list)" +
                    ". Enter C to stop adding Pokemon: ");

            pokemonToAdd = getStringFromUser(scanner);

            if (pokemonToAdd.equals("C")) { continue; }

            if (pokemonToAdd.equals("1") && availableBRP.size() == 1) { // handle case in which there's only 1 pokemon
                // left
                newTeam.add(availableBRP.get(0).clone());
                return newTeam;
            }

            if (!validateNum(pokemonToAdd,availableBRP.size())) {
                System.out.println("Index not in list!");
                continue;
            }

            int i = Integer.parseInt(pokemonToAdd);
            i--; // subtract by 1 as the table shown to the user starts from 1 and ends at availableBrp.size-1

            newTeam.add(availableBRP.get(i).clone());
            pokemonNumToAdd++;
            availableBRP.remove(i); // make the pokemon no longer an option to add to the team
        }

        return newTeam;

    }

    /**
     * Obtain a numerical value from the user for a Pokemons attribute.
     * @param scanner Scanner object used to collect input.
     * @return A string that is able to be parsed into an integer between 1 and 1000.
     */
    private static int getAttributeFromUser(Scanner scanner) {
        String input = scanner.next();
        while (!validateNum(input,1000)) {
            System.out.println("Please enter a number between 1 and 1000.");
            input = scanner.next();
        }
        return Integer.parseInt(input);
    }

    private static String getStringFromUser(Scanner scanner) {
        String input = scanner.nextLine();
        while (input.equals("")) {
            input = scanner.nextLine();
        }
        return input;
    }

    /***
     * Allows user to add a Pokemon to the database.
     * @param scanner The scanner object to collect input.
     */
    private static void addPokemon(Scanner scanner) {
        System.out.print("Name of Pokemon: ");
        String name = scanner.next();
        while (Pokemon.pokemonExists(name)) {
            System.out.print("Pokemon with name '" + name + "' already exists. Please enter a different name: ");
            name = getStringFromUser(scanner);
        }

        System.out.print("HP: ");
        int hp = getAttributeFromUser(scanner);
        System.out.print("Attack Stat: ");
        int atk = getAttributeFromUser(scanner);
        System.out.print("Special Attack Stat: ");
        int spatk = getAttributeFromUser(scanner);
        System.out.print("Defense Stat: ");
        int def = getAttributeFromUser(scanner);
        System.out.print("Special Defense Stat: ");
        int spdef = getAttributeFromUser(scanner);
        System.out.print("Speed Stat: ");
        int speed = getAttributeFromUser(scanner);

        System.out.print("Type 1: ");
        String firstType = scanner.next();

        while (!Type.validateType(firstType)) {
            System.out.println("Please enter a valid Pokemon type. Enter one of: Normal, Fire, Water, Grass, Electric" +
                    "Ice, Fighting, Poison, Ground, Flying, Psychic, Bug, Rock, Ghost, Dark, Dragon, Steel, or Fairy.");
            firstType = scanner.next();
        }

        System.out.println("Type 2 (Optional, enter '.' to leave the type as null): ");

        String secondType = scanner.next();

        while (!(Type.validateType(secondType) || secondType.equals("."))) {
            System.out.println("Please enter a valid Pokemon type, or enter '.' to leave the second type as null. " +
                    "Valid types are: Normal, Fire, Water, Grass, Electric" +
                    "Ice, Fighting, Poison, Ground, Flying, Psychic, Bug, Rock, Ghost, Dark, Dragon, Steel, or Fairy.");
            secondType = scanner.next();
        }
        if (secondType.equals(".")) {
            secondType = "";
        }
        Type type1 = new Type(firstType);
        Type type2 = new Type(secondType);

        System.out.print("Description: ");
        scanner.nextLine();
        String description = scanner.nextLine();
        Pokemon newPokemon = new Pokemon(name,hp,atk,spatk,def,spdef,speed,description,type1,type2);
        pokemon.add(newPokemon);
        System.out.println(newPokemon.name() + " added to the Pokedex. Press enter to go back to the menu.");
        scanner.nextLine();
    }

    /**
     * Let user add a BattleReadyPokemon to the program.
     * @param scanner The scanner object to collect input.
     */
    public static void addBattlePokemon(Scanner scanner) {
        System.out.print("What Pokemon would you like to base this Battle Pokemon off of? Enter the name: ");
        String pokemonName = getStringFromUser(scanner);

        // make sure pokemon exists
        if (!Pokemon.pokemonExists(pokemonName)) {
            System.out.println("No Pokemon with the name '" + pokemonName + " exists. Returning to menu.");
            return;
        }

        Pokemon poke = getPokemon(pokemonName);

        System.out.print("Add a nickname to " + pokemonName + ": ");

        String nickname = getStringFromUser(scanner);
        while (BattleReadyPokemon.nicknameUsed(nickname)) {
            System.out.print("Nickname " + nickname + " has already been used by a Battle-Ready Pokemon." +
                    " Please pick a different nickname: ");
            nickname = getStringFromUser(scanner);
        }
        ArrayList<Move> initialMoves = getMovesFromUser(scanner);
        assert poke != null;
        BattleReadyPokemon newPoke = new BattleReadyPokemon(nickname,poke.name(),poke.health(),poke.attack(),
                poke.specialatk(), poke.defense(), poke.specialdef(), poke.speed(),
                poke.description(), poke.firstType(),poke.secondType(),initialMoves);

        battlePokemon.add(newPoke);

        System.out.println("Battle Pokemon has been added. Details: ");
        System.out.println(newPoke);
        System.out.println("(enter anything to go back to the menu)");
        scanner.nextLine();
    }

    /**
     * Get an arraylist of moves from the user.
     * @param scanner The scanner object to collect input.
     * @return An Arraylist of up to 4 different Move objects.
     */
    public static ArrayList<Move> getMovesFromUser(Scanner scanner) {
        ArrayList<Move> movesList = new ArrayList<>();
        ArrayList<Move> availableMoves = new ArrayList<>(moves);

        String moveToAdd = "";
        int moveNumToAdd = 1;
        while (!moveToAdd.equals("C") && movesList.size() < 4 && availableMoves.size() > 0) {

            // display non-chosen BattleReadyPokemon to user
            for (int i = 1; i < availableMoves.size()+1; i++) {
                System.out.println(i + ") " + availableMoves.get(i-1).name());
            }

            System.out.print("Add Move " + moveNumToAdd + " to team (enter an index in the list)" +
                    ". Enter C to stop adding Moves: ");

            moveToAdd = getStringFromUser(scanner);

            if (moveToAdd.equals("C")) { continue; }

            if (moveToAdd.equals("1") && availableMoves.size() == 1) { // handle case in which there's only 1 pokemon
                // left
                movesList.add(availableMoves.get(0));
                return movesList;
            }

            if (!validateNum(moveToAdd,availableMoves.size())) {
                System.out.println("Index not in list!");
                continue;
            }
            int i = Integer.parseInt(moveToAdd);
            i--; // subtract by 1 as the table shown to the user starts from 1 and ends at availableBrp.size-1

            movesList.add(availableMoves.get(i));
            moveNumToAdd++;
            availableMoves.remove(i); // make the pokemon no longer an option to add to the team
        }
        return movesList;
    }

    /**
     * Get a Pokemon given it's name.
     * @param pokemonName The name of the Pokemon to get.
     * @return The Pokemon Object that has the name pokemonName. If it doesn't exist, null will be returned.
     */
    public static Pokemon getPokemon(String pokemonName) {
        for (Pokemon value : pokemon) { // search and find pokemon
            if (value.name().equals(pokemonName)) {
                return value;
            }
        }
        return null;
    }
    /**
     * Allows user to add a move to the database.
     * @param scanner The scanner object to collect input.
     */
    private static void addMove(Scanner scanner) {
        System.out.print("Move name: ");
        scanner.nextLine();
        String moveName = getStringFromUser(scanner);
        // Check if move name already exists
        System.out.print("Description of move: ");
        String moveDescription = getStringFromUser(scanner);
        System.out.print("Type of move: ");
        String moveType = scanner.next();
        // while movetype is false, movetype doesnt equal .
        while (!(Type.validateType(moveType) || moveType.equals("."))) {
            System.out.print("""
                    Please enter a valid Move type, or enter '.' to leave the second type as null. " +
                    "Valid types are: Normal, Fire, Water, Grass, Electric" +
                    "Ice, Fighting, Poison, Ground, Flying, Psychic, Bug, Rock, Ghost, Dark, Dragon, Steel, or Fairy.""");
            moveType = scanner.next();
        }
        Type type = new Type(moveType);
        moves.add(new Move(moveName,moveDescription,type));
    }

    /***
     * Lets user change a team name.
     * @param scanner A scanner object to collect input.
     */
    private static void changeTeamName(Scanner scanner) {
        System.out.print("Team name to change: ");
        String oldName = getStringFromUser(scanner);

        if (!Team.nameTaken(oldName)) {
            System.out.println("No team with the name " + oldName + " has been found. Returning to menu.");
            return;
        }

        System.out.print("New team name: ");

        String newName = getStringFromUser(scanner);

        // search through teams, find team with name given by user, then use .changeName method on it to change its name
        for (Team team : teams) {
            if (team.name().equals(oldName)) {
                team.changeName(newName);
                System.out.println("Team '" + oldName + "' changed to have name '" + newName + "'. Press anything " +
                        "to return to menu.");
                scanner.nextLine();
            }
        }
    }

    /***
     * Allows user to search for a Pokemon by giving a parameter to filter by.
     * @param scanner The scanner object to collect input.
     */
    private static void searchForPokemon(Scanner scanner) {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        System.out.print("Search Query: ");
        String pokemonName = getStringFromUser(scanner);

        for (Pokemon possiblePokemon : pokemon) {
            if (possiblePokemon.name().contains(pokemonName)) {
                pokemonList.add(possiblePokemon);
            }
        }
        showList(pokemonList,scanner);

    }

    /***
     * Searches for Pokemon in the program using a query and returns them in an ArrayList.
     * @param query The query to filter Pokemon by.
     * @return Arraylist of Pokemon that contain query in their names.
     */
    public static ArrayList<Pokemon> searchPokemon(String query) {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        for (Pokemon possiblePokemon : pokemon) {
            if (possiblePokemon.name().contains(query)) {
                pokemonList.add(possiblePokemon);
            }
        }

        return pokemonList;
    }

    /***
     * Allow user to search for a Pokemon move.
     * @param scanner The scanner object to collect input.
     */
    private static void searchForMove(Scanner scanner) {
        ArrayList<Move> moveList = new ArrayList<>();
        System.out.print("Search Query: ");
        String moveQuery = getStringFromUser(scanner);
        for(Move possibleMove : moves){
            if(possibleMove.name().contains(moveQuery)){
                moveList.add(possibleMove);
            }
        }
        showMoves(moveList,scanner);
    }


    /***
     * Lets user look through all Pokemon teams.
     * @param scanner The scanner object to collect input.
     */
    private static void showTeams(Scanner scanner) {
        int size = teams.size();
        if (size == 0) {
            System.out.println("No teams found.");
        }
        else if(size == 1) {
            Team team = teams.get(0);
            String details = String.format("""
            %1$s\040\040\040\040\040\040\040\040\040
            (enter anything to back to the menu)
                            """, team);
            System.out.println(details);
            scanner.nextLine();
            scanner.nextLine();
        }
        else { // if more than one team in the list
            for (int i = 1; i < size + 1; i++) {
                System.out.println(i + ") " + teams.get(i - 1).name());
            }
            System.out.print("Enter an index to view details of a specific team: ");
            String option = scanner.next();

            while (validateNum(option,size)) { // make sure the selection was a number between 1 and size of array - 1 (max index)
                int i = Integer.parseInt(option);
                i--;
                Team team = teams.get(i);
                System.out.println(team);
                System.out.print("Enter an index to view details about a specific team (or anything else to go" +
                        " back to the menu): ");
                option = scanner.next();
        }
            System.out.println("Index not recognized. Returning to main menu");
        }
    }


    /***
     * Display ten strongest Pokemon to the user. (Pokemon with the highest sum of stats)
     * @param scanner The scanner object to collect input.
     */
    private static void viewTenStrongest(Scanner scanner) {
        List<Pokemon> clone = new ArrayList<>(pokemon); // create clone of pokemon list
        clone.sort(new DecreasingStrengthComparator());
        ArrayList<Pokemon> tenStrongest = getTenStrongest(pokemon);
        showList(tenStrongest,scanner);
    }

    /***
     * Display ten weakest Pokemon to user. (Pokemon with the lowest sum of stats)
     * @param scanner The scanner object to collect input.
     */
    public static void viewTenWeakest(Scanner scanner) {
        ArrayList<Pokemon> tenWeakest = getTenWeakest(pokemon);
        showList(tenWeakest,scanner);
    }

    /***
     * Get the ten weakest Pokemon.
     * @return An ArrayList of up to ten pokemon that have the lowest sums of stats.
     */
    public static ArrayList<Pokemon> getTenWeakest(ArrayList<Pokemon> pokemon) {
        // Accepts pokemon list as parameter rather than using the global list,
        // so that we can test it in PokemonTest
        List<Pokemon> clone = new ArrayList<>(pokemon); // create clone of pokemon list
        clone.sort(new DecreasingStrengthComparator());
        ArrayList<Pokemon> tenWeakest = new ArrayList<>();
        int downTo = clone.size() - Math.min(clone.size(), 10); // only get 10 weakest if
        // there are >= 10 in program
        for (int i=clone.size()-1; i >= downTo; i--) {
            tenWeakest.add(clone.get(i));
        }
        return tenWeakest;
    }

    /***
     * Get the ten strongest Pokemon.
     * @return An ArrayList of up to ten Pokemon that have the lowest sums of stats.
     */
    public static ArrayList<Pokemon> getTenStrongest(ArrayList<Pokemon> pokemon) {
        List<Pokemon> clone = new ArrayList<>(pokemon); // create clone of pokemon list
        clone.sort(new DecreasingStrengthComparator());
        ArrayList<Pokemon> tenStrongest = new ArrayList<>();
        int upTo = Math.min(clone.size(), 10); // only get 10 strongest pokemon if there are >= 10 in program

        for (int i = 0; i< upTo; i++) {
            tenStrongest.add(clone.get(i));
        }
        return tenStrongest;
    }

    /***
     * ALlows user to view all Pokemon of a given type.
     * @param scanner The scanner object to collect input.
     */
    private static void viewPokemonOfTypeX(Scanner scanner) {
        ArrayList<Pokemon> pokemonsOfAType = new ArrayList<>();
        System.out.print("Pokemon type to filter by: ");
        String pokemonType = getStringFromUser(scanner);

        if(Type.validateType(pokemonType)){
            for (Pokemon poke : pokemon) {
                if (poke.firstType().getType().equals(pokemonType) || poke.secondType().getType().equals(pokemonType)) {
                    pokemonsOfAType.add(poke);
                }
            }
            showList(pokemonsOfAType,scanner);
        }
        else{
            System.out.println("Pokemon type is not valid. Returning to main menu.");
        }
    }

    /**
     * Method that calls Writer class to export data from the program into a csv file.
     * @param scanner The scanner object to collect input.
     */
    public static void exportDataToCSV(Scanner scanner) throws IOException {
        if (file == null) {
            System.out.print("File to export to: ");
            String newFilename = getStringFromUser(scanner);
            File newFile = new File(newFilename);
            if (newFile.exists() && newFile.isFile()) {
                Writer.writeToCSV(newFile, pokemon,moves,battlePokemon,teams);
            } else {
                System.out.println("File does not exist or cannot be written to. Returning to menu.");
                return;
            }
        } else {
            Writer.writeToCSV(file,pokemon,moves,battlePokemon,teams);
        }
        System.out.println("Data exported to csv. (enter anything to back to the menu)");
        scanner.nextLine();
        scanner.nextLine();
    }

    /***
     * Create a random team of 6 Pokemon.
     * @param scanner The scanner object to collect input.
     */
    private static void createRandomTeam(Scanner scanner) {
        int numPokemon = pokemon.size();
        // make sure there's enough pokemon to create a random team
        if (numPokemon < 6) {
            System.out.println("Not enough Pokemon to generate a random team of 6.");
            return;
        }
        // get 6 random pokemon
        ArrayList<BattleReadyPokemon> pokemonTeam = getRandomPokemon();

        String details = String.format("""
                New Random Team
                1) %1$s       4) %4$s
                2) %2$s       5) %5$s
                3) %3$s       6) %6$s
               """, pokemonTeam.get(0).nameAndNickName(),pokemonTeam.get(1).nameAndNickName(),
                pokemonTeam.get(2).nameAndNickName(), pokemonTeam.get(3).nameAndNickName(),
                pokemonTeam.get(4).nameAndNickName(),pokemonTeam.get(5).nameAndNickName());

        // ask user if they want to add the random team
        System.out.println(details);
        System.out.println("Add Pokemon Team?");
        System.out.print("Input 'yes' to add this team, or anything else to not add it: ");
        String option = getStringFromUser(scanner);

        if (option.equals("yes")) {
            // if user wants to add a random team, ask them for a team name, and add the team
            System.out.print("New Team Name: ");
            String teamName = getStringFromUser(scanner);
            while (Team.nameTaken(teamName)) {
                System.out.print("Team with name '" + teamName + "' already exists! Please enter a " +
                        "different name: ");
                teamName = getStringFromUser(scanner);
            }
            Team newTeam = new Team(teamName);
            newTeam.addPokemonList(pokemonTeam);
            teams.add(newTeam);
            System.out.println("Pokemon team '" + teamName + "' added. Enter anything to return to the menu.");
        } else {
            System.out.println("Team not added. Enter anything to return to the menu.");
        }

        scanner.nextLine();

    }


    /***
     * Get an array of 6 random Pokemon names.
     * @return An Array, size 6, of 6 different Pokemon names.
     */
    public static ArrayList<BattleReadyPokemon> getRandomPokemon() {
        int numPokemon = battlePokemon.size();
        Random random = new Random();
        HashSet<Integer> randomNums = new HashSet<>();

        // get 6 nums between 0 and num of brp
        while (randomNums.size() < 6) {
            int n = random.nextInt(numPokemon);
            randomNums.add(n);
        }

        int n = 0; // will be num of iterations up to a certain point in the following loop

        ArrayList<BattleReadyPokemon> randomPokemon = new ArrayList<>();

        // loop through all battlePokemon while keeping track of the count; if the curcount is one of the
        // 6 random numbers, add the pokemon to the team.
        for (BattleReadyPokemon poke : battlePokemon){
            if (randomNums.contains(n)) {
                randomPokemon.add(poke.clone());
            }
            n++;
        }
        Collections.shuffle(randomPokemon); // shuffle the random pokemon so theyre not ordered by
        // index anymore
        return randomPokemon;
    }

    /***
     * Display a summation of data to user.
     * @param scanner The scanner object to collect input.
     */
    private static void extraInformation(Scanner scanner) {

        String extraInfo = String.format("""
                Number of Pokemon: %1$s
                Number of Pokemon Teams: %2$s
                Number of Pokemon Moves: %3$s

                (enter anything to go back to menu)""", pokemon.size(),teams.size(),moves.size());
        System.out.println(extraInfo);
        scanner.nextLine();
        scanner.nextLine();
    }



    /**
     * Display a list of Pokemon to the user.
     * @param pokemonList The arrayList of Pokemon to display to the user.
     * @param scanner The scanner object to collect input.
     */
    private static void showList(ArrayList<Pokemon> pokemonList, Scanner scanner) {
        int size = pokemonList.size();

        if (size == 0) {
            System.out.println("No Pokemon found.");
        }

        else if (size == 1) {
            Pokemon poke = pokemonList.get(0);
            System.out.println(poke);
            scanner.nextLine();
            scanner.nextLine();
        }

        else { // if more than one pokemon in the list

            for (int i = 1; i < size+1; i++) {
                System.out.println(i + ") " + pokemonList.get(i-1).name());
            }

            System.out.print("Enter an index to view details about a specific Pokemon: ");
            String option = scanner.next();

            while (validateNum(option,size)) { // make sure the selection was a number between 1 and size of array - 1 (max index)
                int i = Integer.parseInt(option);
                i--;
                Pokemon poke = pokemonList.get(i);
                System.out.println(poke);
                System.out.print("Enter an index to view details about a specific Pokemon (or anything else to" +
                        " go back to the menu): ");
                option = scanner.next();
            }
            System.out.println("Index not recognized. Returning to main menu.");
        }
    }

    /**
     *
     * @param scanner The scanner object to collect input.
     */
    private static void showBattleList(Scanner scanner) {
        int size = battlePokemon.size();

        if (size == 0) {
            System.out.println("No Battle-Ready Pokemon found.");
        }

        else if (size == 1) {
            Pokemon poke = battlePokemon.get(0);
            System.out.println(poke);
            scanner.nextLine();
            scanner.nextLine();
        }

        else {

            for (int i = 1; i < size+1; i++) {
                System.out.println(i + ") " + battlePokemon.get(i-1).nickname() + " (" +
                        battlePokemon.get(i-1).name() + ")");
            }

            System.out.print("Enter an index to view details about a specific Battle-Ready Pokemon: ");
            String option = scanner.next();

            while (validateNum(option,size)) { // make sure the selection was a number between 1 and size of array - 1 (max index)
                int i = Integer.parseInt(option);
                i--;
                Pokemon poke = battlePokemon.get(i);
                System.out.println(poke);
                System.out.print("Enter an index to view details about a specific Battle-Ready Pokemon " +
                        "(or anything else to go back to the menu): ");
                option = scanner.next();
            }
            System.out.println("Index not recognized. Returning to main menu.");
        }
    }


    /***
     * Display a list of Pokemon moves to the user.
     * @param movesList The arraylist with the moves to display.
     * @param scanner The scanner object to collect input.
     */
    private static void showMoves(ArrayList<Move> movesList, Scanner scanner) {
        int size = movesList.size();

        if (size == 0) {
            System.out.println("No moves found.");
        }

        else if (size == 1) {
            Move move = moves.get(0);
            System.out.println(move);
            System.out.println("(enter anything to go back to the menu)\n");
            scanner.nextLine();

        }
        else {

            for (int i = 1; i < size+1; i++) {
                System.out.println(i + ") " + movesList.get(i-1).name());
            }

            System.out.print("Enter an index to view details about a specific move: ");
            String option = scanner.next();
            while (validateNum(option,size)) {
                int i = Integer.parseInt(option);
                i--;
                Move move = moves.get(i);
                System.out.println(move);
                System.out.print("""
                        Enter another index to see another move's details, or anything else to go back:\040""");
                option = scanner.next();
            }
            System.out.println("Index not recognized. Returning to main menu");
        }
    }

    /**
     * Import a csv file into the program.
     * @param scanner The scanner object to collect input.
     */
    public static void importDataFromCSV(Scanner scanner) {
        System.out.print("File name to import from (must be correctly formatted CSV file): ");
        String filename = getStringFromUser(scanner);
        File newFile = new File(filename);
        // check file is valid
        if (!newFile.exists() || !newFile.isFile()) {
            System.out.println("File not found. Returning to menu.");
            return;
        }

        try {
            ArrayList<Object> objects = Reader.readData(newFile);
            addDataToProgram(objects);
        } catch (Exception e) {
            System.err.println("Could not read from file '" + filename + "'. Make sure file exists in the " +
                    "directory," + "and is correctly formatted.");
        }
        System.out.println("Data from file has been imported to the program. Press anything to return to menu.");
        scanner.nextLine();

    }

    /**
     * Add the list of objects returned by Reader to the program.
     * @param dataFromCSV Arraylist of objects that might be Pokemon, BattleReadyPokemon, or Moves.
     */
    public static void addDataToProgram(ArrayList<Object> dataFromCSV) {
        for (Object obj : dataFromCSV) { // add data from csv file to program
            if (obj instanceof Move) {
                moves.add((Move) obj);
            } else if (obj instanceof BattleReadyPokemon) {
                battlePokemon.add((BattleReadyPokemon) obj);
            } else if (obj instanceof Pokemon) {
                pokemon.add((Pokemon) obj);
            } else if (obj instanceof Team) {
                teams.add((Team) obj);
            }
        }
    }
}





