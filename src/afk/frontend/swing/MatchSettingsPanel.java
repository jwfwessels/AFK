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
import java.awt.LayoutManager;

/**
 *
 * @author Jessica
 */
public class MatchSettingsPanel 
{
    RootWindow root;
    //COMPONENTS!!!
    
    public MatchSettingsPanel(RootWindow _root)
    {
        root = _root;  
        setup();
    }
    
    public void initComponents()
    {
                
    }
    
    public void addComponents()
    {
        
    }
    
    public void styleComponents()
    {
        
    }

    private void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
    }
    
    class MatchSettingsPanel_Layout implements LayoutManager
    {
        int panelWidth = 1280;
        int panelHeight = 786;
        @Override
        public void addLayoutComponent(String name, Component comp) 
        {
            
        }

        @Override
        public void removeLayoutComponent(Component comp) 
        {
            
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) 
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void layoutContainer(Container parent) 
        {
            
        }
    }    
}
