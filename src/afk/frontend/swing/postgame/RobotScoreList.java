package afk.frontend.swing.postgame;

import afk.game.GameMaster;
import afk.ge.tokyo.GameResult;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;
import javax.swing.JPanel;

/**
 *
 * @author daniel
 */
public class RobotScoreList extends JPanel
{

    private final int pad = 10;
    private GameResult result;
    private GameMaster gm;
    private Score[] scores;

    public RobotScoreList(GameResult result, GameMaster gm)
    {
        this.result = result;
        this.gm = gm;

        UUID[] ids = result.getParticipants();
        scores = new Score[ids.length];
        for (int i = 0; i < ids.length; i++)
        {
            scores[i] = new Score(
                    gm.getRobotName(ids[i]),
                    result.getScore(ids[i]));
        }
        Arrays.sort(scores, new Comparator<Score>()
        {
            @Override
            public int compare(Score o1, Score o2)
            {
                return Integer.compare(o2.score, o1.score);
            }
        });
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
        String[] numbers = new String[scores.length];
        String[] scoreStr = new String[scores.length];
        int[] scoreWidths = new int[scores.length];
        String[] names = new String[scores.length];
        int w = 0;
        int h = fm.getHeight();
        for (int i = 0; i < scores.length; i++)
        {
            numbers[i] = "" + (i + 1);
            names[i] = scores[i].name;
            scoreStr[i] = "" + scores[i].score;
            int x = (int) fm.getStringBounds(numbers[i], g).getWidth();
            if (x > w)
            {
                w = x;
            }
            scoreWidths[i] = (int) fm.getStringBounds(scoreStr[i], g).getWidth();
        }
        g.drawLine(w + pad*2, 0, w + pad*2, getHeight());

        for (int i = 0; i < scores.length; i++)
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
