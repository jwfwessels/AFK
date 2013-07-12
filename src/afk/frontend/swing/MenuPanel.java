/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Jw
 */
public class MenuPanel extends JPanel implements ActionListener
{
    //TODO; define components + parent ref

    public MenuPanel(RootWindow parent)
    {
        //TODO; set layout
        //TODO; call init, add, style
    }

    private void initComponents()
    {
        //TODO; instatiate components
        //TODO; run components setup(init & add), set visible 
    }

    private void addComponents()
    {
        //TODO; get container, add components to container.
    }

    private void removeComponents()
    {
        //TODO;
    }

    private void styleComponents()
    {
        //TODO; set colours of components(here if you can)
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class MenuPanel_Layout implements LayoutManager
    {

        @Override
        public void addLayoutComponent(String name, Component comp)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeLayoutComponent(Component comp)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Dimension preferredLayoutSize(Container parent)
        {
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
