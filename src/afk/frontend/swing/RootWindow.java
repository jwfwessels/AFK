/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import afk.bot.london.London;
import afk.gfx.athens.Athens;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        super();
        this.setBounds(0, 0, 100, 100);
        LayoutManager layout = new RootWindow_Layout(this);
        this.setLayout(layout);

        games = new ArrayList();
        gamePanels = new ArrayList<GamePanel>();

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
  
        } 
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Intitializing components for rootWindow failed:\n" + e);
        }
    }

    private void addComponents()
    {
        //RobotConfigPanel pan = new RobotConfigPanel(this);
        try
        {
            contentPane.add(menuPanel);
            //contentPane.add(pan);
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

    public void spawnGamePanel(London botEngine)
    {
        // TODO: reciefe BotEngine from MenuPanal and psass it to TokYo's COnstructor
        GraphicsEngine renderer = new Athens(false);
        GameEngine engine = new Tokyo(renderer, botEngine);
        System.out.println("START ENGINE THREAD");
        new Thread(engine).start();

        games.add(engine);

        final GamePanel gamePanel = new GamePanel(this, renderer);
        gamePanel.setup();
        gamePanels.add(gamePanel);
        contentPane.add(gamePanel);
        engine.startGame();



        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.next(contentPane);
        //hack to get awt keyEvents to register
        gamePanel.glCanvas.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }
    
    public void showConfigPanel()
    {
        RobotConfigPanel configPanel = new RobotConfigPanel(this);
        contentPane.add(configPanel);
        
        CardLayout cl = (CardLayout)contentPane.getLayout();
        cl.next(contentPane);
        
        configPanel.requestFocus();
        
        contentPane.invalidate();
        contentPane.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
