package pokedex.pokemon;

import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Math.min;

/**
 * Team class to represent a Pokemon team as an Object.
 */
public class Team {

    /**
     * Set of all team names (used for validation in the program).
     */
    private final static HashSet<String> teamNamesSet = new HashSet<>();

    /**
     * Length of the Pokemon team.
     */
    private final static int TEAM_LENGTH = 6;

    /**
     * User provided list of Pokemon in a team.
     */
    private final ArrayList<BattleReadyPokemon> team = new ArrayList<>();

    /**
     * The user provided team name.
     */
    private String name;

    /**
     * Initialize a Team object given a list of Pokemon.
     * @param name The team name for this particular team.
     */
    public Team(String name) {
        this.name = name;
        teamNamesSet.add(name);
    }

    /**
     * Get number of Pokemon in a team.
     * @return The number of Pokemon in a particular team.
     */
    public int numPokemon() { return team.size(); }

    /**
     * Get the name of the Pokemon team
     * @return The team's name.
     */
    public String name() { return name; }

    public static boolean nameTaken(String nameToCheck) {
        return teamNamesSet.contains(nameToCheck);
    }
    /**
     * Change name of a Team.
     * @param newName The name to change the team to.
     */
    public void changeName(String newName) {
        this.name = newName;
    }

    /**
     * Add a Pokemon to the team object.
     * @param pokemon The pokemon object to add.
     */
    public void addPokemon(BattleReadyPokemon pokemon) {
        if (team.size() < 6) {
            team.add(pokemon);
        }
    }

    /**
     * Swap a Pokemon in a team for another.
     * @param oldPokemon The Pokemon to replace.
     * @param newPokemon The Pokemon to replace oldPokemon with.
     */
    public void swapPokemon(BattleReadyPokemon oldPokemon, BattleReadyPokemon newPokemon) {
        team.remove(oldPokemon);
        team.add(newPokemon);
    }

    /**
     * Get all the Pokemon in a team.
     * @return Arraylist of all nicknames used in a team.
     */
    public ArrayList<String> getAllNicknames() {
        ArrayList<String> nicknames = new ArrayList<>();
        for (BattleReadyPokemon battleReadyPokemon : team) {
            nicknames.add(battleReadyPokemon.nickname());
        }
        return nicknames;
    }

    /**
     * Create string representation of the object.
     * @return String representation of a team.
     */
    @Override
    public String toString() {
        StringBuilder details = new StringBuilder("Team Name: " + name + "\n");

        for (int i = 0; i < team.size(); i++) {
                details.append(i+1).append(") ").append(team.get(i)).append("\n");
            }

        return details.toString();
    }

    /**
     * Find if there's a Pokemon in a team already given a name.
     * @param nameToCheck Check if a Pokemon with this name exists in the name.
     * @return A boolean, true if the team contains a Pokemon with nameToCheck, false otherwise.
     */
    public Boolean contains(String nameToCheck) {
        for (BattleReadyPokemon battleReadyPokemon : team) {
            if (battleReadyPokemon.name().equals(nameToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the current team instance contains any Pokemon.
     * @return A boolean; true if team has Pokemon, false otherwise.
     */
    public Boolean hasPokemon() {
        return team.size() > 0;
    }

    public void addPokemonList(ArrayList<BattleReadyPokemon> pokemonList) {
        for (int i = 0; i < min(pokemonList.size(),TEAM_LENGTH); i++) { // only get first 6 elements from team
            team.add(pokemonList.get(i));
        }
    }

}
