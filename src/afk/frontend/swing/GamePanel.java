/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.game.Game;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
    JLayeredPane hudLayer;
    JPanel pnlControls;
    JLabel fps;
    JToggleButton btnPlayPause;
    JButton btnFaster;
    JButton btnSlower;
    private JLabel lblSpeed;
    Game gm;

    public GamePanel(RootWindow parent, Game game)
    {
        this.parent = parent;
        gm = game;
        
        System.out.println("glCanvas" + glCanvas.getName());
        LayoutManager layout = new GamePanel_Layout();
        this.setLayout(layout);
//        this.setLayout(new BorderLayout());
        //TODO; set layout
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
        // FIXME: find a new way to show FPS
        //gfxEngine.setFPSComponent(fps);
        //hudLayer = new JLayeredPane();
        //System.out.println("size2" + hudLayer.getSize().toString());
        glCanvas = gm.getAWTComponent();
        pnlControls = new JPanel();
        btnPlayPause = new JToggleButton("press");
        btnFaster = new JButton(">>");
        btnSlower = new JButton("<<");
        lblSpeed = new JLabel("speed: " + (int) gm.getGameSpeed());
    }

    private void addComponents()
    {

        add(glCanvas);
        pnlControls.add(fps);
        pnlControls.add(btnSlower);
        pnlControls.add(btnPlayPause);
        pnlControls.add(btnFaster);
        pnlControls.add(lblSpeed);
//        hudLayer.add(glCanvas, JLayeredPane.DEFAULT_LAYER);
//        hudLayer.add(fps, JLayeredPane.PALETTE_LAYER);
        //add(hudLayer);
        add(pnlControls);
//        add(btnStartMatch);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents()
    {
        fps.setOpaque(false);
        fps.setBounds(0, 0, 50, 25);
        hudLayer.setBackground(Color.BLUE);
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
                gm.gameStateChange(new String[]
                {
                    "PLAY_PAUSE",
                    state
                });
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
                gm.gameStateChange(new String[]
                {
                    "PLAY_PAUSE",
                    state
                });
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
                    c.setBounds(insets.left + num1, insets.top, (int) w, (int) (h - 50));

                    glCanvas.setSize(w, h);
                    num2 += c.getSize().height;
                }

                //pnlControls

                c = parent.getComponent(1);
                if (c.isVisible())
                {
                    c.setBounds(insets.left, insets.top + num2, (int) w, (int) 50);
                }
            }
        }
    }
}
