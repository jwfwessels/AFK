/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.bot.Robot;
import afk.frontend.swing.config.RobotConfigPanel;
import afk.game.GameMaster;
import afk.bot.RobotException;
import afk.frontend.Frontend;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jw
 */
public class RootWindow extends JFrame implements Frontend
{

    //TODO; define components
    Dimension dim;
    private JPanel contentPane;
    private MenuPanel menuPanel;
    private RobotConfigPanel configPanel;
    private GamePanel gamePanel;

    @Override
    public void showMain()
    {
        this.setTitle("AFK Arena");
        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    public RootWindow()
    {
        super();
        this.setBounds(0, 0, 100, 100);
        LayoutManager layout = new RootWindow_Layout(this);
        this.setLayout(layout);

        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        this.setContentPane(contentPane);

        initComponents();
        addComponents();
        styleComponents();
        System.out.println("swing setup done!");
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
//                dispose();
                removeComponents();
                System.exit(0);
//                setDefaultCloseOperation(EXIT_ON_CLOSE);
                /*Alternately: a popup window could prevent the user from 
                 * accidentally closing the application.
                 */
            }
        });
    }

    private void initComponents()
    {
        try
        {
            menuPanel = new MenuPanel(this);
            menuPanel.setup();
            menuPanel.loadExistingRobots();

        } catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {
        try
        {
            contentPane.add(menuPanel, "menu");
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
            JOptionPane.showMessageDialog(null, "Removing components from rootWindow failed:\n" + err);
        }
    }

    private void styleComponents()
    {
        try
        {
//            this.getContentPane().setBackground(Color.BLUE);
        } catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Styling components for rootWindow failed:\n" + err);
        }
    }

    @Override
    public void showGame(GameMaster game)
    {
        gamePanel = new GamePanel(this, game);

        gamePanel.setup();
        contentPane.add(gamePanel, "game");

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.show(contentPane, "game");
        //hack to get awt keyEvents to register
        gamePanel.glCanvas.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }

    public void showConfigPanel(Robot robot)
    {

        configPanel = new RobotConfigPanel(this);
        contentPane.add(configPanel, "config");

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.show(contentPane, "config");

        configPanel.requestFocus();

        contentPane.invalidate();
        contentPane.validate();

        configPanel.loadConfig(robot);
    }

    /**
     * This method switches the active card in the frames card layout to the manuPanel.
     * Furthermore it removes and destroys the previous panel that was being displayed
     * @param card the previous panel being displayed
     */
    public void recallMenuPanel(Component card)
    {
        
        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.removeLayoutComponent(card);
        contentPane.remove(card);
        cl.show(contentPane, "menu");

        menuPanel.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }
}
