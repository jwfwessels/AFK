package afk.game;

import afk.bot.RobotEngine;
import afk.bot.RobotException;
import afk.bot.RobotLoader;
import afk.bot.london.London;
import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class AFKGameCoordinator implements GameCoordinator {

    private GameEngine gameEngine;
    private GraphicsEngine gfxEngine;
    private RobotEngine botEngine;
    private List<String> participants;
    private Collection<GameListener> listeners = new ArrayList<GameListener>();

    public AFKGameCoordinator(RobotLoader botLoader, List<String> participants) {
        this.participants = participants;
        botEngine = new London(botLoader);
        gfxEngine = new Athens(false);
        gameEngine = new Tokyo(gfxEngine, botEngine);
    }

    @Override
    public Component getAWTComponent() {
        return gfxEngine.getAWTComponent();
    }

    @Override
    public void start() throws RobotException {
        UUID[] ids = new UUID[participants.size()];

        int i = 0;
        for (String participant : participants) {
            ids[i] = botEngine.addRobot(participant);
            ++i;
        }

        gameEngine.startGame(ids);
    }

    @Override
    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeGameListener(GameListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void playPause() {
        gameEngine.playPause();
    }

    @Override
        public long getGameSpeed(){
        return gameEngine.getSpeed();
    }
    
    @Override
    public void increaseSpeed(){
        gameEngine.increaseSpeed();
    }

    @Override
    public void decreaseSpeed(){
        gameEngine.decreaseSpeed();
    }
}
