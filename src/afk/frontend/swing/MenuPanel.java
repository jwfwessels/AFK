/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.bot.london.London;
import afk.bot.london.Robot;
import afk.bot.london.RobotConfig;
import afk.bot.london.RobotException;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jessica
 */
public class MenuPanel extends JPanel //implements ActionListener
{
// TODO; define components + parent ref
// TODO: Change MenuPanel to have a BotEngine, to perform testing in relation to laoding bots.

    RootWindow parent;
    
    JPanel pnlBotSelButtons;
    JPanel pnlAvailable;
    JPanel pnlSelected;
    JPanel pnlRobotError;
    
    JLabel lblAvailable;
    JLabel lblSelected;
    
    JButton btnAddBot;
    JButton btnAddAllBots;
    JButton btnRemoveBot;
    JButton btnRemoveAllBots;
    JButton btnStartMatch;
    JButton btnLoadBot;
    
    JPopupMenu configMenu;
    JMenuItem configMenuItem;
    
    JTextArea txtErrorConsole;
    
    private HashMap<String, String> botMap;
    
    private ArrayList<RobotConfig> botConfigs;
    
    private JFileChooser fileChooser;
    private JList<String> lstAvailableBots;
    private JList<String> lstSelectedBots;
    private DefaultListModel<String> lsAvailableModel;
    private DefaultListModel<String> lsSelectedModel;
    private Point p;
    private London botEngine;

    public MenuPanel(RootWindow parent)
    {
        //TODO; set layout
        this.parent = parent;

        LayoutManager layout = new MenuPanel_Layout();
        this.setLayout(layout);

        botConfigs = new ArrayList<RobotConfig>();
        botEngine = new London();
    }

    void setup()
    {
        //TODO; call init, add, style
        initComponents();
        addComponents();
        styleComponents();
    }

    private void initComponents()
    {
        //TODO; instatiate components
        //TODO; run components setup(init & add), set visible 
        pnlAvailable = new JPanel();
        pnlAvailable.setLayout(new BorderLayout());
        lblAvailable = new JLabel("Available Bots");

        pnlBotSelButtons = new JPanel();
        //pnlBotSelButtons.setLayout(new GridLayout(5, 1, 50, 50));

        pnlSelected = new JPanel();
        lblSelected = new JLabel("Selected Bots");
        pnlSelected.setLayout(new BorderLayout());

        btnAddBot = new JButton(">");
        btnAddAllBots = new JButton(">>");
        btnRemoveBot = new JButton("<");
        btnRemoveAllBots = new JButton("<<");
        btnStartMatch = new JButton("Start");
        btnLoadBot = new JButton("Load Bot");

        botMap = new HashMap<String, String>();

        fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Load Bot");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".class, .jar", "class", "jar"));

        lstAvailableBots = new JList();
        lstSelectedBots = new JList();
        lsAvailableModel = new DefaultListModel();
        lsSelectedModel = new DefaultListModel();
        
        pnlRobotError = new JPanel();
        txtErrorConsole = new JTextArea();
        txtErrorConsole.setEditable(false);
        txtErrorConsole.setForeground(Color.red);
        pnlRobotError.setLayout(new BorderLayout());
        
        configMenu = new JPopupMenu();
        configMenuItem = new JMenuItem("Configure");
    }

    private void addComponents()
    {
        //TODO; get container, add components to container.
        Iterator it = botMap.keySet().iterator();

        while (it.hasNext())
        {
            lsAvailableModel.addElement((String) it.next());
        }
        lstAvailableBots.setModel(lsAvailableModel);
        lstSelectedBots.setModel(lsSelectedModel);

        this.add(pnlAvailable);
        this.add(pnlBotSelButtons);
        this.add(pnlSelected);
        this.add(pnlRobotError);        
        
        pnlAvailable.add(lblAvailable, BorderLayout.NORTH);
        pnlAvailable.add(lstAvailableBots, BorderLayout.CENTER);
        pnlAvailable.add(btnLoadBot, BorderLayout.SOUTH);

        pnlBotSelButtons.add(btnAddBot);
        pnlBotSelButtons.add(btnAddAllBots);
        pnlBotSelButtons.add(btnRemoveBot);
        pnlBotSelButtons.add(btnRemoveAllBots);
        pnlBotSelButtons.add(btnStartMatch);

        pnlSelected.add(lblSelected, BorderLayout.NORTH);
        pnlSelected.add(lstSelectedBots, BorderLayout.CENTER);
        
        pnlRobotError.add(txtErrorConsole);
        
        configMenu.add(configMenuItem);
        configMenuItem.setHorizontalTextPosition(JMenuItem.RIGHT);
        
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }

    private void styleComponents()
    {
        // for testing
        //pnlAvailable.setBorder(BorderFactory.createLineBorder(Color.RED));
        //pnlBotSelButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        //pnlSelected.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        //pnlRobotError.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
       
        pnlAvailable.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlBotSelButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlSelected.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlRobotError.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        btnAddBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedBot = lstAvailableBots.getSelectedValue();
                System.out.println("selectedBot: " + selectedBot);
                if (selectedBot != null)
                {
                    lsSelectedModel.addElement(selectedBot);
                    botConfigs.add(new RobotConfig(selectedBot));
                }
            }
        });

        btnAddAllBots.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                for (int i = 0; i < lsAvailableModel.size(); i++)
                {
                    lsSelectedModel.addElement(lsAvailableModel.getElementAt(i));
                    botConfigs.add(new RobotConfig(lsAvailableModel.getElementAt(i)));
                }
            }
        });

        btnRemoveBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(lstSelectedBots.getSelectedIndex() != -1)
                {
                    String selectedBot = lstSelectedBots.getSelectedValue();
                    lsSelectedModel.removeElement(selectedBot);
                    botConfigs.remove(lstSelectedBots.getSelectedIndex());
                }
            }
        });

        btnRemoveAllBots.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                while (!lsSelectedModel.isEmpty())
                {
                    lsSelectedModel.removeElementAt(0);
                    botConfigs.remove(0);
                }
            }
        });

        btnLoadBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int option = fileChooser.showOpenDialog(parent.getContentPane());
                if (option == JFileChooser.APPROVE_OPTION)
                {
                    String botPath = fileChooser.getSelectedFile().getAbsolutePath();
                    String botName = (fileChooser.getSelectedFile().getName()).split("\\.")[0];
                    try
                    {
                        botEngine.addRobot(botPath);
                        botMap.put(botName, botPath);
                        lsAvailableModel.addElement(botName);
                    }
                    catch(RobotException ex)
                    {
                        txtErrorConsole.setText("Error: " + ex.getMessage());
                    }
                }
            }
        });

        btnStartMatch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO: Change tab - use selected list model as bots for match - names map to paths in botMap
                // TODO: Change MenuPanel to have a BotEngine, to perform testing in relation to laoding bots.
                botEngine.setParticipatingBots(getParticipatingBots());
                parent.spawnGamePanel(botEngine);
            }
        });
        
        configMenuItem.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                System.out.println("Menu option clicked");
                int item = lstSelectedBots.locationToIndex(p);
                System.out.println(item + ": " + lsSelectedModel.get(item));
                parent.showConfigPanel(botConfigs.get(item));  
            }
        });
        
        lstSelectedBots.addMouseListener(new MousePopupListener());
    }

    public ArrayList<String> getParticipatingBots()
    {
        ArrayList<String> bots = new ArrayList<String>();
        for (int x = 0; x < lsSelectedModel.size(); x++)
        {
            bots.add(lsSelectedModel.getElementAt(x));
        }
        return bots;
    }

    class MenuPanel_Layout implements LayoutManager
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
            int num2 = 0;
            Component c;
            
            //pnlAvailable;

            c = parent.getComponent(0);
            int wVal = 220;
            int hVal = 0;
            if (c.isVisible())
            {
                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
                hVal = ((h / 8) * 7);
                c.setBounds(insets.left + num1, insets.top, (int)wVal, (int)hVal);
                num1 += c.getSize().width;
            }
            
            //pnlBotSelButtons

            c = parent.getComponent(1);
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
            }       
        }
    }
    
    class MousePopupListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e) 
        {
            checkPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) 
        {
            checkPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) 
        {
            checkPopup(e);
        }

        private void checkPopup(MouseEvent e) 
        {
            if (e.isPopupTrigger()) 
            {
                p = new Point(e.getX(), e.getY());
                System.out.println("lsSelected size: " + lsSelectedModel.getSize());
                System.out.println("Point pos: " + lstSelectedBots.locationToIndex(p));
                if(lstSelectedBots.locationToIndex(p) >= 0 && p.getY() <= lstSelectedBots.getCellBounds(lsSelectedModel.size() - 1, lsSelectedModel.size() - 1).getY() + lstSelectedBots.getCellBounds(lsSelectedModel.size() - 1, lsSelectedModel.size()).getHeight())
                {
                    configMenu.show(lstSelectedBots, e.getX(), e.getY());
                }
            }
        }
    }
}