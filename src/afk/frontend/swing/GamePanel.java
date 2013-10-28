/*
 * Copyright (c) 2013 Triforce
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

import afk.game.GameMaster;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    JPanel view;
    JPanel pnlControls;
    Component glPanel = new JPanel();
    JLabel fps;
    JToggleButton btnPlayPause;
    JButton btnFaster;
    JButton btnSlower;
    JButton btnBack;
    private JLabel lblSpeed;
    GameMaster gm;
    ImageIcon pauseIcon;
    ImageIcon playIcon;

    public GamePanel(RootWindow parent, GameMaster game)
    {
        this.parent = parent;
        gm = game;

        LayoutManager layout = new GamePanel_Layout();
        this.setLayout(layout);
        setup();
    }

    private void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
    }

    private void initComponents()
    {
        fps = new JLabel("FPS: x");
        view = new JPanel();
        glPanel = gm.getAWTComponent();
        pnlControls = new JPanel();

        ImageIcon faster = createImageIcon("icons/FasterIcon.png", "Speed Up");
        ImageIcon slower = createImageIcon("icons/SlowerIcon.png", "Slow Down");

        pauseIcon = createImageIcon("icons/PauseIcon.png", "Play");
        playIcon = createImageIcon("icons/PlayIcon.png", "Pause");

        btnPlayPause = new JToggleButton();
        btnPlayPause.setIcon(pauseIcon);
        btnFaster = new JButton();
        btnSlower = new JButton();
        btnFaster.setIcon(faster);
        btnSlower.setIcon(slower);
        lblSpeed = new JLabel("speed: " + (int) gm.getGameSpeed());
        btnBack = new JButton("Back");

        lblSpeed.setName("label");
    }

    protected ImageIcon createImageIcon(String path, String description)
    {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, description);
        } else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void addComponents()
    {
        view.setLayout(new ViewPanel_Layout());
        view.add(glPanel);

        pnlControls.add(fps);
        pnlControls.add(btnSlower);
        pnlControls.add(btnPlayPause);
        pnlControls.add(btnFaster);
        pnlControls.add(lblSpeed);
        pnlControls.add(btnBack);
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
        //btnPlayPause.setText("Pause");

        ActionListener playPauseAction = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                playPausePressed();
            }
        };

        btnPlayPause.addActionListener(playPauseAction);
        btnPlayPause.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "playPause");
        btnPlayPause.getActionMap().put("playPause", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                btnPlayPause.setSelected(!btnPlayPause.isSelected());
                playPausePressed();
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

        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gm.stop();
                parent.recallMenuPanel(GamePanel.this);
            }
        });
    }

    private void playPausePressed()
    {
        if (btnPlayPause.isSelected())
        {
            btnPlayPause.setIcon(pauseIcon);
        } else
        {
            btnPlayPause.setIcon(playIcon);
        }
        gm.playPause();
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

                    glPanel.setSize(w, h);
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
