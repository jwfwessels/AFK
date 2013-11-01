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
 package afk.frontend.swing.config;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.frontend.swing.RootWindow;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Jessica
 */
public class RobotConfigPanel extends JPanel
{

    private RootWindow root;
    private JLabel lblRConfig;
    private JPanel pnlModel;
    private Component pnlCanvas;
    private Component glCanvas;
    //private JButton btnPrev;
    //private JButton btnNext;
    //private JButton btnBrowse;
    private JPanel pnlSettings;
    private JLabel lblRName;
    private JTextField txtName;
    //private JLabel lblRColour;
    ///private JComboBox cmbColour; // Change to colour picker
    private JButton btnBack;
    private JButton btnSave;
    private GraphicsEngine gfxEngine;
    private ConfigEngine configEngine = null;
    private Robot robot = null;

    public RobotConfigPanel(RootWindow _root)
    {
        root = _root;
        setup();
        this.setBounds(0, 0, 800, 600);
    }

    private void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
    }

    public void initComponents()
    {
        lblRConfig = new JLabel("Configuration");

        pnlModel = new JPanel();

        gfxEngine = new Athens(true);

        pnlCanvas = gfxEngine.getAWTComponent();
//        pnlCanvas = new JPanel();
        /*btnPrev = new JButton("<");
        btnNext = new JButton(">");
        btnBrowse = new JButton("Browse");*/

        pnlSettings = new JPanel();
        lblRName = new JLabel("Name:");
        txtName = new JTextField("Name");
        //lblRColour = new JLabel("Colour");
        //cmbColour = new JComboBox<String>();
        btnBack = new JButton("Back");
        btnSave = new JButton("Save");
    }

    public void addComponents()
    {
        //lblRColour.setName("label");
        lblRConfig.setName("label");
        lblRName.setName("label");
        
        pnlModel.setLayout(new ModelPanel_Layout());
        //pnlModel.add(btnPrev);
        pnlModel.add(pnlCanvas);
        //pnlModel.add(btnNext);
        //pnlModel.add(btnBrowse);
        //add glCanvas to pnlCanvas

        pnlSettings.setLayout(new SettingsPanel_Layout());
        pnlSettings.add(lblRName);
        pnlSettings.add(txtName);
        //pnlSettings.add(lblRColour);
        //pnlSettings.add(cmbColour);
        pnlSettings.add(btnBack);
        pnlSettings.add(btnSave);

        this.add(lblRConfig);
        this.add(pnlModel);
        this.add(pnlSettings);
    }

    public void styleComponents()
    {
        /*pnlModel.setBorder(new LineBorder(Color.yellow));
        pnlSettings.setBorder(new LineBorder(Color.blue));
        */
        this.setLayout(new RobotConfigPanel_Layout());


        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                back();
            }
        });

        btnSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveSettings();

                back();
            }
        });
    }

    /**
     * called when the config is opened.
     */
    public void loadConfig(Robot robot)
    {
//        pnlCanvas = gfxEngine.getAWTComponent();
//        this.revalidate();
        this.robot = robot;
        RobotConfigManager config = robot.getConfigManager();
        UUID id = robot.getId();

        txtName.setText(robot.toString());

        if (configEngine == null)
        {
            configEngine = new ConfigEngine(gfxEngine, config);
            configEngine.start();
        }

        configEngine.setDisplayedRobot(robot);
    }

    private void saveSettings()
    {
        RobotConfigManager config = robot.getConfigManager();
        UUID id = robot.getId();

        config.setProperty(id, "name", txtName.getText());

        // TODO: other stuff...
    }

    private void back()
    {
        configEngine.stop();
        configEngine = null;
        robot = null;
        root.recallMenuPanel(this);
    }

    class RobotConfigPanel_Layout implements LayoutManager
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

            int numH = 0;
            int hVal = 0;
            Component c;

            //lblRConfig

            c = parent.getComponent(0);

            if (c.isVisible())
            {
                hVal = (h / 10) / 2;
                c.setSize(new Dimension(120, (int) hVal));
                c.setBounds((w / 2) - (c.getWidth() / 2), insets.top, 240, (int) hVal);
                numH += hVal;
            }

            //pnlModel

            c = parent.getComponent(1);
            if (c.isVisible())
            {
                hVal = (h / 10) * 6;
                c.setSize(new Dimension(w / 2, (int) hVal));
                c.setBounds((w / 2) - (c.getWidth() / 2), numH, w / 2, (int) hVal);
                numH += hVal;
            }

            //pnlSetings

            c = parent.getComponent(2);
            if (c.isVisible())
            {
                hVal = (h / 10) * 3;
                c.setSize(new Dimension((w / 2), (int) hVal));
                c.setBounds((w / 2) - (c.getWidth() / 2), numH, w / 2, (int) hVal);
            }

        }
    }

    class ModelPanel_Layout implements LayoutManager
    {

        int panelWidth = pnlModel.getWidth();
        int panelHeight = pnlModel.getHeight();

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

            //btnPrev

            /*c = parent.getComponent(0);

            if (c.isVisible())
            {
                hVal = (int) (h / 6);
                wVal = (int) (w / 8);
                c.setSize(new Dimension((int) wVal, (int) hVal));
                c.setBounds(insets.left, (h / 2) - c.getHeight() / 2, wVal, hVal);
                numW += wVal;
            }*/

            //pnlCanvas

            c = parent.getComponent(0);

            if (c.isVisible())
            {
                //wVal = (int) ((w / 8) * 6);
                hVal = (int) ((h / 8) * 6);
                c.setSize(new Dimension((int) w, (int) hVal));
                c.setBounds(numW, (h / 2) - c.getHeight() / 2, w, hVal);
                numW += w;
                numH += hVal + (h / 2) - c.getHeight() / 2;
            }

            //btnNext

            /*c = parent.getComponent(2);

            if (c.isVisible())
            {
                hVal = (int) (h / 6);
                wVal = (int) (w / 8);
                c.setSize(new Dimension((int) wVal, (int) hVal));
                c.setBounds(numW, (h / 2) - c.getHeight() / 2, wVal, hVal);
                numW += wVal;
            }*/

            //btnBrowse

            /*c = parent.getComponent(3);

            if (c.isVisible())
            {
                hVal = (int) (h - numH);
                wVal = (int) (w / 3);
                c.setSize(new Dimension((int) wVal, (int) hVal));
                c.setBounds(((w / 2) - (c.getWidth() / 2)), numH, wVal, hVal);
            }*/
        }
    }

    class SettingsPanel_Layout implements LayoutManager
    {

        int panelWidth = pnlModel.getWidth();
        int panelHeight = pnlModel.getHeight();

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

            int numW = w / 8;
            int numH = h / 8;
            int hVal;
            int wVal;
            Component c;

            //lblRName

            c = parent.getComponent(0);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 4);
                c.setBounds(numW, numH, wVal, hVal);
                numW += wVal + (w / 12);
            }

            //txtName

            c = parent.getComponent(1);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 2);
                c.setBounds(numW, numH, wVal, hVal);
                numW += wVal + (w / 8);
                numH += hVal + (h / 18);
            }

            //lblRColour

            /*numW = w / 18;

            c = parent.getComponent(2);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 4);
                c.setBounds(numW, numH, wVal, hVal);
                numW += wVal + (w / 8);
            }

            //cmbColour

            c = parent.getComponent(3);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 4);
                c.setBounds(numW, numH, wVal, hVal);
                numW += wVal + (w / 8);
                numH += hVal + (h / 18);
            }*/

            //btnBack

            numW = w / 18;

            c = parent.getComponent(2);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 4);
                c.setSize(wVal, hVal);
                c.setBounds((numW / 2) + (c.getWidth() / 2), numH, wVal, hVal);
                numW += wVal + (w / 8);
            }

            //btnSave

            c = parent.getComponent(3);

            if (c.isVisible())
            {
                hVal = (int) ((h / 9) * 2);
                wVal = (int) (w / 4);
                c.setSize(wVal, hVal);
                c.setBounds((numW) + (c.getWidth() / 2), numH, wVal, hVal);
                numW += wVal + (w / 8);
            }
        }
    }
}
