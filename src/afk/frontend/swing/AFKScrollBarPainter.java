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
public class AFKScrollBarPainter extends SynthPainter
{
    @Override
    public void paintScrollBarBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {

    }
    
    @Override
    public void paintScrollBarBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {
        
    }
    
    @Override
    public void paintScrollBarBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        
    }
    
    @Override
    public void paintScrollBarBorder(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {
        
    }
    
    @Override
    public void paintScrollBarThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {        
        Color col1 = UIManager.getColor("ScrollBar.third");
        g.setColor(col1);
        g.fillRect(x, y, w, h); 
    }
    
    @Override
    public void paintScrollBarThumbBorder(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("ScrollBar.second"));
        g2.drawRect(x, y, w-1, h-1);
    }
    
    @Override
    public void paintScrollBarTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        
        Color col1 = UIManager.getColor("ScrollBar.sixth");
        g.setColor(col1);
        g.fillRect(x, y, w, h); 
    }
    
    @Override
    public void paintScrollBarTrackBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {

        Color col1 = UIManager.getColor("ScrollBar.sixth");
        g.setColor(col1);
        g.fillRect(x, y, w, h);
    }
    
    @Override
    public void paintScrollBarTrackBorder(SynthContext context, Graphics g, int x, int y, int w, int h)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("ScrollBar.fifth"));
        g2.drawRect(x, y, w-1, h-1);
    }
    
    @Override
    public void paintScrollBarTrackBorder(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation)
    {
        Graphics2D g2 = (Graphics2D) g;
        
        Stroke borderStroke;
        borderStroke = new BasicStroke(1);
        
        g2.setStroke(borderStroke);
        g2.setPaint(UIManager.getColor("ScrollBar.fifth"));
        g2.drawRect(x, y, w-1, h-1);
    }
}
