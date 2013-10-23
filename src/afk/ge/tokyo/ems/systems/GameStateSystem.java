/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.game.GameListener;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.GameState;
import afk.ge.tokyo.ems.components.ScoreBoard;
import afk.ge.tokyo.ems.nodes.ControllerNode;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Jw
 */
public class GameStateSystem implements ISystem
{

    Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<ControllerNode> nodes = engine.getNodeList(ControllerNode.class);
        ScoreBoard scoreboard = engine.getGlobal(ScoreBoard.class);
        GameState gameState = engine.getGlobal(GameState.class);

        int size = nodes.size();
        if (size <= 1)
        {
            if (size == 1)
            {
                gameState.winner = nodes.get(0).controller.id;
                Integer score = scoreboard.scores.get(gameState.winner);
                score += scoreboard.scores.size()-2;
                scoreboard.scores.put(gameState.winner, score);
            } else
            {
                gameState.winner = null;
            }
            gameState.gameOver = true;
        }

    }

    @Override
    public void destroy()
    {
    }
}
