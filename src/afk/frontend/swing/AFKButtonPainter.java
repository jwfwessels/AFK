/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.BasicStroke;
import java.awt.Color;
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
public class AFKButtonPainter extends SynthPainter
{
    @Override
    public void paintButtonBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        Graphics2D g2 = (Graphics2D) g;
        Color start = UIManager.getColor("Button.first");
        Color end = UIManager.getColor("Button.second");
        GradientPaint grPaint = new GradientPaint((float) (x + w / 2), (float) y, start, (float) w / 2, (float) h, end);
        g2.setPaint(grPaint);
        g2.fillRect(x, y, w, h);
        g2.setPaint(null);
    }
    
    @Override
    public void paintButtonBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        System.out.println("SYNTH X:" + x);
        System.out.println("SYNTH Y" + y);
        System.out.println("SYNTH W" + w);
        System.out.println("SYNTH H" + h);
        
        Graphics2D g2 = (Graphics2D) g;
        
        Paint borderPaint;
        borderPaint = new Color(1, 1, 1);
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(2);
        
        g2.setStroke(borderStroke);
        g2.setPaint(borderPaint);
        g2.drawRect(x, y, w-1, h-1);
        /*Graphics2D g2 = (Graphics2D) g;
        Color temp = UIManager.getColor("Panel.first");
        g2.setPaint(temp);
        g2.setColor(temp);
        g2.fillRect(x, y, w, h);
        g2.setPaint(null);*/
    }
}
