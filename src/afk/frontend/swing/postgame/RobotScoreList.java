package afk.frontend.swing.postgame;

import afk.game.GameMaster;
import afk.ge.tokyo.GameResult;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.UUID;
import javax.swing.JPanel;

/**
 *
 * @author daniel
 */
public class RobotScoreList extends JPanel
{

    private GameResult result;
    private GameMaster gm;

    public RobotScoreList(GameResult result, GameMaster gm)
    {
        this.result = result;
        this.gm = gm;
    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;
        
        g.setBackground(Color.YELLOW);
        
        g.setColor(Color.BLACK);
        UUID[] ids = result.getParticipants();
        for (int i = 0; i < ids.length; i++)
        {
            g.drawString((i+1)+". "+gm.getRobotName(ids[i]), 10, 10+i*10);
        }
    }
}
