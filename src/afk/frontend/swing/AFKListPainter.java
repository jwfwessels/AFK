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

        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("List.second"));
        g2.drawRect(x, y, w-1, h-1);
    }
}
