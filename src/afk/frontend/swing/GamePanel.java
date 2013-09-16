/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.game.GameCoordinator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Jw
 */
public class GamePanel extends JPanel {

    RootWindow parent;
    Component glCanvas;
    JLayeredPane hudLayer;
    JPanel pnlControls;
    JLabel fps;
    JToggleButton btnPlayPause;
    GameCoordinator gm;

    public GamePanel(RootWindow parent, GameCoordinator gameCoordinator) {
        this.parent = parent;
        gm = gameCoordinator;
        glCanvas = gm.getAWTComponent();
        System.out.println("glCanvas" + glCanvas.getName());
        LayoutManager layout = new GamePanel_Layout();
        this.setLayout(layout);
//        this.setLayout(new BorderLayout());
        //TODO; set layout
    }

    void setup() {
        initComponents();
        addComponents();
        styleComponents();
    }

    private void initComponents() {
        fps = new JLabel("FPS: x");
        // FIXME: find a new way to show FPS
        //gfxEngine.setFPSComponent(fps);
        hudLayer = new JLayeredPane();
        System.out.println("size2" + hudLayer.getSize().toString());
        pnlControls = new JPanel();
        btnPlayPause = new JToggleButton("press");
    }

    private void addComponents() {

        hudLayer.add(glCanvas, new Integer(0));
        pnlControls.add(fps);
        pnlControls.add(btnPlayPause);
//        hudLayer.add(glCanvas, JLayeredPane.DEFAULT_LAYER);
//        hudLayer.add(fps, JLayeredPane.PALETTE_LAYER);
        add(hudLayer);
        add(pnlControls);
//        add(btnStartMatch);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents() {
        fps.setOpaque(false);
        fps.setBounds(0, 0, 50, 25);
        hudLayer.setBackground(Color.BLUE);

        btnPlayPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnPlayPause.isSelected()) {
                    btnPlayPause.setText("Play");
                } else {
                    btnPlayPause.setText("Pause");
                }
                gm.playPause();
            }
        });
    }

    class GamePanel_Layout implements LayoutManager {

        int panelWidth = 800;
        int panelHeight = 600;
        int w = 0;
        int h = 0;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            Dimension dim = new Dimension(0, 0);

            Insets insets = parent.getInsets();

            dim.width = panelWidth + insets.left + insets.right;
            dim.height = panelHeight + insets.top + insets.bottom;

            return dim;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            Dimension dim = new Dimension(0, 0);
            return dim;
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            if ((w != parent.getSize().width) || (h != parent.getSize().height)) {
                w = parent.getSize().width;
                h = parent.getSize().height;

                int num1 = 0;
                int num2 = 0;
                Component c;

                //HudPanel;

                c = parent.getComponent(0);
                if (c.isVisible()) {
                    c.setBounds(insets.left + num1, insets.top, (int) w, (int) (h - 50));

                    glCanvas.setSize(w, h);
                    num2 += c.getSize().height;
                }

                //pnlControls

                c = parent.getComponent(1);
                if (c.isVisible()) {
                    c.setBounds(insets.left, insets.top + num2, (int) w, (int) 50);
                }
            }
        }
    }
}
