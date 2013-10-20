package afk.frontend.swing;

import afk.game.GameListener;
import afk.game.GameMaster;
import afk.ge.tokyo.GameResult;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

/**
 *
 * @author Jw
 */
public class GamePanel extends JPanel
{
    
    RootWindow parent;
    Component glCanvas;
    JPanel view;
    JPanel pnlControls;
    JLabel fps;
    JToggleButton btnPlayPause;
    JButton btnFaster;
    JButton btnSlower;
    private JLabel lblSpeed;
    GameMaster gm;
    
    public GamePanel(RootWindow parent, GameMaster game)
    {
        this.parent = parent;
        gm = game;
        
        game.addGameListener(new GameListener() {

            @Override
            public void gameOver(GameResult result)
            {
                UUID winnerID = result.getWinner();
                String winner = (winnerID == null) ? "Nobody" : gm.getRobotName(winnerID);
                JOptionPane.showMessageDialog(GamePanel.this.parent, winner + " won!");
            }
        });

        LayoutManager layout = new GamePanel_Layout();
        this.setLayout(layout);
    }
    
    void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
    }
    
    private void initComponents()
    {
        fps = new JLabel("FPS: x");
        glCanvas = gm.getAWTComponent();
        view = new JPanel();
        pnlControls = new JPanel();
        btnPlayPause = new JToggleButton("press");
        btnFaster = new JButton(">>");
        btnSlower = new JButton("<<");
        lblSpeed = new JLabel("speed: " + (int) gm.getGameSpeed());
    }
    
    private void addComponents()
    {
        view.setLayout(new ViewPanel_Layout());
        view.add(glCanvas);
        
        pnlControls.add(fps);
        pnlControls.add(btnSlower);
        pnlControls.add(btnPlayPause);
        pnlControls.add(btnFaster);
        pnlControls.add(lblSpeed);
        add(view);
        add(pnlControls);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents()
    {
        fps.setOpaque(false);
        fps.setBounds(0, 0, 50, 25);
        lblSpeed.setBackground(Color.LIGHT_GRAY);
        btnPlayPause.setSelected(true);
        btnPlayPause.setText("Pause");
        
        ActionListener playPauseAction = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String state;
                if (btnPlayPause.isSelected())
                {
                    btnPlayPause.setText("Play");
                    state = "Puased";
                    
                } else
                {
                    btnPlayPause.setText("Pause");
                    state = "Playing";
                }
                gm.playPause();
            }
        };
        
        btnPlayPause.addActionListener(playPauseAction);
        btnPlayPause.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "playPause");
        btnPlayPause.getActionMap().put("playPause", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String state;
                if (btnPlayPause.isSelected())
                {
                    btnPlayPause.setText("Play");
                    state = "Puased";
                } else
                {
                    btnPlayPause.setText("Pause");
                    state = "Playing";
                }
                btnPlayPause.setSelected(!btnPlayPause.isSelected());
                gm.playPause();
            }
        });
        
        
        AbstractAction increaseSpeed = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gm.increaseSpeed();
                setLblSpeed();
            }
        };
        
        btnFaster.addActionListener(increaseSpeed);
        btnFaster.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "increaseSpeed");
        btnFaster.getActionMap().put("increaseSpeed", increaseSpeed);
        
        
        AbstractAction decreaseSpeed = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gm.decreaseSpeed();
                setLblSpeed();
            }
        };
        
        btnSlower.addActionListener(decreaseSpeed);
        btnSlower.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "increaseSpeed");
        btnSlower.getActionMap().put("increaseSpeed", decreaseSpeed);
    }
    
    private void setLblSpeed()
    {
        float speed = gm.getGameSpeed();
        if (speed >= 1)
        {
            lblSpeed.setText("speed: " + (int) speed);
        } else
        {
            lblSpeed.setText("speed: " + 1 + "/" + (int) (1 / speed));
        }
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
                int num2 = 0;
                Component c;

                //HudPanel;

                c = parent.getComponent(0);
                if (c.isVisible())
                {
                    c.setBounds(insets.left, insets.top, (int) w, ((int) h - 50));
                    
                    glCanvas.setSize(w, h);
                    num2 += c.getSize().height;
                    System.out.println("dim1:   " + insets.left + "   " + insets.top + "   " + (int) w + " " + ((int) h - 50));
                }

                //pnlControls

                c = parent.getComponent(1);
                if (c.isVisible())
                {
                    c.setBounds(insets.left, (insets.top + num2), (int) w, (int) 50);
                    System.out.println("dim2:   " + insets.left + "   " + (insets.top + num2) + "   " + (int) w + " " + (50));
                }
            }
        }
    }
    
    class ViewPanel_Layout implements LayoutManager
    {
        
        int panelWidth = view.getWidth();
        int panelHeight = view.getHeight();
        
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
            Dimension dim = new Dimension(600, 800);
            return dim;
        }
        
        @Override
        public void layoutContainer(Container parent)
        {
            Insets insets = parent.getInsets();
            
            int w = parent.getSize().width;
            int h = parent.getSize().height;
            
            int numW = 0;
            int numH = 0;
            int hVal;
            int wVal;
            Component c;

            //pnlCanvas

            c = parent.getComponent(0);
            if (c.isVisible())
            {
                c.setBounds(0, 0, w, h);
            }
            
        }
    }
}
