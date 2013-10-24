
import afk.frontend.Frontend;
import afk.frontend.swing.RootWindow;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;


public class Main
{
    private static String LAF_FILE = "LAF.xml";
    public static void main(String[] args)
    {
        initLookAndFeel();
        Frontend frontend = new RootWindow();
        frontend.showMain();
    }
    
    private static void initLookAndFeel() 
    {
        SynthLookAndFeel lookAndFeel = new SynthLookAndFeel();
        
        try 
        {
            lookAndFeel.load(RootWindow.class.getResourceAsStream(LAF_FILE), RootWindow.class);
            UIManager.setLookAndFeel(lookAndFeel);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }        
    }
}
