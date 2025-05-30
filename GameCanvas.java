import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**     
        The GameCanvas class extends Jcomponent. It serves as the main
        object where other GameObjects and Entities are drawn. Additionally,
        UI and Map elements are drawn here as well.

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

public class GameCanvas extends JComponent {
    public static final int TILESIZE = 16;
    private static final int REFRESHINTERVAL = 16;
    private final int width, height;
    private final GameClient gameClient;
    private final ClientMaster clientMaster;
    private final ScheduledExecutorService renderLoopScheduler;
    public SceneHandler sceneHandler;
    public PlayerUI playerUI;
    private float screenOpacity;
    private int currentStage;

    private static BufferedImage[] sprites;

    /**
     * Calls the static setScreens() method
     */
    static {
        setScreens();
    }

    /**
     * Sets the different game screens statically
     */
    private static void setScreens() {
        try {
            BufferedImage gameOverScreen = ImageIO.read(GameCanvas.class.getResourceAsStream("resources/Misc/gameOver.png"));
            sprites = new BufferedImage[] {gameOverScreen};
        } catch (IOException e) {
            System.out.println("Exception in setScreens()" + e);
        }
    }

    /**
     * Creates a GameCanvas instance with width and height. Insantiates a gameClient,
     * a clientMaster, playerUI, scenehandler.
     * @param width the canvas' width
     * @param height the canvas' height
     */
    public GameCanvas(int width, int height){
        this.width = width;
        this.height = height;
        renderLoopScheduler = Executors.newSingleThreadScheduledExecutor();
        clientMaster = new ClientMaster();
        gameClient = new GameClient(clientMaster);
        setPreferredSize(new Dimension(width, height));
        playerUI = new PlayerUI();
        sceneHandler = new SceneHandler();
        currentStage = -1;
    }   

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);

        Player userPlayer = clientMaster.getUserPlayer();
        if (sceneHandler.getIsScenePlaying()){
            sceneHandler.drawScene(g2d, width, height, currentStage);
        }
        else if (clientMaster.getIsGameOver()){
            if (screenOpacity < 1f){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, screenOpacity));
                screenOpacity += 0.005f;
            } 
            g2d.drawImage(sprites[0], 0, 0, width, height, null);

        }
        else if ( (userPlayer == null) || (clientMaster.getCurrentRoom() == null) ){
            //Temporary Background
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);

        } 
        else{
            
            // Set the background/outside of the room
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, width, height);

            int scaleFactor = 2;
            g2d.scale(scaleFactor, scaleFactor);
            
            int screenX = (800/ (2 * scaleFactor) - userPlayer.getWidth() / (2 * scaleFactor));
            int screenY = (600/ (2 * scaleFactor) - userPlayer.getHeight() / (2 * scaleFactor));

            int cameraX = userPlayer.getWorldX() - screenX;
            int cameraY = userPlayer.getWorldY() - screenY;

            Room currentRoom = clientMaster.getCurrentRoom();
            currentRoom.draw(g2d, cameraX, cameraY);
            
            // Draw room doors
            for (Door door : currentRoom.getDoorsArrayList()) {
                door.draw(g2d, cameraX, cameraY);
            }

            // Draw enemies, projectiles, other players

            synchronized (clientMaster.getEntities()) {
                // Sort by z index for proper rendering
                ArrayList<Entity> sortedEntitiesByZ = new ArrayList<>(clientMaster.getEntities());
                sortedEntitiesByZ.sort(Comparator.comparingInt(Entity::getZIndex));

                for (Entity entity : sortedEntitiesByZ)    
                    entity.draw(g2d, entity.getWorldX() - userPlayer.getWorldX() + screenX, entity.getWorldY()- userPlayer.getWorldY() + screenY);    
            }
            
            //Draw current user's player
            userPlayer.draw(g2d, screenX, screenY); //CHANGE 50 BY ACTUAL ASSET SIZE

            //Draw UI elements
            playerUI.drawPlayerUI(g2d, clientMaster, scaleFactor);

            //Draw scenes
            if(currentStage < clientMaster.getCurrentStage()){
                currentStage++;
                sceneHandler.setIsScenePlaying(true);
            }
        }
    }

    /**
     * Gets a reference to gameClient
     * @return the GameClient object assigned to the canvas
     */
    public GameClient getGameClient(){
        return gameClient;
    }

     /**
     * Gets a reference to gameClient
     * @return the GameClient object assigned to the canvas
     */
    public SceneHandler getSceneHandler(){
        return sceneHandler;
    }


    /**
     * Calls repaint on this GameCanvas every REFRESHINTERVAL milliseconds
     */
    public void startRenderLoop(){
        //Since putting Thread.sleep in a loop as necessary for this Loop is bad, use ScheduledExecutorService instead
        final Runnable renderLoop = this::repaint;
        renderLoopScheduler.scheduleAtFixedRate(renderLoop, 0, REFRESHINTERVAL, TimeUnit.MILLISECONDS);
    }
}
