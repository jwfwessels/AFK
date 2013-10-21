/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
/**
 *
 * @author Jessica
 */
public class AFKPanelPainter extends SynthPainter
{
    @Override
    public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) 
    { 
        Color start = UIManager.getColor("Panel.first");
        Color end = UIManager.getColor("Panel.second");
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint grPaint = new GradientPaint((float) (x + w / 2), (float) y, start, (float) w / 2, (float) h, end);
        g2.setPaint(grPaint);
        g2.fillRect(x, y, w, h);
        g2.setPaint(null);
        
    }
    
    @Override
    public void paintPanelBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        /*Graphics2D g2 = (Graphics2D) g;
        Color temp = UIManager.getColor("Panel.first");
        g2.setPaint(temp);
        g2.fillRect(x, y, w, h);
        g2.setPaint(null);*/

    }
}
