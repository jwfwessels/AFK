/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.frontend.swing.postgame;

import afk.bot.Robot;
import afk.game.GameResult;
import afk.game.TournamentGame;
import afk.game.TournamentGameResult;
import afk.ge.MultiplyFilter;
import afk.ge.tokyo.Tokyo;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author daniel
 */
public class TournamentTree extends JComponent
{

    public static final Color BG_COLOUR = new Color(0x444444);
    public static final Color TEXT_COLOUR = new Color(0xD1D2D4);
    public static final Color LINE_COLOUR = new Color(0xFFFFFF);
    public static final Color FILL_COLOUR = new Color(0x333333);
    private final Font FONT = new Font("Myriad Pro", Font.PLAIN, 12);
    private TournamentGameResult result;
    private Robot[][][] robots;
    private Robot[] robotsThrough;
    private GameResult[][] results;
    private int[][] nextGroups;
    public static final int ICON_SIZE = 48;
    public static final int ICON_PADDING = 25;
    public static final int TEXT_BASELINE = 15;
    public static final int GROUP_PADDING = 20;
    public static final int ROUND_PADDING = 50;
    private int roundNumber;
    private int gameNum;
    private int mx, my;
    private float dx, dy;
    private float zoom = 1.0f;
    private Map<String, BufferedImage> icons = new HashMap<String, BufferedImage>();

    public TournamentTree(TournamentGameResult result)
    {
        this.result = result;
        this.robots = result.getGroups();
        this.robotsThrough = result.getRobotsThrough();
        this.roundNumber = robots.length - 1;
        this.gameNum = result.getCurrentGame();

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mx = e.getX();
                my = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                int nx = e.getX();
                int ny = e.getY();
                dx += nx - mx;
                dy += ny - my;
                mx = nx;
                my = ny;
                repaint();
            }
        });

        addMouseWheelListener(new MouseAdapter()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                double r = e.getPreciseWheelRotation();
                float x1 = (e.getX()-dx)/zoom;
                float y1 = (e.getY()-dy)/zoom;
                if (r > 0)
                {
                    zoom *= 0.9f * r;
                } else if (r < 0)
                {
                    zoom *= 1.1f * -r;
                    if (zoom > 1)
                    {
                        zoom = 1;
                    }
                }
                float x2 = (e.getX()-dx)/zoom;
                float y2 = (e.getY()-dy)/zoom;
                dx += (x2-x1)*zoom;
                dy += (y2-y1)*zoom;
                repaint();
            }
        });

    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;

        g.setBackground(BG_COLOUR);
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

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
                gi[j] = drawGroup(robots[i][j], i == roundNumber && j == gameNum);
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
                gi[i] = drawGroup(bots, false);
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
        }, false);
        groupImages.add(new BufferedImage[]
        {
            img
        });
        roundWidths.add(img.getWidth());
        roundHeights.add(2 * GROUP_PADDING + img.getHeight());

        AffineTransform originalTransform = g.getTransform();
        g.translate(dx, dy);
        g.scale(zoom, zoom);

        int x = ROUND_PADDING;
        int y = 0;
        for (int i = 0; i < groupImages.size(); i++)
        {
            BufferedImage[] gi = groupImages.get(i);
            int top = y;
            y += GROUP_PADDING;
            g.setColor(LINE_COLOUR);
            if (i < groupImages.size()-1)
            {
                g.drawLine(
                        x+roundWidths.get(i), y+gi[0].getHeight()/2,
                        x+roundWidths.get(i)+ROUND_PADDING/2, y+gi[0].getHeight()/2);
                g.drawLine(
                        x+roundWidths.get(i)+ROUND_PADDING/2, y+gi[0].getHeight()/2,
                        x+roundWidths.get(i)+ROUND_PADDING/2, top + roundHeights.get(i) / 2);
            }
            for (int j = 0; j < gi.length; j++)
            {
                g.setColor(FILL_COLOUR);
                if (i == roundNumber && j == gameNum)
                {
                    g.fillRect(x, y, gi[j].getWidth(), gi[j].getHeight());
                }
                g.drawImage(gi[j], null,
                        x, y);
                y += gi[j].getHeight() + GROUP_PADDING;
            }
            g.setColor(LINE_COLOUR);
            if (i < groupImages.size()-1)
            {
                g.drawLine(
                        x+roundWidths.get(i), y-gi[gi.length-1].getHeight()/2-GROUP_PADDING-1,
                        x+roundWidths.get(i)+ROUND_PADDING/2, y-gi[gi.length-1].getHeight()/2-GROUP_PADDING-1);
                g.drawLine(x+roundWidths.get(i)+ROUND_PADDING/2, top + roundHeights.get(i) / 2,
                        x+roundWidths.get(i)+ROUND_PADDING, top + roundHeights.get(i) / 2);
                g.drawLine(
                        x+roundWidths.get(i)+ROUND_PADDING/2, y-gi[gi.length-1].getHeight()/2-GROUP_PADDING-1,
                        x+roundWidths.get(i)+ROUND_PADDING/2, top + roundHeights.get(i) / 2);
            }
            x += roundWidths.get(i) + ROUND_PADDING;
            if (i < groupImages.size() - 1)
            {
                y = top + (roundHeights.get(i) / 2 - roundHeights.get(i + 1) / 2);
            }
        }

        g.setTransform(originalTransform);
    }

    public BufferedImage drawGroup(Robot[] bots, boolean current)
    {
        final int ICON_SPACE = (ICON_SIZE + ICON_PADDING);
        int w = (int) Math.ceil(bots.length * 0.5f) * ICON_SPACE + ICON_PADDING;
        int h = bots.length == 1 ? ICON_PADDING * 2 + ICON_SIZE
                : ICON_SPACE * 2 + ICON_PADDING;

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);

        Graphics2D g = img.createGraphics();
        
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        try
        {
            int x, y;
            for (int i = 0; i < bots.length; i++)
            {
                y = ICON_PADDING + (i % 2) * ICON_SPACE;
                x = ICON_PADDING + (i / 2) * ICON_SPACE;

                if (bots[i] != null)
                {
                    drawBot(g, x, y, bots[i], current ? i : -1);
                } else
                {
                    g.setColor(FILL_COLOUR);
                    g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
                }
            }
        } finally
        {
            g.dispose();
        }

        return img;
    }

    private void drawBot(Graphics2D g, int x, int y, Robot robot, int i)
    {
        g.setColor(LINE_COLOUR);
        g.fillRoundRect(x-1, y-1, ICON_SIZE+2, ICON_SIZE+2, 4, 4);
        BufferedImageOp op = i < 0 ? null
                : new MultiplyFilter(new Color(
                    Tokyo.BOT_COLOURS[i].getX(),
                    Tokyo.BOT_COLOURS[i].getY(),
                    Tokyo.BOT_COLOURS[i].getZ()));
        g.setColor(Color.MAGENTA);
        try
        {
            g.drawImage(getIcon(robot.getConfigManager().getProperty(robot.getId(), "type")), op, x, y);
        } catch (IOException ex)
        {
            ex.printStackTrace(System.err);
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, ICON_SIZE, ICON_SIZE);
        }

        g.setFont(FONT);
        FontMetrics fm = g.getFontMetrics(FONT);

        g.setColor(TEXT_COLOUR);
        String text = robot.toString();

        g.drawString(text, x + ICON_SIZE / 2 - fm.stringWidth(text) / 2, y + ICON_SIZE + TEXT_BASELINE);
    }
    
    private BufferedImage getIcon(String type) throws IOException
    {
        BufferedImage icon = icons.get(type);
        if (icon == null)
        {
            icon = ImageIO.read(new File("textures/icons/" + type + ".png"));
            icons.put(type, icon);
        }
        return icon;
    }
}
