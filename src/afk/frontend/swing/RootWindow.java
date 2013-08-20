/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.GameCoordinator;
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
    ArrayList games;
    private JPanel contentPane;
    private MenuPanel menuPanel;
    private ArrayList<GamePanel> gamePanels;

    @Override
    public void showMain()
    {
        this.setTitle("AFK Arena");
        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    @Override
    public void showGame(GameCoordinator game)
    {
        final GamePanel gamePanel = new GamePanel(this, game);
        gamePanel.setup();
        gamePanels.add(gamePanel);
        contentPane.add(gamePanel);

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.next(contentPane);
        //hack to get awt keyEvents to register
        gamePanel.glCanvas.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }

    public RootWindow()
    {
        LayoutManager layout = new RootWindow_Layout(this);
        this.setLayout(layout);

        games = new ArrayList();
        gamePanels = new ArrayList<GamePanel>();

        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
//        contentPane.setSize(1280, 786);
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

    public void spawnGamePanel(GameCoordinator game) throws RobotException
    {
        GamePanel gamePanel = new GamePanel(this, game);

        games.add(gamePanel);

        gamePanel.setup();
        gamePanels.add(gamePanel);
        contentPane.add(gamePanel);
        game.start();

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.next(contentPane);
        //hack to get awt keyEvents to register
        gamePanel.glCanvas.requestFocus();

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
