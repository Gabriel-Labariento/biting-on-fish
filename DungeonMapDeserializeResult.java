import java.util.*;

/**     
        The DungeonMapDeserializeResult is an class specifically
        designed to hold the result of DungeonMap.deserialize().
        It has three fields: a Room corresponding to the map's startRoom
        a HashMap that "maps" the an Id to a Room, 
        and an int that indicates the gameLevel of the current map

        @author Niles Tristan Cabrera (240828)
        @author Gabriel Matthew Labariento (242425)
        @version 20 May 2025

        We have not discussed the Java language code in our program
        with anyone other than my instructor or the teaching assistants
        assigned to this course.
        We have not used Java language code obtained from another student,
        or any other unauthorized source, either modified or unmodified.
        If any Java language code or documentation used in our program
        was obtained from another source, such as a textbook or website,
        that has been clearly noted with a proper citation in the comments
        of our program.
**/

public class DungeonMapDeserializeResult {
    private final Room startRoom;
    private HashMap<Integer, Room> allRooms = new HashMap<>();
    private int gameLevel;

    /**
     * Creates an instance of DungeonMapDeserializeResult with a Room and Hashmap fields
     * @param startRoom the dungeon's starting room
     * @param allRooms the HashMap mapping rooms to their ids
     * @param gameLevel
     */
    public DungeonMapDeserializeResult(Room startRoom, HashMap<Integer, Room> allRooms, int gameLevel){
        this.startRoom = startRoom;
        this.allRooms = allRooms;
        this.gameLevel = gameLevel;
    }

    /**
     * Gets a reference to the startRoom
     * @return the startRoom
     */
    public Room getStartRoom() {
        return startRoom;
    }

    /**
     * Gets the Hashmap of Ids to Rooms 
     * @return the allRooms HashMap
     */
    public HashMap<Integer, Room> getAllRooms() {
        return allRooms;
    }

    /**
     * Gets the gameLevel int
     * @return gameLevel int
     */
    public int getGameLevel(){
        return gameLevel;
    }
}