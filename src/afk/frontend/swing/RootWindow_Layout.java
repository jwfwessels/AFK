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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * 
 * @author Jw
 */
public class RootWindow_Layout implements LayoutManager
{
    int panelWidth = 800;
    int panelHeight = 600;
    int panelWidthMin = 800;
    int panelHeightMax = 600;

    public RootWindow_Layout(Container parent)
    {
        
        //set window dimention constraints
//        parent.setPreferredSize(new Dimension(panelWidth, panelHeight));
//        parent.setMinimumSize(new Dimension(640, 480));
    }

    @Override
    public void addLayoutComponent(String name, Component comp)
    {
    }

    @Override
    public void removeLayoutComponent(Component comp)
    {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(800, 600);

        Insets insets = parent.getInsets();
        dim.width = 0 + insets.left + insets.right;
        dim.height = 0 + insets.top + insets.bottom;

        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(800, 600);
        return dim;
    }

    @Override
    public void layoutContainer(Container parent)
    {
        Insets insets = parent.getInsets();

        int w = parent.getSize().width;
        int h = parent.getSize().height;

        int num1 = 0;
        Component c;
    }
}
