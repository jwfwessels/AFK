/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import afk.london.London;
import afk.london.Robot;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jw
 */
public class MenuPanel extends JPanel implements ActionListener
{
// TODO; define components + parent ref
// TODO: Change MenuPanel to have a BotEngine, to perform testing in relation to laoding bots.

    RootWindow parent;
    JPanel pnlBotSelButtons;
    JPanel pnlAvailable;
    JPanel pnlSelected;
    JLabel lblAvailable;
    JLabel lblSelected;
    JButton btnAddBot;
    JButton btnAddAllBots;
    JButton btnRemoveBot;
    JButton btnRemoveAllBots;
    JButton btnStartMatch;
    JButton btnLoadBot;
    private HashMap<String, String> botMap;
    private JFileChooser fileChooser;
    private JList<String> lstAvailableBots;
    private JList<String> lstSelectedBots;
    private DefaultListModel<String> lsAvailableModel;
    private DefaultListModel<String> lsSelectedModel;
    private London botEngine;

    public MenuPanel(RootWindow parent)
    {
        //TODO; set layout
        this.parent = parent;

        LayoutManager layout = new MenuPanel_Layout();
        this.setLayout(layout);

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
        pnlBotSelButtons.setLayout(new GridLayout(5, 1, 50, 50));

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
        fileChooser.setFileFilter(new FileNameExtensionFilter("java class file", "class", "jar"));

        lstAvailableBots = new JList();
        lstSelectedBots = new JList();
        lsAvailableModel = new DefaultListModel();
        lsSelectedModel = new DefaultListModel();
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

    }

//    private void removeComponents()
//    {
//        //TODO;
//    }

    private void styleComponents()
    {
        // for testing
//        pnlAvailable.setBorder(BorderFactory.createLineBorder(Color.RED));
//        pnlBotSelButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//        pnlSelected.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        btnAddBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedBot = lstAvailableBots.getSelectedValue();
                System.out.println("selectedBot: " + selectedBot);
//                JOptionPane.showMessageDialog(parent, "Select a bot");
                if (selectedBot != null)
                {
                    lsSelectedModel.addElement(selectedBot);
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
                }
            }
        });

        btnRemoveBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedBot = lstSelectedBots.getSelectedValue();
                lsSelectedModel.removeElement(selectedBot);
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
                    System.out.println("file opened!");

                    String botPath = fileChooser.getSelectedFile().getAbsolutePath();
                    String botName = (fileChooser.getSelectedFile().getName()).split("\\.")[0];
                    if (checkBots(botPath))
                    {
                        botMap.put(botName, botPath);
                        lsAvailableModel.addElement(botName);
                        System.out.println("added to available" + botName);
                    } else
                    {
                        System.out.println("Error Loading Bots" + botEngine.getBotLoadingError());
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
                ArrayList<String> bots = getParticipatingBots();
                for (int i = 0; i < bots.size(); i++)
                {
                    String path = bots.get(i);
                    Robot loadedBot = botEngine.loadBot(path);
                    // TODO: call - arraylist.add(botengine.createBot(path)) , do check on created bot instance!
                    botEngine.registerBot(loadedBot);
                }

                parent.spawnGamePanel(botEngine);

            }
        });

    }

    public ArrayList<String> getParticipatingBots()
    {
        ArrayList<String> bots = new ArrayList<String>();
        for (int x = 0; x < lsSelectedModel.size(); x++)
        {
            bots.add(botMap.get(lsSelectedModel.getElementAt(x)));
        }
        return bots;
    }

    private boolean checkBots(String path)
    {
        Robot loadedBot = botEngine.loadBot(path);
        if (loadedBot == null)
        {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.


    }

    class MenuPanel_Layout implements LayoutManager
    {

        int panelWidth = 1280;
        int panelHeight = 786;

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

            int w = parent.getSize().width;
            int h = parent.getSize().height;

            int num1 = 0;
            Component c;

            //pnlAvailable;

            c = parent.getComponent(0);
            int wVal = 220;
            if (c.isVisible())
            {
                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) h);
                num1 += c.getSize().width;
            }
            //pnlBotSelButtons

            c = parent.getComponent(1);
            if (c.isVisible())
            {
                wVal = (w) >= 300 ? (w / 3) : 100;
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) h);
                num1 += c.getSize().width;
            }


            //pnlSelected

            c = parent.getComponent(2);
            if (c.isVisible())
            {
                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) h);
            }
        }
    }
}