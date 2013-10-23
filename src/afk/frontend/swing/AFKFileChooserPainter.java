/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Graphics;
import javax.swing.JFileChooser;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
import javax.swing.UIManager;

/**
 *
 * @author Jessica
 */
public class AFKFileChooserPainter extends SynthPainter
{
    @Override
    public void paintFileChooserBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        /*JFileChooser temp = (JFileChooser)context.getComponent();
        temp.set*/
        
    }
    
    @Override
    public void paintFileChooserBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        
    }
}
