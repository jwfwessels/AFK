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
