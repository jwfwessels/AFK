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
 package afk.ge.tokyo.ems.systems;

import afk.bot.RobotConfigManager;
import afk.ge.AbstractFilter;
import afk.ge.MultiplyFilter;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Life;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.nodes.BotInfoHUDNode;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;

/**
 *
 * @author daniel
 */
public class BotInfoHUDSystem implements ISystem
{

    private final Font FONT = new Font("Myriad Pro", Font.BOLD, 14);
    private Engine engine;
    private Map<UUID, Float> lastLife = new HashMap<UUID, Float>();
    private Map<String, BufferedImage> icons = new HashMap<String, BufferedImage>();
    private RobotConfigManager config;
    public static final int PANEL_WIDTH = 273;
    public static final int PANEL_HEIGHT = 68;
    public static final int PANEL_PADDING = 10;
    public static final int ICON_SIZE = 48;
    public static final int INNER_OFFSET = 5;
    public static final int BAR_HEIGHT = 8;
    public static final int BAR_PADDING = 1;
    public static final Color HEALTH_COLOUR = new Color(0xEE2524);
    public static final Color ENERGY_COLOUR = new Color(0x47B649);
    public static final Color TEXT_COLOUR = new Color(0xD1D2D4);
    public static final Color BG_COLOUR = new Color(0x66333132, true);

    public BotInfoHUDSystem(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<BotInfoHUDNode> nodes = engine.getNodeList(BotInfoHUDNode.class);
        for (BotInfoHUDNode node : nodes)
        {
            Entity bot = node.infoHud.bot;
            Controller controller = bot.getComponent(Controller.class);
            Renderable renderable = bot.getComponent(Renderable.class);
            Color color = new Color(renderable.colour.getX(), renderable.colour.getY(), renderable.colour.getZ());
            Life life = bot.getComponent(Life.class);
            float last = getLastLife(controller.id);

            if (last != life.hp)
            {
                node.image.setImage(createBotInfoPanel(controller.id, life, color));
                lastLife.put(controller.id, life.hp);
            }
        }
    }

    private float getLastLife(UUID id)
    {
        Float i = lastLife.get(id);
        if (i == null)
        {
            i = Float.NaN;
            lastLife.put(id, i);
        }
        return i;
    }

    private BufferedImage createBotInfoPanel(UUID id, Life life, Color colour)
    {
        String str = config.getProperty(id, "name");

        BufferedImage image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        g.setFont(FONT);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.setColor(BG_COLOUR);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        try
        {
            g.setColor(Color.BLACK);
            g.drawImage(getIcon(config.getProperty(id, "type")),
                    new MultiplyFilter(colour), PANEL_PADDING, PANEL_PADDING);
        } catch (IOException ex)
        {
            ex.printStackTrace(System.err);
            g.setColor(Color.MAGENTA);
            g.fillRoundRect(PANEL_PADDING, PANEL_PADDING, ICON_SIZE, ICON_SIZE, 2, 2);
        }

        int x = PANEL_PADDING + ICON_SIZE + INNER_OFFSET;
        int y = PANEL_HEIGHT - PANEL_PADDING - INNER_OFFSET;

        int barWidth = PANEL_WIDTH - x - PANEL_PADDING;

        y -= BAR_HEIGHT;
        drawBar(g, x, y, barWidth, BAR_HEIGHT, 1, ENERGY_COLOUR);

        y -= INNER_OFFSET + BAR_HEIGHT;
        drawBar(g, x, y, barWidth, BAR_HEIGHT, life.hp / life.maxHp, HEALTH_COLOUR);

        y -= INNER_OFFSET;
        g.setColor(TEXT_COLOUR);
        g.drawString(str, x, y);

        g.dispose();

        return image;
    }

    private void drawBar(Graphics2D g, int x, int y, int width, int height, float percent, Color colour)
    {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(colour);
        int fill = (int) ((width - BAR_PADDING * 2) * percent);
        g.fillRect(x + BAR_PADDING, y + BAR_PADDING, fill, height - BAR_PADDING * 2);
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

    @Override
    public void destroy()
    {
    }
}
