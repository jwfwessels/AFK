/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

/**
 *
 * @author Jw
 */
public interface GameEngine {

    public void startGame();

//    public void playPause();

    public float getSpeed();

    public void increaseSpeed();

    public void decreaseSpeed();

    public void setState(int i, String msg);
}
