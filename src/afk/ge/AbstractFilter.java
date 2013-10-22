/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 *
 * @author daniel
 */
public abstract class AbstractFilter
        implements BufferedImageOp
{

    @Override
    public abstract BufferedImage filter(
            BufferedImage src, BufferedImage dest);

    @Override
    public Rectangle2D getBounds2D(BufferedImage src)
    {
        return new Rectangle(0, 0, src.getWidth(),
                src.getHeight());
    }

    @Override
    public BufferedImage createCompatibleDestImage(
            BufferedImage src, ColorModel destCM)
    {
        if (destCM == null)
        {
            destCM = src.getColorModel();
        }

        return new BufferedImage(destCM,
                destCM.createCompatibleWritableRaster(
                src.getWidth(), src.getHeight()),
                destCM.isAlphaPremultiplied(), null);
    }

    @Override
    public Point2D getPoint2D(Point2D srcPt,
            Point2D dstPt)
    {
        return (Point2D) srcPt.clone();
    }

    @Override
    public RenderingHints getRenderingHints()
    {
        return null;
    }

    public static int[] getPixels(BufferedImage img,
            int x, int y,
            int w, int h,
            int[] pixels)
    {
        if (w == 0 || h == 0)
        {
            return new int[0];
        }
        if (pixels == null)
        {
            pixels = new int[w * h];
        } else if (pixels.length < w * h)
        {
            throw new IllegalArgumentException(
                    "pixels array must have a length >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB)
        {
            Raster raster = img.getRaster();
            return (int[]) raster.getDataElements(x, y, w, h, pixels);
        }

        return img.getRGB(x, y, w, h, pixels, 0, w);
    }

    public static void setPixels(BufferedImage img,
            int x, int y,
            int w, int h,
            int[] pixels)
    {
        if (pixels == null || w == 0 || h == 0)
        {
            return;
        } else if (pixels.length < w * h)
        {
            throw new IllegalArgumentException(
                    "pixels array must have a length >= w*h");
        }

        int imageType = img.getType();
        if (imageType == BufferedImage.TYPE_INT_ARGB
                || imageType == BufferedImage.TYPE_INT_RGB)
        {
            WritableRaster raster = img.getRaster();
            raster.setDataElements(x, y, w, h, pixels);
        } else
        {
            img.setRGB(x, y, w, h, pixels, 0, w);
        }
    }
}