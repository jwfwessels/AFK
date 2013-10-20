package afk.frontend.swing.postgame;

import afk.bot.RobotConfigManager;
import afk.ge.tokyo.GameResult;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.UUID;
import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class RobotScoreList extends JComponent
{

    private GameResult result;
    private RobotConfigManager config;

    public RobotScoreList(GameResult result, RobotConfigManager config)
    {
        this.result = result;
        this.config = config;
    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;
        
        g.setBackground(Color.WHITE);
        
        g.setColor(Color.BLACK);
        UUID[] ids = result.getParticipants();
        for (int i = 0; i < ids.length; i++)
        {
            g.drawString((i+1)+". "+config.getProperty(ids[i], "name"), 10, 10+i*10);
        }
    }
}
