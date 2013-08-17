/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.gfx.GraphicsEngine;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Jw
 */
public class GamePanel extends JPanel implements ActionListener
{

    RootWindow parent;
    GraphicsEngine gfxEngine;
    Component glCanvas;
    JLayeredPane hudLayer;
    JLabel fps;

    //    private JButton btnStartMatch;
    //TODO; define components + parent ref
    public GamePanel(RootWindow parent, GraphicsEngine renderer)
    {
        this.parent = parent;
        gfxEngine = renderer;
        glCanvas = gfxEngine.getAWTComponent();
        System.out.println("glCanvas" + glCanvas.getName());
        LayoutManager layout = new GamePanel_Layout();
        this.setLayout(layout);
//        this.setLayout(new BorderLayout());
        //TODO; set layout
    }

    void setup()
    {
        //TODO; call init, add, style
        initComponents();
        addComponents();
        styleComponents();
    }

    private void initComponents()
    {
        //TODO; instatiate components
        fps = new JLabel("1");
        gfxEngine.setFPSComponent(fps);
        hudLayer = new JLayeredPane();
        System.out.println("size2" + hudLayer.getSize().toString());
//        btnStartMatch = new JButton("Start");
    }

    private void addComponents()
    {

        hudLayer.add(glCanvas, new Integer(0));
        hudLayer.add(fps, new Integer(1));
//        hudLayer.add(glCanvas, JLayeredPane.DEFAULT_LAYER);
//        hudLayer.add(fps, JLayeredPane.PALETTE_LAYER);
        add(hudLayer);
//        add(btnStartMatch);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents()
    {
        fps.setOpaque(false);
        fps.setBounds(0, 0, 50, 25);
        hudLayer.setBackground(Color.BLUE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class GamePanel_Layout implements LayoutManager
    {

        int panelWidth = 800;
        int panelHeight = 600;
        int w = 0;
        int h = 0;

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

            dim.width = panelWidth + insets.left + insets.right;
            dim.height = panelHeight + insets.top + insets.bottom;

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
            if ((w != parent.getSize().width) || (h != parent.getSize().height))
            {
                w = parent.getSize().width;
                h = parent.getSize().height;

            int num1 = 0;
            Component c;

            //HudPanel;

            c = parent.getComponent(0);
            if (c.isVisible())
            {
                c.setBounds(insets.left + num1, insets.top, (int) w, (int) h);

                glCanvas.setSize(w, h);
            }
            }
        }
    }
}
