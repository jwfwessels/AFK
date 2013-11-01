/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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

import afk.game.GameMaster;
import afk.game.GameResult;
import java.awt.Color;
import java.awt.FontMetrics;
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

    private final int pad = 10;
    private GameResult result;
    private GameMaster gm;
    private UUID[] bots;

    public RobotScoreList(GameResult result, GameMaster gm)
    {
        this.result = result;
        this.gm = gm;

        bots = result.getTop();
    }

    private class Score
    {

        String name;
        int score;

        public Score(String name, int score)
        {
            this.name = name;
            this.score = score;
        }
    }

    @Override
    public void paint(Graphics g1)
    {
        Graphics2D g = (Graphics2D) g1;

        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        String[] numbers = new String[bots.length];
        String[] scoreStr = new String[bots.length];
        int[] scoreWidths = new int[bots.length];
        String[] names = new String[bots.length];
        int w = 0;
        int h = fm.getHeight();
        for (int i = 0; i < bots.length; i++)
        {
            numbers[i] = "" + (i + 1);
            names[i] = gm.getRobotName(bots[i]);
            scoreStr[i] = "" + result.getScore(bots[i]);
            int x = (int) fm.getStringBounds(numbers[i], g).getWidth();
            if (x > w)
            {
                w = x;
            }
            scoreWidths[i] = (int) fm.getStringBounds(scoreStr[i], g).getWidth();
        }
        g.drawLine(w + pad*2, 0, w + pad*2, getHeight());

        for (int i = 0; i < bots.length; i++)
        {
            int y = pad + i * (h+pad*2);
            int textY = y+fm.getAscent();
            g.drawString(numbers[i], pad, textY);
            g.drawString(names[i], w+pad*3, textY);
            g.drawString(scoreStr[i], getWidth()-pad-scoreWidths[i], textY);
            g.drawLine(0, y+h+pad, getWidth(), y+h+pad);
        }
    }
}
