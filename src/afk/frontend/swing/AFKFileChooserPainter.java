/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.Transparency;
import javax.swing.JFileChooser;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 *
 * @author Jessica
 */
public class AFKFileChooserPainter extends SynthPainter
{
    @Override
    public void paintFileChooserBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        JFileChooser temp = (JFileChooser)context.getComponent();  
    }
    
    @Override
    public void paintFileChooserBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        
    }
}
