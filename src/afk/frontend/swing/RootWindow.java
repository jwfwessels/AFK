/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private JPanel contentPane;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

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
//            menuPanel.setVisible(false);

            gamePanel = new GamePanel(this);
            gamePanel.setup();
//            menuPanel.setVisible(false);

            ////TODO: set visible 
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {

        try
        {
//            Container c = this.getContentPane();
            contentPane.add(menuPanel);
            contentPane.add(gamePanel);

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
            menuPanel.setBackground(Color.RED);
            gamePanel.setBackground(Color.BLACK);
        } catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Styling components for rootWindow failed:\n" + err);
        }
    }

    public void swapPanel()
    {
        if (menuPanel.isVisible())
        {
            menuPanel.setVisible(false);
            gamePanel.setVisible(true);
        } else
        {
            menuPanel.setVisible(true);
            gamePanel.setVisible(false);
        }

        contentPane.invalidate();
        contentPane.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
