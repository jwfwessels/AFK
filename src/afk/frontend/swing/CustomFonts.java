/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
