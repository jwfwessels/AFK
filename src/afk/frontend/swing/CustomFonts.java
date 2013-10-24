/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Jessica
 */
public class CustomFonts 
{
    public static FontUIResource createFont(String path) 
    {
        Font font = null;
        try
        {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(path));
            FontUIResource fontResource = new FontUIResource(font.deriveFont(Font.PLAIN, 14));
            return fontResource;
        }
        catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
