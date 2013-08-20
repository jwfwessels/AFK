package afk;

import java.awt.Component;

/**
 *
 * @author Daniel
 */
public interface GameCoordinator
{
    /**
     * Gets the AWT Component for this game.
     * @return the AWT component for viewing the game.
     */
    public Component getAWTComponent();
}
