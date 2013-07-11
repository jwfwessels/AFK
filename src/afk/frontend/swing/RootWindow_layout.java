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
public class RootWindow_layout implements LayoutManager
{

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
        dim.width = 200 + insets.left + insets.right;
        dim.height = 200 + insets.top + insets.bottom;

        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        Dimension dim = new Dimension(0, 0);
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
