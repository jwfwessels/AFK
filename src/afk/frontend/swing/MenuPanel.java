package afk.frontend.swing;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.game.GameMaster;
import afk.bot.RobotException;
import afk.bot.RobotLoader;
import afk.bot.london.LondonRobotConfigManager;
import afk.bot.london.LondonRobotLoader;
import afk.frontend.swing.postgame.PostGamePanel;
import afk.frontend.swing.postgame.TournamentPostGamePanel;
import afk.game.GameListener;
import afk.game.SingleGame;
import afk.game.TournamentGame;
import afk.ge.tokyo.GameResult;
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
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 *
 * @author Jessica
 */
public class MenuPanel extends JPanel
{

    private static String RELETIVE_ROBOT_DIRECTORY = "build/classes";
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
    JButton btnStartTournament;
    JButton btnLoadBot;
    JPopupMenu configMenu;
    JMenuItem configMenuItem;
    JTextArea txtErrorConsole;
    private HashMap<String, String> botMap;
    private JFileChooser fileChooser;
    private JList<String> lstAvailableBots;
    private JList<Robot> lstSelectedBots;
    private DefaultListModel<String> lsAvailableModel;
    private DefaultListModel<Robot> lsSelectedModel;
    private Point p;
    private RobotLoader botLoader;
    private RobotConfigManager config;
    private boolean gameRunning = false;

    public MenuPanel(RootWindow parent)
    {
        this.parent = parent;

        LayoutManager layout = new MenuPanel_Layout();

        botLoader = new LondonRobotLoader();
        config = new LondonRobotConfigManager();
        this.setLayout(layout);
    }

    /*
     * Loops through the dirctory given by RELETIVE_ROBOT_DIRECTORY and attempts to load all robots in that directory
     */
    public void loadExistingRobots()
    {
        File robotDir = new File(RELETIVE_ROBOT_DIRECTORY);
        for (File tempFile : robotDir.listFiles())
        {
            try
            {
                String tempPath = tempFile.getAbsolutePath();
                botLoader.loadRobot(tempPath);
                String fileName = tempFile.getName();
                String botName = fileName.substring(0, fileName.lastIndexOf('.'));
                System.out.println("botname: " + botName);
                botMap.put(botName, tempPath);
                lsAvailableModel.addElement(botName);
            } catch (RobotException e)
            {
                // TODO: notify user which files did not load, he/she might be interested
            }
            //catch(Exception e)
            //{
            // Pokemon exception handling! >.<
            // not cool :(
            //}
        }
    }

    void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
        
        System.out.println("COLOUR:" + pnlBotSelButtons.getBackground());
    }

    private void initComponents()
    {
        //pnlAvailable = new JPanel();
        pnlAvailable = new JPanel();
        pnlAvailable.setLayout(new BorderLayout());
        lblAvailable = new JLabel("Available Bots");
        lblAvailable.setName("label");

        //pnlBotSelButtons = new JPanel();
        pnlBotSelButtons = new AFKPanel();
        pnlBotSelButtons.setName("PanelBotSel");
        //pnlBotSelButtons.setLayout(new GridLayout(5, 1, 50, 50));

        //pnlSelected = new JPanel();
        pnlSelected = new JPanel();
        lblSelected = new JLabel("Selected Bots");
        pnlSelected.setLayout(new BorderLayout());
        lblSelected.setName("label");

        btnAddBot = new JButton(">");
        btnAddAllBots = new JButton(">>");
        btnRemoveBot = new JButton("<");
        btnRemoveAllBots = new JButton("<<");
        btnStartMatch = new JButton("Match");
        btnStartTournament = new JButton("Tournament");
        btnLoadBot = new JButton("Load Bot");

        botMap = new HashMap<String, String>();

        fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Load Bot");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".class, .jar", "class", "jar"));
        
        fileChooser.setFileView(new FileView(){
            public Icon getIcon(File f)
            {
                return FileSystemView.getFileSystemView().getSystemIcon(f);
            }
        });
        
        System.out.println(fileChooser.getComponent(0).toString());
        System.out.println(fileChooser.getComponent(1).toString());
        System.out.println(fileChooser.getComponent(2).toString());
        System.out.println(fileChooser.getComponent(3).toString());
        

        lstAvailableBots = new JList();
        lstSelectedBots = new JList();
        lsAvailableModel = new DefaultListModel();
        lsSelectedModel = new DefaultListModel();

        //pnlRobotError = new JPanel();
        pnlRobotError = new JPanel();
        txtErrorConsole = new JTextArea();
        txtErrorConsole.setEditable(false);
        txtErrorConsole.setForeground(new Color(255, 00, 00));
        pnlRobotError.setLayout(new BorderLayout());

        configMenu = new JPopupMenu();
        configMenuItem = new JMenuItem("Configure");
        
        lstAvailableBots.setCellRenderer(new AFKListCellRenderer());
        lstSelectedBots.setCellRenderer(new AFKListCellRenderer());
    }
    
    private void addComponents()
    {
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
        pnlBotSelButtons.add(btnStartTournament);

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

        /*pnlAvailable.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlBotSelButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlSelected.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pnlRobotError.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));*/

        btnAddBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedBot = lstAvailableBots.getSelectedValue();
                System.out.println("selectedBot: " + selectedBot);
                if (selectedBot != null)
                {
                    addRobotToSelected(selectedBot);
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
                    addRobotToSelected(lsAvailableModel.getElementAt(i));
                }
            }
        });

        btnRemoveBot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Robot selectedBot = lstSelectedBots.getSelectedValue();
                if (selectedBot == null)
                {
                    return;
                }
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
                    String botPath = fileChooser.getSelectedFile().getAbsolutePath();
                    String botName = (fileChooser.getSelectedFile().getName()).split("\\.")[0];
                    try
                    {
                        botLoader.loadRobot(botPath);
                        botMap.put(botName, botPath);
                        lsAvailableModel.addElement(botName);
                    } catch (RobotException ex)
                    {
                        showError(ex);
                    }
                }
            }
        });

        btnStartMatch.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final GameMaster gm = new SingleGame(config);
                gm.addGameListener(new GameListener()
                {
                    @Override
                    public void gameOver(GameResult result)
                    {
                        gm.stop();
                        UUID winnerID = result.getWinner();
                        String winner = (winnerID == null) ? "Nobody" : gm.getRobotName(winnerID);
//                        JOptionPane.showMessageDialog(MenuPanel.this.parent, winner + " won!");
                        MenuPanel.this.parent.destroyGame();
                        MenuPanel.this.parent.showPanel(
                                new PostGamePanel(MenuPanel.this.parent, result, gm), "postGame");
                    }

                    @Override
                    public void newGame(GameMaster gm)
                    {
                        parent.showGame(gm);
                    }

                    @Override
                    public void displayScores(GameResult result)
                    {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                startGame(gm);
            }
        });

        btnStartTournament.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final GameMaster gm = new TournamentGame(config);
                gm.addGameListener(new GameListener()
                {
                    
                    @Override
                    public void gameOver(GameResult result)
                    {
                        gm.stop();
                        UUID winnerID = result.getWinner();
                        String winner = (winnerID == null) ? "Nobody" : gm.getRobotName(winnerID);
//                        JOptionPane.showMessageDialog(MenuPanel.this.parent, winner + " won!");
                        MenuPanel.this.parent.destroyGame();
                        MenuPanel.this.parent.showPanel(
                                new PostGamePanel(MenuPanel.this.parent, result, gm), "postGame");
                    }

                    @Override
                    public void newGame(GameMaster gm)
                    {
                        parent.showGame(gm);
                    }

                    @Override
                    public void displayScores(GameResult result)
                    {
                        MenuPanel.this.parent.destroyGame();
                        MenuPanel.this.parent.showPanel(
                                new TournamentPostGamePanel(MenuPanel.this.parent, result, gm), "postGame");
                    }
                });
                startGame(gm);
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
                parent.showConfigPanel(lsSelectedModel.get(item));

            }
        });

        lstSelectedBots.addMouseListener(new MousePopupListener());
    }

    private void addRobotToSelected(String robotName)
    {
        try
        {
            Robot r = botLoader.getRobotInstance(robotName);
            r.setConfigManager(config);
            r.init();
            lsSelectedModel.addElement(r);
        } catch (RobotException ex)
        {
            showError(ex);
        }
    }

    private void startGame(final GameMaster gm)
    {

        for (int i = 0; i < lsSelectedModel.size(); i++)
        {
            gm.addRobotInstance(lsSelectedModel.getElementAt(i));
        }
        config.initComplete();
        gm.start();
        gameRunning = true;
    }

    public void showError(Exception ex)
    {
        ex.printStackTrace(System.err);
        txtErrorConsole.setText("Error: " + ex.getMessage());
    }

    public void recalled()
    {
        if (gameRunning)
        {
            flushConfig();
            gameRunning = false;
        }
    }

    private void flushConfig()
    {
        config = new LondonRobotConfigManager();
        lsSelectedModel.clear();
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
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) hVal);
                num1 += c.getSize().width;
            }

            //pnlBotSelButtons

            c = parent.getComponent(1);
            if (c.isVisible())
            {
                wVal = (w) >= 300 ? (w / 3) : 100;
                hVal = (h / 8) * 7;
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) hVal);
                num1 += c.getSize().width;
                pnlBotSelButtons.setLayout(new GridLayout(6, 1, 0, (int) (hVal / 9)));
                pnlBotSelButtons.setBorder(BorderFactory.createEmptyBorder(wVal / 12, wVal / 3, wVal / 12, wVal / 3));
            }

            //pnlSelected

            c = parent.getComponent(2);
            if (c.isVisible())
            {
                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
                hVal = (h / 8) * 7;
                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) hVal);
                num2 += c.getSize().height;
            }

            //pnlError

            c = parent.getComponent(3);
            if (c.isVisible())
            {
                hVal = h / 8;
                wVal = w;
                c.setBounds(insets.left, insets.top + num2, (int) wVal, (int) hVal);
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
                if (lstSelectedBots.locationToIndex(p) >= 0 && p.getY() <= lstSelectedBots.getCellBounds(lsSelectedModel.size() - 1, lsSelectedModel.size() - 1).getY() + lstSelectedBots.getCellBounds(lsSelectedModel.size() - 1, lsSelectedModel.size()).getHeight())
                {
                    configMenu.show(lstSelectedBots, e.getX(), e.getY());
                }
            }
        }
    }
}