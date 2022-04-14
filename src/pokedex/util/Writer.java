package pokedex.util;

import pokedex.pokemon.BattleReadyPokemon;
import pokedex.pokemon.Move;
import pokedex.pokemon.Pokemon;
import pokedex.pokemon.Team;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Writer Class to write the data from the current instance of the program to a file.
 */
public final class Writer {

    /**
     * Field positions in the CSV files (pokemon index is the first field, pokemoninteam second, etc.)
     */
    private static final int NUM_ROWS = 26;
    private static final int IS_POKEMON_INDEX = 0;
    private static final int IS_POKEMON_IN_TEAM_INDEX = 1;
    private static final int IS_MOVE_INDEX = 2;
    private static final int IS_TEAM_INDEX = 3;

    private static final int NAME_INDEX = 4;
    private static final int HP_INDEX = 5;
    private static final int ATTACK_INDEX = 6;
    private static final int SPATTACK_INDEX = 7;
    private static final int DEFENSE_INDEX = 8;
    private static final int SPDEFENSE_INDEX = 9;
    private static final int SPEED_INDEX = 10;
    private static final int DESCRIPTION_INDEX = 11;
    private static final int TYPE_1_INDEX = 12;
    private static final int TYPE_2_INDEX = 13;
    private static final int MOVE_1_INDEX = 14;
    private static final int MOVE_2_INDEX = 15;
    private static final int MOVE_3_INDEX = 16;
    private static final int MOVE_4_INDEX = 17;
    private static final int NICKNAME_INDEX = 18;

    private static final int POKE_1_INDEX = 19;
    private static final int POKE_2_INDEX = 20;
    private static final int POKE_3_INDEX = 21;
    private static final int POKE_4_INDEX = 22;
    private static final int POKE_5_INDEX = 23;
    private static final int POKE_6_INDEX = 24;

    /**
     * Function to write data from the program into a given CSV file.
     * @param file The file to write to.
     * @param pokemonList The arraylist of Pokemon in the program.
     * @param movesList The arraylist of Moves in the program.
     * @param battlePokemonList The arraylist of BattleReadyPokemon in the program.
     */
    public static void writeToCSV(File file, ArrayList<Pokemon> pokemonList, ArrayList<Move> movesList,
                                           ArrayList<BattleReadyPokemon> battlePokemonList,
                                  ArrayList<Team> teamList) {
        if (file.exists() && file.isFile()) {
            try {
                FileWriter file_writer = new FileWriter(file);
                PrintWriter print_writer = new PrintWriter(file_writer);

                // write headers to csv
                print_writer.println("pokemon,pokemonInTeam,move,name,hp,attack,specialattack,defense,specialdef," +
                        "speed,description,type1,type2,move1,move2,move3,move4,nickname");

                // first, write moves data to file. important to do this BEFORE writing battlePokemon.
                for (Move move : movesList) {
                    print_writer.println(createMoveCSVRow(move));
                }
                print_writer.flush();

                // second, add pokemon objects to the file
                for (Pokemon pokemon : pokemonList) {
                    print_writer.println(createPokemonCSVRow(pokemon));
                }

                print_writer.flush();

                // third, add battlePokemon to file
                for (BattleReadyPokemon battleReadyPokemon : battlePokemonList) {
                    print_writer.println(createBattleReadyPokemonCSVRow(battleReadyPokemon));
                }

                print_writer.flush();

                for (Team team : teamList) {
                    print_writer.println(createTeamCsvRow(team));
                }

                print_writer.flush();

            } catch(IOException e) {
                System.err.println("Error writing to the file!");
                System.exit(1);
            }
        } else {
            System.err.println("Cannot access the file to write to!");
            System.exit(1);
        }
    }

    /**
     * Create a String representing a line in the csv file for a given Move.
     * @param move The move to represent in the line.
     * @return A string representing the line to add in the csv.
     */
    private static String createMoveCSVRow(Move move) {
        String description = "\"\"\"" + move.description() + "\"\"\""; // wrap around in double quotes
        String name = move.name();
        String type = move.getType().getType(); // move.getType returns type object, on which getType returns string
        String[] row = new String[NUM_ROWS]; // 25

        // set each value to a "." (null in our array)
        for (int i = 0; i < NUM_ROWS; i++) { row[i] = "."; }

        row[IS_MOVE_INDEX] = "Y"; // indicate this row is a move
        row[NAME_INDEX] = name;
        row[DESCRIPTION_INDEX] = description;
        row[TYPE_1_INDEX] = type;

        return String.join(",",row);

    }

    /**
     * Create a String representing a line in the csv file for a given Pokemon.
     * @param poke The Pokemon to represent in the line.
     * @return The string representing the Pokemon in a line for the csv file.
     */
    private static String createPokemonCSVRow(Pokemon poke) {

        String[] row = new String[NUM_ROWS]; // 18 fields in each row
        // set each value to a "." (null in our array)

        for (int i = 0; i < NUM_ROWS; i++) { row[i] = "."; }

        row[IS_POKEMON_INDEX] = "Y";
        row[NAME_INDEX] = poke.name();
        row[HP_INDEX] = String.valueOf(poke.health());
        row[ATTACK_INDEX] = String.valueOf(poke.attack());
        row[SPATTACK_INDEX] = String.valueOf(poke.specialatk());
        row[DEFENSE_INDEX] = String.valueOf(poke.defense());
        row[SPDEFENSE_INDEX] = String.valueOf(poke.specialdef());
        row[SPEED_INDEX] = String.valueOf(poke.speed());
        row[DESCRIPTION_INDEX] = "\"\"\"" + poke.description() + "\"\"\""; // wrap in double quotes
        // (avoids issue with splitting the string)
        row[TYPE_1_INDEX] = poke.firstType().getType();
        row[TYPE_2_INDEX] = poke.secondType().getType();

        return String.join(",", row);
    }

    /**
     * Create a String representing a line in the csv file for a given BattleReadyPokemon.
     * @param poke The Pokemon to represent in the line.
     * @return A String representing a line in the CSV file for a given BattleReadyPokemon.
     */
    private static String createBattleReadyPokemonCSVRow(BattleReadyPokemon poke) {
        String[] row = new String[NUM_ROWS]; // 18 fields in each row
        // set each value to a "." (null in our array)
        for (int i = 0; i < NUM_ROWS; i++) { row[i] = "."; }

        row[IS_POKEMON_IN_TEAM_INDEX] = "Y";
        row[NAME_INDEX] = poke.name();
        row[HP_INDEX] = String.valueOf(poke.health());
        row[ATTACK_INDEX] = String.valueOf(poke.attack());
        row[SPATTACK_INDEX] = String.valueOf(poke.specialatk());
        row[DEFENSE_INDEX] = String.valueOf(poke.defense());
        row[SPDEFENSE_INDEX] = String.valueOf(poke.specialdef());
        row[SPEED_INDEX] = String.valueOf(poke.speed());
        row[DESCRIPTION_INDEX] = "\"\"\"" + poke.description() + "\"\"\""; // wrap in double quotes
        row[TYPE_1_INDEX] = poke.firstType().getType();
        row[TYPE_2_INDEX] = poke.secondType().getType();
        row[NICKNAME_INDEX] = poke.nickname();

        if (poke.getMove(1) != null) {
            row[MOVE_1_INDEX] = Objects.requireNonNull(poke.getMove(1)).name();
        }
        if (poke.getMove(2) != null) {
            row[MOVE_2_INDEX] = Objects.requireNonNull(poke.getMove(2)).name();
        }
        if (poke.getMove(3) != null) {
            row[MOVE_3_INDEX] = Objects.requireNonNull(poke.getMove(3)).name();
        }
        if (poke.getMove(4) != null) {
            row[MOVE_4_INDEX] = Objects.requireNonNull(poke.getMove(4)).name();
        }
        return String.join(",", row);
    }

    /**
     * Create a String representing a line in the csv file for a given Team.
     * @param team The team to represent in the line.
     * @return The string representing a line in a csv file for this program.
     */
    private static String createTeamCsvRow(Team team) {
        String[] row = new String[NUM_ROWS]; // 18 fields in each row
        for (int i = 0; i < NUM_ROWS; i++) { row[i] = "."; }
        ArrayList<String> nicknames = team.getAllNicknames();

        if (nicknames.size() > 0) {
            row[POKE_1_INDEX] = nicknames.get(0);
        }
        if (nicknames.size() > 1) {
            row[POKE_2_INDEX] = nicknames.get(1);
        }
        if (nicknames.size() > 2) {
            row[POKE_3_INDEX] = nicknames.get(2);
        }
        if (nicknames.size() > 3) {
            row[POKE_4_INDEX] = nicknames.get(3);
        }
        if (nicknames.size() > 4) {
            row[POKE_5_INDEX] = nicknames.get(4);
        }
        if (nicknames.size() > 5) {
            row[POKE_6_INDEX] = nicknames.get(5);
        }

        row[NAME_INDEX] = team.name();

        return String.join(",",row);
    }
}
