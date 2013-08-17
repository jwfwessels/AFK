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
    //COMPONENTS!!!
    
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
        pnlModel.setLayout(new BorderLayout());
        pnlModel.add(btnPrev, BorderLayout.WEST);
        pnlModel.add(btnNext, BorderLayout.EAST);
        pnlModel.add(btnBrowse, BorderLayout.SOUTH);
        //add glCanvas 
        
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
        //pnlModel.setLayout(new BorderLayout());
        pnlModel.setBorder(new LineBorder(Color.yellow));
        
        
        
        //TODO: change this to custom layout
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

            /*c = parent.getComponent(1);
            if (c.isVisible())
            {
                wVal = (w) >= 300 ? (w / 3) : 100;
                hVal = (h / 8) * 7;
                c.setBounds(insets.left + num1, insets.top, (int)wVal, (int)hVal);
                num1 += c.getSize().width;
                pnlBotSelButtons.setLayout(new GridLayout(5, 1, 0, (int)(hVal/ 7)));
                pnlBotSelButtons.setBorder(BorderFactory.createEmptyBorder(wVal/12, wVal/3, wVal/12, wVal/3));
            }

            //pnlSelected

            c = parent.getComponent(2);
            if (c.isVisible())
            {
                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
                hVal = (h / 8) * 7;
                c.setBounds(insets.left + num1, insets.top, (int)wVal, (int)hVal);
                num2 += c.getSize().height;
            }
            
           
            //pnlError
            
            c = parent.getComponent(3);
            if(c.isVisible())
            {           
                hVal = h / 8;
                wVal = w;
                c.setBounds(insets.left, insets.top + num2, (int)wVal, (int)hVal);
            } */      
        
        }
    }
    
}
