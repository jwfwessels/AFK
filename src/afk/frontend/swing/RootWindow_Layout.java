/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

    int panelWidth = 1280;
    int panelHeight = 786;
    int panelWidthMin = 640;
    int panelHeightMax = 480;

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
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 0 + insets.left + insets.right;
        dim.height = 0 + insets.top + insets.bottom;

        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(640, 480);
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
