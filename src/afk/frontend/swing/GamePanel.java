/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.gfx.GraphicsEngine;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
        this.setLayout(new BorderLayout());
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
        fps = new JLabel("1");
        gfxEngine.setFPSComponent(fps);
        fps.setBounds(640, 0, 100, 100);
        glCanvas.setBounds(0, 0, 1280, 786);
        hudLayer = new JLayeredPane();
//        hudLayer.setLayout(new BorderLayout());
        hudLayer.setSize(parent.getSize());
        System.out.println("size2" + hudLayer.getSize().toString());
//        btnStartMatch = new JButton("Start");
        //TODO; instatiate components
        //TODO; run components setup(init & add), set visible 
    }

    private void addComponents()
    {

        hudLayer.add(glCanvas, new Integer(0));
        hudLayer.add(fps, new Integer(1));
//        hudLayer.add(glCanvas, JLayeredPane.DEFAULT_LAYER);
//        hudLayer.add(fps, JLayeredPane.PALETTE_LAYER);
        add(hudLayer, BorderLayout.CENTER);
//        add(btnStartMatch);
        //TODO; get container, add components to container.
    }

    private void removeComponents()
    {
        //TODO;
    }

    private void styleComponents()
    {
        fps.setOpaque(false);
//        glCanvas.requestFocus();
//        this.setBackground(Color.BLUE);
        //TODO; set colours of components(here if you can)
//        glCanvas.addMouseListener(new MouseAdapter()
//        {
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                glCanvas.requestFocus();
//            }
//        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class GamePanel_Layout implements LayoutManager
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
