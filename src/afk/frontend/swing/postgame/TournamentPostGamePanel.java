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
 package afk.frontend.swing.postgame;

import afk.frontend.swing.RootWindow;
import afk.game.GameMaster;
import afk.game.TournamentGameResult;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Jessica
 */
public class TournamentPostGamePanel extends JPanel
{
    public static final int TIMEOUT = 5000;

    private RootWindow parent;
    private JPanel pnlTop, pnlBottom;
    private JLabel lblTitle;
    private JButton btnNextGame;
    private TournamentTree tTree;
    private TournamentGameResult result;
    private GameMaster gm;
    private Timer timer;
    

    public TournamentPostGamePanel(RootWindow parent, TournamentGameResult result, GameMaster gm)
    {
        this.result = result;
        this.gm = gm;
        this.parent = parent;

        LayoutManager layout = new PostGamePanel_Layout();
        
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
        lblTitle = new JLabel("Tournament Tree");
        btnNextGame = new JButton("Continue");
        tTree = new TournamentTree(result);
        timer = new Timer(TIMEOUT, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                gm.nextGame();
            }
        });
        timer.setRepeats(false);
        timer.start();
        
        tTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e)
            {
                timer.stop();
            }
            
        });
    }

    private void addComponents()
    {
        pnlTop = new JPanel();
        pnlTop.add(lblTitle);
        add(pnlTop);
        add(tTree);
        pnlBottom = new JPanel();
        pnlBottom.add(btnNextGame);
        add(pnlBottom);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents()
    {
        btnNextGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                timer.stop();
                gm.nextGame();
            }
        });
    }

    class PostGamePanel_Layout implements LayoutManager
    {

        int panelWidth = 800;
        int panelHeight = 600;

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

            Component c;
            
            int topHeight = 50;
            int bottomHeight = 50;
            int centerHeight = h-(topHeight+bottomHeight);

            //pnlTop

            c = parent.getComponent(0);
            if (c.isVisible())
            {
                c.setBounds(0, 0, w, topHeight);
            }

            // tree!

            c = parent.getComponent(1);
            if (c.isVisible())
            {
                c.setBounds(0, topHeight, w, centerHeight);
            }
            
            //pnlBottom

            c = parent.getComponent(2);
            if (c.isVisible())
            {
                c.setBounds(0, h-bottomHeight, w, bottomHeight);
            }
        }
    }
}