package afk.rome;

import afk.gfx.athens.Athens;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class Rome
{
    JFrame frame;
    Athens athens;
    RomeEngine engine;

    public Rome()
    {
        frame = new JFrame("Rome - AFK Map Editor");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e)
            {
                engine.stop();
                frame.dispose();
                System.exit(0);
            }
            
        });
        athens = new Athens(true);
        engine = new RomeEngine(athens);
        engine.start();
        
        frame.add(athens.getAWTComponent());
        
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    
    
    public static void main(String[] args)
    {
        new Rome();
    }
}
