/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

/**
 *
 * @author Jessica
 */
public class AFKListPainter extends SynthPainter
{
    @Override
    public void paintListBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {  
        int state = context.getComponentState();
       // if((state & 32) == 0) //selected
        
            Graphics2D g2 = (Graphics2D) g;
            Color start = UIManager.getColor("List.first");
            Color end = UIManager.getColor("List.second");
            GradientPaint grPaint = new GradientPaint((float) (x + w / 2), (float) y, start, (float) w / 2, (float) h, end);

            g2.setPaint(grPaint);
            g2.fillRoundRect(x, y, w, h, 10, 10);
            g2.setPaint(null);    
    }
    
    public void paintListBorder(SynthContext context, Graphics g, int x, int y, int w, int h)    
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Paint borderPaint;
        borderPaint = new Color(1, 1, 1);
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("List.second"));
        g2.drawRect(x, y, w-1, h-1);
    }
}
