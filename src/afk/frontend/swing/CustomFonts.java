/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.frontend.swing;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Jessica
 */
public class CustomFonts
{
    // NOTE: this is a tpical dan fix

    private final static HashMap<String, Font> fontMap = new HashMap<String, Font>();

    public static FontUIResource createFont(String path)
    {
        try
        {
            Font font = fontMap.get(path);
            if (font == null)
            {
                System.out.println("File IO is a-happening.");
                font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(path));
                fontMap.put(path, font);
            }
            FontUIResource fontResource = new FontUIResource(font.deriveFont(Font.PLAIN, 14));
            return fontResource;
        } catch (FontFormatException e)
        {
            e.printStackTrace(System.err);
        } catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
        return null;
    }
}
