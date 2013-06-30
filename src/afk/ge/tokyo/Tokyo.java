/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.london.London;
import afk.london.Robot;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    EntityManager entityManager;
    boolean running = true;
    final static float GAME_SPEED = 60;
    float t = 0.0f;
    public static final float NANOS_PER_SECOND = 1.0f;
    final static float DELTA = NANOS_PER_SECOND / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static double TARGET_FPS = 90;
    final static double MIN_FPS = 25;
    final static double MIN_FRAMETIME = NANOS_PER_SECOND / TARGET_FPS;
    final static double MAX_FRAMETIME = NANOS_PER_SECOND / MIN_FPS;
    private AtomicBoolean loaded = new AtomicBoolean(false);

    public Tokyo(GraphicsEngine gfxEngine)
    {
        this.gfxEngine = gfxEngine;
        this.botEngine = new London();
        
        entityManager = new EntityManager(botEngine);
        
        constructGUI();
    }

    @Override
    public void run()
    {
        if (entityManager.loadResources())
        {
            loaded.set(true);
        }

        loadBots();

        while (!loaded.get())
        { /* spin */ }

        gameLoop();
    }

    @Override
    protected void gameLoop()
    {
        double currentTime = System.nanoTime();
        float accumulator = 0.0f;
        int i = 0;
        while (running)
        {
            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= DELTA)
            {
                updateGame();
                t += DELTA;
                accumulator -= DELTA;
            }
            float alpha = accumulator / DELTA;
            render(alpha);
        }
    }

    @Override
    protected void updateGame()
    {
        entityManager.updateEntities(t, DELTA);
    }

    @Override
    protected void render(float alpha)
    {
        entityManager.renderEntities(alpha);
        gfxEngine.redisplay();
    }

    private void loadBots()
    {
        //TODO refactor load bots
        getParticipatingBots();
    }
    
        
    private JFrame jFrame;
    private JTabbedPane jTPane;

    private HashMap<String, String> botMap = new HashMap<String, String>();
    private JPanel pnlBotSelection = new JPanel();
    private JPanel pnlArena = new JPanel();
    private JFileChooser fileChooser = new JFileChooser();
    private JList<String> lstAvailableBots = new JList();
    private JList<String> lstSelectedBots = new JList();
    private DefaultListModel<String> lsAvailableModel = new DefaultListModel();
    private DefaultListModel<String> lsSelectedModel = new DefaultListModel();
    
    private void constructGUI()
    {
        jFrame = new JFrame("");
        // TODO: get width and height from somehwere else
        jFrame.setBounds(0, 0, 1280, 786);
        
        jTPane = new JTabbedPane();
               
        jTPane.add(pnlBotSelection, 0);
        jTPane.add(pnlArena, 1);
        
        // Removes some stupid stuff from swing. It's services are not required
        jTPane.setUI(new BasicTabbedPaneUI() 
        {  
            @Override  
            protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) 
            {   
                    return 0;  
            }  
            @Override
            protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) 
            {  

            } 
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
            {
                
            }
        }); 
        
        jTPane.setEnabledAt(0, false);
        jTPane.setEnabledAt(1, false);
        
        jTPane.setSelectedComponent(pnlBotSelection);
        
        pnlBotSelection.setLayout(new GridLayout(1, 3));
        
        JPanel pnlBotSelButtons = new JPanel();
        JPanel pnlAvailable = new JPanel();
        JPanel pnlSelected = new JPanel();
        JLabel lblAvailable = new JLabel("Available Bots");
        JLabel lblSelected = new JLabel("Selected Bots");
        
        pnlAvailable.setLayout(new BorderLayout());
        pnlSelected.setLayout(new BorderLayout());
        
        pnlBotSelButtons.setLayout(new GridLayout(5, 1, 50, 50));
        pnlBotSelButtons.setBorder(new EmptyBorder(150, 150, 150, 150));
        
        JButton btnAddBot = new JButton(">");
        JButton btnAddAllBots = new JButton(">>");
        JButton btnRemoveBot = new JButton("<");
        JButton btnRemoveAllBots = new JButton("<<");
        JButton btnStartMatch = new JButton("Start");
        JButton btnLoadBot = new JButton("Load Bot");
        
        fileChooser.setDialogTitle("Load Bot");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".class", "java class file"));
        
        btnAddBot.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String selectedBot = lstAvailableBots.getSelectedValue();
                lsAvailableModel.removeElement(selectedBot);
                lsSelectedModel.addElement(selectedBot);
            }
        });
        
        btnAddAllBots.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                while(!lsAvailableModel.isEmpty())
                {
                    lsSelectedModel.addElement(lsAvailableModel.getElementAt(0));
                    lsAvailableModel.removeElementAt(0);
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
                lsAvailableModel.addElement(selectedBot);
            }
        });
        
        btnRemoveAllBots.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                while(!lsSelectedModel.isEmpty())
                {
                    lsAvailableModel.addElement(lsSelectedModel.getElementAt(0));
                    lsSelectedModel.removeElementAt(0);
                }
            }
        });
        
        btnLoadBot.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int option = fileChooser.showOpenDialog(jFrame);
                if(option != JFileChooser.APPROVE_OPTION)
                {
                    return;
                }
                String botPath = fileChooser.getSelectedFile().getAbsolutePath();
                String botName = (fileChooser.getSelectedFile().getName()).split("\\.")[0];
                botMap.put(botName, botPath);
                lsAvailableModel.addElement(botName);
            }
        });
        
        btnStartMatch.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // TODO: Change tab - use selected list model as bots for match - names map to paths in botMap
                loadBots();
                
                jTPane.setSelectedComponent(pnlArena);
            }
        });
        
        pnlBotSelButtons.add(btnAddBot);
        pnlBotSelButtons.add(btnAddAllBots);
        pnlBotSelButtons.add(btnRemoveBot);
        pnlBotSelButtons.add(btnRemoveAllBots);
        pnlBotSelButtons.add(btnStartMatch);
        
        //TODO: Put list boxes on scroll panes and give them borders
        
        Iterator it = botMap.keySet().iterator();
        
        while(it.hasNext())
        {
            lsAvailableModel.addElement((String)it.next());
        }
        lstAvailableBots.setModel(lsAvailableModel);
        lstSelectedBots.setModel(lsSelectedModel);
        
        pnlAvailable.add(lblAvailable, BorderLayout.NORTH);
        pnlAvailable.add(lstAvailableBots, BorderLayout.CENTER);
        pnlAvailable.add(btnLoadBot, BorderLayout.SOUTH);
        
        pnlSelected.add(lblSelected, BorderLayout.NORTH);
        pnlSelected.add(lstSelectedBots, BorderLayout.CENTER);
        
        pnlBotSelection.add(pnlAvailable);
        pnlBotSelection.add(pnlBotSelButtons);
        pnlBotSelection.add(pnlSelected);
        
        jFrame.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent windowevent ) {
                jFrame.dispose();
                System.exit(0);
            }
        });
        jFrame.setResizable(false);
		
        pnlArena.add(gfxEngine.getAWTComponent(), BorderLayout.CENTER);

        jFrame.getContentPane().add(jTPane);
        
        jFrame.setVisible(true);
    }
    
    public ArrayList<String> getParticipatingBots()
    {
        ArrayList<String> bots = new ArrayList<String>();
        for(int x = 0; x < lsSelectedModel.size(); x++)
        {
            bots.add(botMap.get(lsSelectedModel.getElementAt(x)));
        }
        return bots;
    }
}
