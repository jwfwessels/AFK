package afk.frontend.swing.postgame;

import afk.bot.Robot;
import afk.game.TournamentGameResult;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class TournamentTree extends JComponent
{

    public static final Color BG_COLOUR = new Color(0x444444);
    private TournamentGameResult result;
    private Robot[][][] robots;
    public static final int ICON_SIZE = 48;
    public static final int ICON_PADDING = 10;
    public static final int GROUP_PADDING = 20;
    public static final int ROUND_PADDING = 50;

    public TournamentTree(TournamentGameResult result)
    {
        this.result = result;
        robots = result.getGroups();
    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;

        g.setBackground(BG_COLOUR);
        g.clearRect(0, 0, getWidth(), getHeight());
        
        BufferedImage[][] groupImages = new BufferedImage[robots.length][];
        int[] roundWidths = new int[robots.length];
        int[] roundHeights = new int[robots.length];
        
        for (int i = 0; i < robots.length; i++)
        {
            roundWidths[i] = 0;
            roundHeights[i] = GROUP_PADDING;
            groupImages[i] = new BufferedImage[robots[i].length];
            for (int j = 0; j < robots[i].length; j++)
            {
                groupImages[i][j] = drawGroup(robots[i][j]);
                int w = groupImages[i][j].getWidth();
                if (w > roundWidths[i])
                    roundWidths[i] = w;
                roundHeights[i] += GROUP_PADDING + groupImages[i][j].getHeight();
            }
        }
        
        int x = ROUND_PADDING;
        int y = 0;
        for (int i = 0; i < robots.length; i++)
        {
            for (int j = 0; j < robots[i].length; j++)
            {
                g.drawImage(groupImages[i][j], null,
                        x, y);
                y += groupImages[i][j].getHeight()+GROUP_PADDING;
            }
            x += roundWidths[i]+ROUND_PADDING;
            if (i < robots.length-1)
            {
                y = roundHeights[i]/2 - roundHeights[i+1]/2;
            }
        }
    }

    public BufferedImage drawGroup(Robot[] bots)
    {
        final int ICON_SPACE = (ICON_SIZE + ICON_PADDING);
        int w = (int) Math.ceil(bots.length * 0.5f) * ICON_SPACE + ICON_PADDING;
        int h = ICON_SPACE * 2 + ICON_PADDING;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

        Graphics2D g = img.createGraphics();

        try
        {
            int x, y;
            for (int i = 0; i < bots.length; i++)
            {
                y = (i/2)*ICON_SPACE;
                x = (i%2)*ICON_SPACE;
                
                // TODO: debug
                g.setColor(Color.MAGENTA);
                g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
            }
        } finally
        {
            g.dispose();
        }

        return img;
    }
}
