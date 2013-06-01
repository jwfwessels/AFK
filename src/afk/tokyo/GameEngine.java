/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import java.util.ArrayList;

/**
 *
 * @author Jw
 */
public abstract class GameEngine
{

    protected abstract void loadResources();

    protected abstract void gameLoop();

    protected abstract void updateGame();

    protected abstract void render();
}
