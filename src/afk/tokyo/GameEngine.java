/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GraphicsEngine;

/**
 *
 * @author Jw
 */
public abstract class GameEngine implements Runnable
{

    protected GraphicsEngine gfxEngine;

    protected abstract void loadResources();

    public abstract void addEntity(AbstractEntity tankEntity);

    @Override
    public abstract void run();

    protected abstract void gameLoop();

    protected abstract void updateGame(boolean[] flags);

    protected abstract void render(float alpha);
}
