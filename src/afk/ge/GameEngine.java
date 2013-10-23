package afk.ge;

/**
 *
 * @author Jw
 */
public interface GameEngine {

    public void startGame();
    
    public void stopGame();

    public void playPause();

    public float getSpeed();

    public void increaseSpeed();

    public void decreaseSpeed();
}
