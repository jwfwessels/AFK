/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jw
 */
public class RootWindow extends JFrame implements ActionListener
{

    //TODO; define components
    Dimension dim;
    ArrayList games;
    private JPanel contentPane;
    private MenuPanel menuPanel;
    private ArrayList<GamePanel> gamePanels;

    public void start()
    {
        //TODO; currently a bit of a hack since this is not the main thread its called by main
        this.setTitle("AFK Arena");
        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
//        menuPanel.setVisible(false);
//        gamePanel.setVisible(false);
    }

    public RootWindow()
    {
        games = new ArrayList();
        gamePanels = new ArrayList<GamePanel>();
        dim = new Dimension(1280, 786);
        this.setPreferredSize(dim);
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        contentPane.setSize(1280, 786);
        this.setContentPane(contentPane);
        //        LayoutManager layout = new RootWindow_Layout();
//        this.setLayout(layout);

        initComponents();
        addComponents();
        styleComponents();
        System.out.println("swing setup done!");
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
                System.exit(0);
            }
        });
    }

    private void initComponents()
    {
        try
        {
            menuPanel = new MenuPanel(this);
            menuPanel.setup();
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {

        try
        {
            contentPane.add(menuPanel);
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Adding components to rootWindow failed:\n" + e);
        }
    }

    private void removeComponents()
    {
        try
        {
            Container c = this.getContentPane();
            c.removeAll();
        } catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Removing components to rootWindow failed:\n" + err);
        }
    }

    private void styleComponents()
    {
        try
        {
            this.getContentPane().setBackground(Color.BLUE);
//            menuPanel.setBackground(Color.RED);

        } catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Styling components for rootWindow failed:\n" + err);
        }
    }

    public void spawnGamePanel(DefaultListModel<String> lsSelectedModel, HashMap<String, String> botMap)
    {
        GraphicsEngine renderer = GraphicsEngine.createInstance(false);
        GameEngine engine = new Tokyo(renderer, lsSelectedModel, botMap);
        System.out.println("START ENGINE THREAD");
        new Thread(engine).start();

        games.add(engine);
        final Component glCanvas = renderer.getAWTComponent();
        System.out.println("glCanvas" + glCanvas.getName());
        final GamePanel gamePanel = new GamePanel(this);
        gamePanels.add(gamePanel);
        gamePanel.add(glCanvas, BorderLayout.CENTER);
        //vvvvv this is horrible
//        gamePanel.addComponentListener(new ComponentAdapter()
//        {
//            @Override
//            public void componentResized(ComponentEvent e)
//            {
//                super.componentResized(e);
//                glCanvas.setSize(gamePanel.getSize());
//            }
//        });
        //^^^^^ this is horrible
        contentPane.add(gamePanel);
        engine.startGame();



        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.next(contentPane);

        contentPane.invalidate();
        contentPane.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
