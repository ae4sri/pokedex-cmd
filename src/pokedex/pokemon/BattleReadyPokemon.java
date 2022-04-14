package pokedex.pokemon;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The class to represents a Pokemon's instance in a team. Inherits from Pokemon and contains attributes
 * specific to a single Pokemon instance.
 */
public final class BattleReadyPokemon extends Pokemon {

    /**
     * The Pokemon's nickname.
     */
    private String nickname;

    /**
     * The Pokemon's list of moves.
     */
    private final ArrayList<Move> moves;

    /**
     * Store all nicknames in a hashset for validation purposes.
     */
    private static final HashSet<String> nicknamesSet = new HashSet<>();

    /**
     * Object initializer for PokemonInTeam.
     *
     * @param nickname     Optional nickname for the Pokemon.
     * @param name         The actual name of the Pokemon.
     * @param hp           Health points of the Pokemon.
     * @param atk          Attack points of the Pokemon.
     * @param spatk        Special attack points of the Pokemon.
     * @param def          Defense points of the Pokemon.
     * @param spdef        Special defense points of the Pokemon.
     * @param speed        Speed points of the Pokemon.
     * @param description  Description of the Pokemon.
     * @param type1        First type of the Pokemon.
     * @param type2        Second type of the Pokemon.
     * @param initialMoves Moves to intiialize the Pokemon with.
     */
    public BattleReadyPokemon(String nickname, String name, int hp, int atk, int spatk, int def, int spdef, int speed,
                              String description, Type type1, Type type2, ArrayList<Move> initialMoves) {
        super(name, hp, atk, spatk, def, spdef, speed, description, type1, type2);

        this.nickname = nickname;

        if (initialMoves.size() > 4) {
            initialMoves = (ArrayList<Move>) initialMoves.subList(0, 4);
        }

        this.moves = initialMoves;
        // add list of moves
        nicknamesSet.add(nickname);
    }

    /**
     * Get nickname of a BattleReadyPokemon.
     *
     * @return The nickname of the BattleReadyPokemon the method is called on.
     */
    public String nickname() {
        return nickname;
    }

    /**
     * Get name and nickname of a BattleReadyPokemon in the form: name (nickname)
     * @return String with name and nickname of a BattleReadyPokemon.
     */
    public String nameAndNickName() {
        return nickname + " (" + name() + ")";
    }

    /**
     * Get a move from a BattleReadyPokemon.
     *
     * @param moveToGet The move number (1-4), where 1 is the first move, 2 is the second, etc.
     * @return The Move object corresponding to moveToGet. If the move doesn't exist, null is returned.
     */
    public Move getMove(int moveToGet) {
        if (moves.size() >= moveToGet) {
            return moves.get(moveToGet - 1);
        }
        return null;
    }

    public static boolean nicknameUsed(String name) {
        return nicknamesSet.contains(name);
    }

    /**
     * Add a Pokemon Move to a Pokemon's move list.
     *
     * @param move The move to add to an individual Pokemon instance.
     */
    public void addMove(Move move) {
        if (moves.size() < 4) {
            moves.add(move);
        }
    }

    /**
     * Swap a move for another in an individual Pokemon.
     *
     * @param oldMove Move to remove from the Pokemon.
     * @param newMove Move to add to the Pokemon.
     */
    public void swapMove(Move oldMove, Move newMove) {
        moves.remove(oldMove);
        moves.add(newMove);
    }

    /**
     * Allows a user to change the nickname of a Pokemon.
     *
     * @param newName The new nickname for the Pokemon.
     */
    public void changeNickname(String newName) {
        this.nickname = newName;
    }

    /**
     * Get list of moves from a BattleReadyPokemon.
     * @return ArrayList of Move objects.
     */
    public ArrayList<Move> getMoves() { return moves; }

    /**
     * Clone method for BattleReadyPokemon.
     * @return A clone of the current BattleReadyPokemon.
     */
    public BattleReadyPokemon clone() {
        return new BattleReadyPokemon(nickname(), name(), health(),
                attack(), specialatk(), defense(),specialdef(), speed(), description(),
                firstType(), secondType(), getMoves());
    }
    /**
     * Modify string representation of the object.
     * @return String representation of the PokemonInTeam object.
     */
    @Override
    public String toString() {
        String[] moveNames = new String[4];
        for (int i = 0; i < 4; i++) {
            if (i >= moves.size()) {
                moveNames[i] = ""; // empty string rather than null
            } else {
                moveNames[i] = moves.get(i).name();
            }
        }
        return String.format("""
                        Name: %1$s (%2$s)
                        Health: %3$s
                        Attack: %4$s    Special Attack: %5$s
                        Defense: %6$s   Special Defense: %7$s
                        Speed: %8$s \s
                        Type 1: %9$s    Type 2: %10$s
                        Description: %11$s
                        Move 1: %12$s   Move 2: %13$s
                        Move 3: %14$s   Move 4: %15$s
                                        """, nickname, this.name(), this.health(), this.attack(),
                this.specialatk(), this.defense(), this.specialdef(),
                this.speed(), this.firstType().getType(), this.secondType().getType(), this.description(),
                moveNames[0], moveNames[1], moveNames[2], moveNames[3]);
    }
}

