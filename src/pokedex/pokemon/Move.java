package pokedex.pokemon;

/**
 * Class to represent a Pokemon's Move as an object.
 */
public class Move {

    /**
     * Name of the move.
     */
    private final String name;

    /**
     * Description of the move.
     */
    private String description;

    /**
     * Type of the move.
     */
    private final Type type;

    /**
     * Object initializer for Move.
     * @param name Name of the move.
     * @param description Description of the move.
     */
    public Move(String name,String description, Type type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    /**
     * Get name of the Pokemon.
     * @return Name of the Pokemon.
     */
    public String name() { return name; }

    /**
     * Get the description of the Pokemon.
     * @return Description of the Pokemon.
     */
    public String description() { return description; }

    /**
     * Get type of the move.
     * @return The type of the move.
     */
    public Type getType() { return type; }

    /**
     * Change the description of the Pokemon.
     * @param newDescription The new description of the Pokemon.
     */
    public void changeDescription(String newDescription) { description = newDescription; }

    /**
     * Modify String representation of a Move.
     * @return String representation of a Move.
     */
    @Override
    public String toString() {
        return String.format("""
               %1$s: %2$s
               Type: %3$s\040\040\040\040\040
               """,name,description,type);
    }
}
