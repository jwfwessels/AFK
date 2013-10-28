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
 package afk.ge;

import static afk.ge.AbstractFilter.getPixels;
import static afk.ge.AbstractFilter.setPixels;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
public class MultiplyFilter extends AbstractFilter
{

    private Color mixColor;

    public MultiplyFilter(Color mixColor)
    {
        this.mixColor = mixColor;
    }

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest)
    {
        if (dest == null)
        {
            dest = createCompatibleDestImage(src, null);
        }

        int width = src.getWidth();
        int height = src.getHeight();

        int[] pixels = new int[width * height];
        getPixels(src, 0, 0, width,
                height, pixels);
        mixColor(pixels);
        setPixels(dest, 0, 0, width,
                height, pixels);
        return dest;
    }

    private void mixColor(int[] inPixels)
    {
        int mix_a = mixColor.getAlpha();
        int mix_r = mixColor.getRed();
        int mix_b = mixColor.getBlue();
        int mix_g = mixColor.getGreen();

        for (int i = 0; i < inPixels.length; i++)
        {
            int argb = inPixels[i];

            int a = argb & 0xFF000000;
            int r = (argb >> 16) & 0xFF;
            int g = (argb >> 8) & 0xFF;
            int b = (argb) & 0xFF;
            r = (int) ((r * mix_r) / 256.0f);
            g = (int) ((g * mix_g) / 256.0f);
            b = (int) ((b * mix_b) / 256.0f);

            inPixels[i] = a << 24 | r << 16 | g << 8 | b;
        }
    }
}
