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
import java.awt.Stroke;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

/**
 *
 * @author Jessica
 */
public class AFKTextFieldPainter extends SynthPainter
{
    @Override
    public void paintTextFieldBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
            Color col1 = UIManager.getColor("TextField.second");
            g.setColor(col1);
            g.fillRect(x, y, w, h);
    }
        
    @Override
    public void paintTextFieldBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("TextField.first"));
        g2.drawRect(x, y, w-1, h-1);
    }
}
