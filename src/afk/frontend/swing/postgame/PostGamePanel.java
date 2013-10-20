package afk.frontend.swing.postgame;

import afk.bot.RobotConfigManager;
import afk.frontend.swing.RootWindow;
import afk.ge.tokyo.GameResult;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jessica
 */
public class PostGamePanel extends JPanel
{

    private RootWindow parent;
    private JLabel lblTitle;
    private JButton btnBack;
    private RobotScoreList scoreList;
    private GameResult result;
    private RobotConfigManager config;
    

    public PostGamePanel(RootWindow parent, GameResult result, RobotConfigManager config)
    {
        this.result = result;
        this.config = config;
        this.parent = parent;

        LayoutManager layout = new PostGamePanel_Layout();
        
        this.setLayout(layout);
    }

    

    void setup()
    {
        initComponents();
        addComponents();
        styleComponents();
    }

    private void initComponents()
    {
        lblTitle = new JLabel("Game Result");
        btnBack = new JButton("Back");
        scoreList = new RobotScoreList(result, config);
    }

    private void addComponents()
    {
        add(lblTitle);
        add(scoreList);
        add(btnBack);
    }

//    private void removeComponents()
//    {
//        //TODO;
//    }
    private void styleComponents()
    {
        btnBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.recallMenuPanel(PostGamePanel.this);
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

            int num1 = 0;
            int num2 = 0;
            Component c;

            //pnlAvailable;

//            c = parent.getComponent(0);
//            int wVal = 220;
//            int hVal = 0;
//            if (c.isVisible())
//            {
//                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
//                hVal = ((h / 8) * 7);
//                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) hVal);
//                num1 += c.getSize().width;
//            }
//
//            //pnlSelected
//
//            c = parent.getComponent(2);
//            if (c.isVisible())
//            {
//                wVal = (w) >= 500 ? (w / 3) : ((w - 100) / 2);
//                hVal = (h / 8) * 7;
//                c.setBounds(insets.left + num1, insets.top, (int) wVal, (int) hVal);
//                num2 += c.getSize().height;
//            }
        }
    }
}