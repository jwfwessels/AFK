/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

import java.util.UUID;

/**
 *
 * @author Jw
 */
public interface GameEngine
{

    public void startGame(UUID[] participants);
    public void playPause();
    
}
