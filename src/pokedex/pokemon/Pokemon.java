package pokedex.pokemon;

import java.util.HashSet;

/**
 * Class to represent a Pokemon as an object.
 */
public class Pokemon {

    /**
     * Static field to keep track of total # of Pokemon.
     */
    private static int indexCounter = 1;

    private final String name;
    private final int hp;
    private final int attack;
    private final int specialatk;
    private final int defense;
    private final int specialdef;
    private final int speed;
    private final String description;
    private final Type type1;
    private final Type type2;

    private static final HashSet<String> namesSet = new HashSet<>();
    /**
     * Initializer for Pokemon object.
     * @param name Name of Pokemon to be created.
     * @param hp HP stat of Pokemon to be created.
     * @param atk ATK stat of Pokemon to be created.
     * @param spatk Special Attack stat of Pokemon to be created.
     * @param def Defense stat of Pokemon to be created.
     * @param spdef Special Defense stat of Pokemon to be created.
     * @param speed Speed stat of Pokemon to be created.
     * @param description Description of Pokemon to be created.
     * @param type1 First type of Pokemon to be created.
     * @param type2 Second type of Pokemon to be created.
     */
    public Pokemon(String name, int hp, int atk, int spatk, int def, int spdef, int speed, String description,
                   Type type1, Type type2) {
        name.replace(",",""); // handle odd case in which name has commas, which would mess up reader

        if (name.equals("")) { // if name is empty, replace with "Pokemon <Index>".
            name = "Pokemon " + indexCounter;
        }

        this.name = name;
        this.hp = hp;
        this.attack = atk;
        this.specialatk = spatk;
        this.defense = def;
        this.specialdef = spdef;
        this.speed = speed;
        this.description = description;
        this.type1 = type1;
        this.type2 = type2;
        int id = indexCounter;
        indexCounter++; // have indexes for pokemon by order of creation

        namesSet.add(name);
    }

    /**
     * Get name of the Pokemon.
     * @return The name of the Pokemon.
     */
    public String name() { return name; }

    /**
     * Get health stat of the Pokemon.
     * @return The health stat of the Pokemon.
     */
    public int health() { return hp; }

    /**
     * Get attack stat of the Pokemon.
     * @return The attack stat of the Pokemon.
     */
    public int attack() { return attack; }

    /**
     * Get special attack stat of the Pokemon.
     * @return Special attack stat of the Pokemon.
     */
    public int specialatk() { return specialatk; }

    /**
     * Get defense stat of the Pokemon.
     * @return The defense stat of the Pokemon.
     */
    public int defense() { return defense; }

    /**
     * Get special defense stat of the Pokemon.
     * @return The special defense stat of the Pokemon.
     */
    public int specialdef() { return specialdef; }

    /**
     * Get speed stat of the Pokemon.
     * @return The speed stat of the Pokemon.
     */
    public int speed() { return speed; }

    /**
     * Get description of the Pokemon.
     * @return The description of the Pokemon.
     */
    public String description() { return description; }

    /**
     * Get first type of the Pokemon.
     * @return The first type of the Pokemon.
     */
    public Type firstType() { return type1; }

    /**
     * Get second type of the Pokemon.
     * @return The second type of the Pokemon.
     */
    public Type secondType() { return type2; }

    /**
     * Get the strength of a Pokemon (sum of the Pokemon's stats combined).
     * @return The strength of a Pokemon.
     */
    public int strength() {
        return hp+attack+specialatk+defense+specialdef+speed+hp;
    }

    /**
     * Check if a Pokemon with the given name exists. Used for validation to check if we can create a Pokemon with
     * a given name.
     * @param name The name to check for.
     * @return A boolean, true if there already exists a Pokemon with the given name, false otherwise.
     */
    public static boolean pokemonExists(String name) {
        return namesSet.contains(name);
    }

    /**
     * Create String representation of the Pokemon.
     * @return String representation of the Pokemon.
     */
    @Override
    public String toString() {
        return String.format("""
                Pokemon Name: %1$s
                Health: %2$s
                Attack: %3$s    Special Attack: %4$s
                Defense: %5$s   Special Defense: %6$s
                Speed: %7$s
                Description: %8$s
                Type 1: %9$s    Type 2: %10$s
                                """,name, hp, attack,
                specialatk, defense, specialdef,
                speed, description, type1.getType(), type2.getType());
    }
}
