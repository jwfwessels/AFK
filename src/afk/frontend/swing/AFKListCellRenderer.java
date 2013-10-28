/*
 * Copyright (c) 2013 Triforce
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Jessica
 */
public class AFKListCellRenderer extends JLabel implements ListCellRenderer
{
     @Override
     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
     {
         setText(value instanceof File ? ((File)value).getName() : value.toString());

         if(value instanceof File)
         {
             setIcon(javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemIcon((File)value));
         }
         this.setName("listCell");
         
         Color background;
         Color foreground;

         FontUIResource tempFont = CustomFonts.createFont("fonts/MyriadPro-Regular.otf");
         
         setFont(tempFont);
         // check if this cell represents the current DnD drop location
        /* JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null && !dropLocation.isInsert() && dropLocation.getIndex() == index) 
         {

             background = Color.BLUE;
             foreground = Color.WHITE;
         } */
         
         // check if this cell is selected
         //else 
         if (isSelected) 
         {
             /*background = new Color(153, 153, 153);  //204
             foreground = new Color(0, 0, 0);*/
             background = new Color(153, 153, 153);  //204
             foreground = new Color(0, 0, 0);
             this.setOpaque(true);
         } 
         
         // unselected, and not the DnD drop location
         else 
         {
             /*background = new Color(51, 51, 51);
             foreground = new Color(204, 204, 204);*/
             background = new Color(51, 51, 51);
             foreground = new Color(204, 204, 204);
         }

         
         setBackground(background);
         setForeground(foreground);

         return this;
     }
}
