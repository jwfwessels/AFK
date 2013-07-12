/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Color;
import java.awt.Container;
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
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public void start()
    {
        //TODO; currently a bit of a hack since this is not the main thread its called by main
        this.pack();
        this.setVisible(true);
    }

    public RootWindow()
    {
        this.setBounds(0, 0, 1280, 786);

        LayoutManager layout = new RootWindow_Layout();
        this.setLayout(layout);

        initComponents();
        addComponents();
        styleComponents();

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
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
            menuPanel.setVisible(true);

            gamePanel = new GamePanel(this);
            gamePanel.setup();

            ////TODO: set visible 
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {

        Container c = null;
        try
        {
            c = this.getContentPane();
            c.add(menuPanel);
            c.add(gamePanel);

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
            menuPanel.setBackground(Color.GRAY);
            menuPanel.setBackground(Color.BLACK);
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
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
