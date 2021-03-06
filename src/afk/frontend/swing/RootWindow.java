/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.frontend.swing;

import afk.bot.Robot;
import afk.frontend.swing.config.RobotConfigPanel;
import afk.game.GameMaster;
import afk.frontend.Frontend;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

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
    
    /*private static void initLookAndFeel() 
    {
        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
        
        try 
        {
            lookAndFeel.load(RootWindow.class.getResourceAsStream(LAF_FILE), RootWindow.class);
            UIManager.setLookAndFeel(lookAndFeel);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        System.out.println("SYNTH INIT DONE");
    }*/
    
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
        //initLookAndFeel();
        try
        {
//            this.getContentPane().setBackground(Color.BLUE);
            
        } catch (Exception err)
        {
            JOptionPane.showMessageDialog(null, "Styling components for rootWindow failed:\n" + err);
        }
    }

    public void destroyMe(Component card)
    {
        System.out.println("component.destroyMe: " + card);
        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.removeLayoutComponent(card);
        contentPane.remove(card);
    }

    public void showPanel(JPanel panel, String name)
    {
        contentPane.add(panel, name);

        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.show(contentPane, name);
        panel.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }

    public void showGame(GameMaster game)
    {
        gamePanel = new GamePanel(this, game);

        showPanel(gamePanel, "game");

    }

    public void destroyGame()
    {
        if (gamePanel != null)
        {
            destroyMe(gamePanel);
        }
    }

    public void showConfigPanel(Robot robot)
    {

        configPanel = new RobotConfigPanel(this);
        showPanel(configPanel, "config");

        configPanel.loadConfig(robot);
    }

    /**
     * This method switches the active card in the frames card layout to the
     * manuPanel. Furthermore it removes and destroys the previous panel that
     * was being displayed
     *
     * @param card the previous panel being displayed
     */
    public void recallMenuPanel(Component card)
    {
        menuPanel.recalled();

        CardLayout cl = (CardLayout) contentPane.getLayout();
        destroyMe(card);
        cl.show(contentPane, "menu");

        menuPanel.requestFocus();

        contentPane.invalidate();
        contentPane.validate();
    }
}
