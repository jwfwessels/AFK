/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
    RootWindow root;
    
    JLabel lblRConfig;
    
    JPanel pnlModel;
    JPanel pnlCanvas;
    Component glCanvas;
    JButton btnPrev;
    JButton btnNext;
    JButton btnBrowse;
    
    JPanel pnlSettings;
    JLabel lblRName;
    JTextField txtName;
    JLabel lblRColour;
    JComboBox cmbColour;
    JButton btnBack;
    JButton btnSave;
    
    public RobotConfigPanel(RootWindow _root)
    {
        root = _root;   
        setup();
        this.setBounds(0,0, 800, 600);
    }
    
    public void initComponents()
    {
        lblRConfig = new JLabel("Robot Configuration");
    
        pnlModel = new JPanel();
        pnlCanvas = new JPanel();
        //glCanvas
        btnPrev = new JButton("<");
        btnNext = new JButton(">");
        btnBrowse = new JButton("Browse");

        pnlSettings = new JPanel();
        lblRName = new JLabel("Robot Name");
        txtName = new JTextField("Name");
        lblRColour = new JLabel("Robot Colour");
        cmbColour = new JComboBox<String>();
        btnBack = new JButton("Back");
        btnSave = new JButton("Save");          
    }
    
    public void addComponents()
    {
        pnlModel.setLayout(new ModelPanel_Layout());
        pnlModel.add(btnPrev);
        pnlModel.add(pnlCanvas);
        pnlModel.add(btnNext);
        pnlModel.add(btnBrowse);
        //add glCanvas to pnlCanvas
        
        pnlSettings.setLayout(new GridLayout(2, 3));
        pnlSettings.add(lblRName);
        pnlSettings.add(txtName);
        pnlSettings.add(lblRColour);
        pnlSettings.add(cmbColour);
        pnlSettings.add(btnBack);
        pnlSettings.add(btnSave);
        
        this.add(lblRConfig);
        this.add(pnlModel);
        this.add(pnlSettings);
    }
    
    public void styleComponents()
    {
        pnlModel.setBorder(new LineBorder(Color.yellow));
        pnlCanvas.setBorder(new LineBorder(Color.red));
        this.setLayout(new RobotConfigPanel_Layout());
    }

    private void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
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

            int num1 = 0;
            int hVal = 0;
            Component c;

            //lblRConfig

            c = parent.getComponent(0);

            if (c.isVisible())
            {
                hVal = h /10;
                c.setSize(new Dimension(120, (int)hVal));
                c.setBounds((w/2) - (c.getWidth() / 2), insets.top, 120, (int)hVal);
                num1 += hVal;
            }
            
            //pnlModel
            c = parent.getComponent(1);
            if(c.isVisible())
            {
                hVal = (h /10) * 6;
                c.setSize(new Dimension(w/2, (int)hVal));
                c.setBounds((w/2) - (c.getWidth() / 2), num1, w/2, (int)hVal);
                num1 += hVal;
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
            int hVal = 0;
            int wVal = 0;
            Component c;
            
            //btnPrev
            
            c = parent.getComponent(0);
            
            if(c.isVisible())
            {
                hVal = (int)(h / 6);
                wVal = (int)(w / 8);
                c.setSize(new Dimension((int)wVal, (int)hVal));
                c.setBounds(insets.left, (h / 2) - c.getHeight() / 2, wVal, hVal);
                numW += wVal;
            }
            
            //pnlCanvas
            
            c = parent.getComponent(1);
            
            if(c.isVisible())
            {
                wVal = (int)((w / 8) * 6);
                hVal = (int)((h / 8) * 6);
                c.setSize(new Dimension((int)wVal, (int)hVal));
                c.setBounds(numW, (h / 2) - c.getHeight() / 2, wVal, hVal);
                numW += wVal;
                numH += hVal + (h / 2) - c.getHeight() / 2;
            } 
            
            //btnNext
            
            c = parent.getComponent(2);
            
            if(c.isVisible())
            {
                hVal = (int)(h / 6);
                wVal = (int)(w / 8);
                c.setSize(new Dimension((int)wVal, (int)hVal));
                c.setBounds(numW, (h / 2) - c.getHeight() / 2, wVal, hVal);
                numW += wVal;
            } 
            
            //btnBrowse
        
            c = parent.getComponent(3);
            
            if(c.isVisible())
            {
                hVal = (int)(h - numH);
                wVal = (int)(w / 5);
                c.setSize(new Dimension((int)wVal, (int)hVal));
                c.setBounds(((w / 2) - (c.getWidth() / 2)), numH, wVal, hVal);
            } 
        }
    }
    
}
