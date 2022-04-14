package pokedex;

import org.junit.jupiter.api.*;
import pokedex.pokemon.*;
import pokedex.util.Reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test different methods in the project.
 */
class PokemonTest {

    private BattleReadyPokemon brp1;
    private BattleReadyPokemon brp2;
    private BattleReadyPokemon brp3;

    private Team team1;
    private Team team2;

    @BeforeEach
    public void setUp() {
        // set up variables to use for test functions
        Pokemon poke1 = new Pokemon("Charizard", 78, 84, 109, 78, 85, 100,
                "Charizard description", new Type("Fire"), new Type("Flying"));

        Pokemon poke2 = new Pokemon("Pikachu", 35, 55, 50, 40, 50, 90,
                "Pikachu description", new Type("Electric"), new Type(""));

        Pokemon poke3 = new Pokemon("Mew", 100, 100, 100, 100, 100, 100,
                "Mew description", new Type("Psychic"), new Type(""));

        Pokemon poke4 = new Pokemon("Arcanine", 90, 110, 100, 80, 80, 95,
                "Arcanine description", new Type("Fire"), new Type("Flying"));

        Pokemon poke5 = new Pokemon("Alakazam", 55, 40, 135, 45, 95, 120,
                "Alakazam description", new Type("Fire"), new Type("Flying"));

        Pokemon poke6 = new Pokemon("Gengar", 60, 65, 130, 60, 75, 110,
                "Gengar description", new Type("Fire"), new Type("Flying"));

        Move move1 = new Move("Fire Blast", "The target is attacked with an intense blast of " +
                "all-consuming fire. This may also leave the target with a burn.", new Type("Fire"));

        Move move2 = new Move("Ice Beam", "The target is struck with an icy-cold beam of energy. This may " +
                "also leave the target frozen.", new Type("Ice"));

        Move move3 = new Move("Vine Whip", "The target is struck with slender, whiplike vines to inflict " +
                "damage.", new Type("Grass"));

        Move move4 = new Move("Thunderbolt", "A strong electric blast crashes down on the target. This may " +
                "also leave the target with paralysis.", new Type("Electric"));

        // initialize arraylists of moves to create some BattleReadyPokemon
        ArrayList<Move> moves1 = new ArrayList<>();
        ArrayList<Move> moves2 = new ArrayList<>();
        ArrayList<Move> moves3 = new ArrayList<>();
        ArrayList<Move> moves4 = new ArrayList<>();
        ArrayList<Move> moves5 = new ArrayList<>();
        ArrayList<Move> moves6 = new ArrayList<>();

        moves2.add(move1);
        moves2.add(move2);

        moves3.add(move1);
        moves3.add(move2);
        moves3.add(move3);
        moves3.add(move4);

        moves4.add(move3);

        moves5.add(move1);
        moves5.add(move3);

        moves6.add(move4);

        // set up BattleReadyPokemon
        brp1 = new BattleReadyPokemon("Alex","Charizard",78,84,109,78,85,
                100,  "Charizard description", new Type("Fire"), new Type("Flying"),
                moves1);
        brp2 = new BattleReadyPokemon("Ash","Pikachu",35,55,50,40,50,90,
                "Pikachu description", new Type("Electric"), new Type(""),moves2);
        brp3 = new BattleReadyPokemon("Mew One", "Mew",100,100,100,100,100,100,
                "Mew description", new Type("Psychic"), new Type(""), moves3);
        BattleReadyPokemon brp4 = new BattleReadyPokemon("Japan", "Arcanine", 90, 110, 100, 80, 80, 95,
                "Arcanine description", new Type("Fire"), new Type("Flying"), moves4);
        BattleReadyPokemon brp5 = new BattleReadyPokemon("Nine", "Alakazam", 55, 40, 135, 45, 95, 120,
                "Alakazam description", new Type("Fire"), new Type("Flying"), moves5);
        BattleReadyPokemon brp6 = new BattleReadyPokemon("Bounty", "Gengar", 60, 65, 130, 60, 75, 110,
                "Gengar description", new Type("Fire"), new Type("Flying"), moves6);

        // set up teams (need to put pokemon into an arraylist first)

        ArrayList<BattleReadyPokemon> list1 = new ArrayList<>(List.of(brp1));
        ArrayList<BattleReadyPokemon> list2 = new ArrayList<>(Arrays.asList(brp1,brp2,brp3, brp4, brp5, brp6));
        ArrayList<BattleReadyPokemon> list3 = new ArrayList<>(Arrays.asList(brp2,brp3, brp5));

        team1 = new Team("team1");
        team1.addPokemonList(list1);

        team2 = new Team("team2");
        team2.addPokemonList(list2);

        Team team3 = new Team("team3");
        team3.addPokemonList(list3);
    }
    // METHODS TO TEST:
    // - Type.validateType
    // - Team.addPokemon, Team.swapPokemon, Team.contains
    // - Test creating a Pokemon object, and making sure it has correct properties
    // - Pokemon.pokemonExists
    // - Move.changeDescription
    // - Test creating a BattleReadyPokemon, make sure it has correct properties
    // - BattleReadyPokemon Methods to test: getMove, addMove, swapMove, changeNickname

    /**
     * Test .validateNum() in Main
     */
    @Test
    void testValidateNum1() { //
        String testInput = "o"; // test with invalid string
        int maximum = 30;
        assertFalse(Main.validateNum(testInput,maximum));
    }

    @Test
    void testValidateNum2() {
        String testInput = "39"; // test with valid string below maximum
        int maximum = 40;

        assertTrue(Main.validateNum(testInput,maximum));
    }

    @Test
    void testValidateNum3() {
        String testInput = "2939"; // test with valid string above maximum
        int maximum = 2938;

        assertFalse(Main.validateNum(testInput,maximum));
    }

    /**
     * Test .contains() method in Team class
     */
    @Test
    void testContains() {
        assertTrue(team1.contains(brp1.name()));
    }

    @Test
    void testContains2() {
        assertTrue(team2.contains(brp2.name()));
    }

    /**
     * Test .addPokemon() method in Team Class
     */
    @Test
    void testAddPokemon() {
        int prevSize = team1.numPokemon();
        team1.addPokemon(brp2);
        int curSize = team1.numPokemon();

        assertEquals(prevSize + 1,curSize); //  make sure size is 1 greater after adding pokemon
        assertTrue(team1.contains(brp2.name())); // make sure pokemon with brp2s name is in the team
    }

    /**
     * Test .swapPokemon() method in Team Class
     */
    @Test
    void testSwapPokemon() {
        assertTrue(team1.contains(brp1.name())); // make sure team has brp1 in it
        team1.swapPokemon(brp1,brp3); // perform the swap
        assertFalse(team1.contains(brp1.name())); // make sure team no longer has brp1
        assertTrue(team1.contains(brp3.name())); // make sure team contains brp3 (swapped pokemon)
    }

    @Test
    void testCreatePokemon() {
        String name = "Charizard";
        String description = "A dragon.";
        int hp = 1;
        int attack = 2;
        int spattack = 3;
        int defense = 4;
        int spdefense = 5;
        int speed = 6;
        Type type1 = new Type("Fire");
        Type type2 = new Type("Water");

        Pokemon poke = new Pokemon(name,hp,attack,spattack,defense,spdefense,speed,description,type1,type2);

        assertEquals(poke.health(),hp);
        assertEquals(poke.attack(),attack);
        assertEquals(poke.specialatk(),spattack);
        assertEquals(poke.defense(),defense);
        assertEquals(poke.specialdef(),spdefense);
        assertEquals(poke.speed(),speed);
        assertEquals(poke.firstType().getType(), type1.getType());

    }

    @Test
    void testValidateType1() { // test a valid type
        assertTrue(Type.validateType("Fire"));
    }

    @Test
    void testValidateType2() { // test an invalid type
        assertFalse(Type.validateType(""));
    }

}