package afk.frontend.swing.postgame;

import afk.bot.Robot;
import afk.game.GameResult;
import afk.game.TournamentGame;
import afk.game.TournamentGameResult;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author daniel
 */
public class TournamentTree extends JComponent
{

    public static final Color BG_COLOUR = new Color(0x444444);
    private final Font FONT = new Font("Myriad Pro", Font.BOLD, 14);
    private TournamentGameResult result;
    private Robot[][][] robots;
    private Robot[] robotsThrough;
    private GameResult[][] results;
    private int[][] nextGroups;
    public static final int ICON_SIZE = 48;
    public static final int ICON_PADDING = 10;
    public static final int GROUP_PADDING = 20;
    public static final int ROUND_PADDING = 50;
    private int roundNumber;
    private int gameNum;

    public TournamentTree(TournamentGameResult result)
    {
        this.result = result;
        this.robots = result.getGroups();
        this.robotsThrough = result.getRobotsThrough();
        this.roundNumber = robots.length - 1;
        this.gameNum = result.getCurrentGame();
    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;

        g.setBackground(BG_COLOUR);
        g.clearRect(0, 0, getWidth(), getHeight());

        ArrayList<BufferedImage[]> groupImages = new ArrayList<BufferedImage[]>();
        ArrayList<Integer> roundWidths = new ArrayList<Integer>();
        ArrayList<Integer> roundHeights = new ArrayList<Integer>();

        // generate images for existing groups
        for (int i = 0; i < robots.length; i++)
        {
            int rw = 0;
            int rh = GROUP_PADDING;
            BufferedImage[] gi = new BufferedImage[robots[i].length];
            for (int j = 0; j < robots[i].length; j++)
            {
                gi[j] = drawGroup(robots[i][j]);
                int w = gi[j].getWidth();
                if (w > rw)
                {
                    rw = w;
                }
                rh += GROUP_PADDING + gi[j].getHeight();
            }
            roundWidths.add(rw);
            roundHeights.add(rh);
            groupImages.add(gi);
        }


        int botsLeft = robots[robots.length - 1].length * 2;
        // You may THINK this if statement says
        // "If there are 2 bots and AND if there are 2 bots"
        // but really its something else entirely.
        
        // talk to past Dan, he'll know
        if (botsLeft == 2 && robots[robots.length - 1][0].length == 2)
        {
            botsLeft = 1;
        }

        // generate images for future matches
        int rtIndex = 0;
        while (botsLeft > 1)
        {
            int[] groups = TournamentGame.calculateGroupSizes(botsLeft);

            BufferedImage[] gi = new BufferedImage[groups.length];

            int rw = 0;
            int rh = GROUP_PADDING;
            for (int i = 0; i < groups.length; i++)
            {
                Robot[] bots = new Robot[groups[i]];
                for (int j = 0; j < groups[i] && rtIndex < robotsThrough.length; j++, rtIndex++)
                {
                    bots[j] = robotsThrough[rtIndex];
                }
                gi[i] = drawGroup(bots);
                int w = gi[i].getWidth();
                if (w > rw)
                {
                    rw = w;
                }
                rh += GROUP_PADDING + gi[i].getHeight();
            }
            roundWidths.add(rw);
            roundHeights.add(rh);
            groupImages.add(gi);
            botsLeft = botsLeft == 2 ? 1 : groups.length * 2;

        }

        BufferedImage img;

        img = drawGroup(new Robot[]
        {
            rtIndex < robotsThrough.length ? robotsThrough[rtIndex] : null
        });
        groupImages.add(new BufferedImage[]
        {
            img
        });
        roundWidths.add(img.getWidth());
        roundHeights.add(2 * GROUP_PADDING + img.getHeight());


        int x = ROUND_PADDING;
        int y = 0;
        for (int i = 0; i < groupImages.size(); i++)
        {
            BufferedImage[] gi = groupImages.get(i);
            int top = y;
            y += GROUP_PADDING;
            for (int j = 0; j < gi.length; j++)
            {
                g.setColor(Color.GREEN);
                if (i == roundNumber && j == gameNum)
                {
                    g.fillRect(x, y, gi[j].getWidth(), gi[j].getHeight());
                } else
                {
                    g.drawRect(x, y, gi[j].getWidth(), gi[j].getHeight());
                }
                g.drawImage(gi[j], null,
                        x, y);
                y += gi[j].getHeight() + GROUP_PADDING;
            }
            x += roundWidths.get(i) + ROUND_PADDING;
            if (i < groupImages.size() - 1)
            {
                y = top + roundHeights.get(i) / 2 - roundHeights.get(i + 1) / 2;
            }
        }
    }

    public BufferedImage drawGroup(Robot[] bots)
    {
        final int ICON_SPACE = (ICON_SIZE + ICON_PADDING);
        int w = (int) Math.ceil(bots.length * 0.5f) * ICON_SPACE + ICON_PADDING;
        int h = bots.length == 1 ? ICON_PADDING * 2 + ICON_SIZE
                : ICON_SPACE * 2 + ICON_PADDING;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

        Graphics2D g = img.createGraphics();

        try
        {
            int x, y;
            for (int i = 0; i < bots.length; i++)
            {
                y = ICON_PADDING + (i % 2) * ICON_SPACE;
                x = ICON_PADDING + (i / 2) * ICON_SPACE;

                // TODO: debug
                if (bots[i] != null)
                {
                    drawBot(g, x, y, bots[i]);
                } else
                {
                    g.setColor(Color.MAGENTA);
                    g.drawRect(x, y, ICON_SIZE, ICON_SIZE);
                }
            }
        } finally
        {
            g.dispose();
        }

        return img;
    }

    private void drawBot(Graphics2D g, int x, int y, Robot robot)
    {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, ICON_SIZE, ICON_SIZE);

        g.setFont(FONT);

        g.setColor(Color.CYAN);
        g.drawString(robot.toString(), x, y);
    }
}
