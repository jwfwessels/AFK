/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Jw
 */
public class RootWindow extends JFrame implements ActionListener
{

    //TODO; define components
    public void start()
    {
        //TODO; currently a bit of a hack since this is not the main thread its called by main
        this.pack();
        this.setVisible(true);
    }

    public RootWindow()
    {
        LayoutManager layout = new RootWindow_Layout();
        this.setLayout(layout);

        initComponents();
        addComponents();
        styleComponents();

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    private void initComponents()
    {
        //TODO; instatiate components
        // //TODO; run components setup(init & add), 
        //TODO: set visible 
    }

    private void addComponents()
    {
        //TODO; get container, add components to container.
    }

    private void removeComponents()
    {
        //TODO;
    }

    private void styleComponents()
    {
        //TODO; set colours of components(here if you can)
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
