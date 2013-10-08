/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.frontend.swing.config.RobotConfigPanel;
import afk.game.GameCoordinator;
import afk.bot.RobotException;
import afk.frontend.Frontend;
import java.awt.CardLayout;
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
            configPanel = new RobotConfigPanel(this);
            menuPanel.setup();
  
        } 
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {
        try
        {
            contentPane.add(menuPanel, "menu");
            contentPane.add(configPanel, "config");
        } 
        catch (Exception e)
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
        } 
        catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Removing components from rootWindow failed:\n" + err);
        }
    }

    private void styleComponents()
    {
        try
        {
//            this.getContentPane().setBackground(Color.BLUE);
        } 
        catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Styling components for rootWindow failed:\n" + err);
        }
    }


    @Override
    public void showGame(GameCoordinator gameCoordinator)
    {
        gamePanel = new GamePanel(this, gameCoordinator);

        gamePanel.setup();
        contentPane.add(gamePanel, "game");

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.show(contentPane, "game");
        //hack to get awt keyEvents to register
        gamePanel.glCanvas.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }
    
    public void showConfigPanel()
    {        
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, "config");
        
        configPanel.requestFocus();
        
        contentPane.invalidate();
        contentPane.validate();
        
        configPanel.loadConfig();
    }
    
    public void recallMenuPanel()
    {
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.show(contentPane, "menu");
        
        menuPanel.requestFocus();
        
        contentPane.invalidate();
        contentPane.validate();
    }

    @Override
    public void showError(String message)
    {
        menuPanel.showError(message);
    }

    @Override
    public void showWarning(String message)
    {
        menuPanel.showError(message);
    }

    @Override
    public void showMessage(String message)
    {
        menuPanel.showError(message);
    }

    @Override
    public void showAlert(String message)
    {
        JOptionPane.showMessageDialog(rootPane, message, "Alert!", JOptionPane.ERROR_MESSAGE);
    }
}
