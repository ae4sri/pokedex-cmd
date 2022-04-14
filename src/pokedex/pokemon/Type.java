package pokedex.pokemon;

/**
 * Class to represent Pokemon type.
 */
public class Type {


    private final String type;

    /**
     * Object initializer to create a Type.
     * @param GivenType The string of the type to base the Type off; if it's not valid, type will be empty.
     */
    public Type(String GivenType) {
        if (validateType(GivenType)) {
            this.type = GivenType;
        } else {
            this.type = "";
        }
    }

    /**
     * Get the Type as a String.
     * @return A string representation of the type.
     */
    public String getType() {
        return type;
    }

    /**
     * Validate a given type.
     * @param type The type to validate.
     * @return A boolean; true if the given type is valid, false otherwise.
     */
    public static Boolean validateType(String type) {
        return (type.equals("Normal") || type.equals("Fire") || type.equals("Water") || type.equals("Grass") ||
                type.equals("Electric") || type.equals("Ice") || type.equals("Fighting") || type.equals("Poison") ||
                type.equals("Ground") || type.equals("Flying") || type.equals("Psychic") || type.equals("Bug") ||
                type.equals("Rock") || type.equals("Ghost") || type.equals("Dark") || type.equals("Dragon") ||
                type.equals("Steel") || type.equals("Fairy"));
    }

    /**
     * Modify String representation of object
     * @return A string representing the type of the object.
     */
    @Override
    public String toString() {
        String representation = type;
        if (type.equals("")) {
            representation = "No Type";
        }
        return representation;
    }

}
