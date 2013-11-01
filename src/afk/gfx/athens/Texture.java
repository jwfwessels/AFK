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
 package afk.gfx.athens;


import com.jogamp.common.nio.Buffers;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL2;

public abstract class Texture extends AthensResource
{
    protected int handle;
    
    protected int target;
    
    public static final HashMap<Integer, Integer> texParamsDefault = new HashMap<Integer, Integer>();
    static
    {
        texParamsDefault.put(GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        texParamsDefault.put(GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        texParamsDefault.put(GL2.GL_TEXTURE_WRAP_R, GL2.GL_REPEAT);
        texParamsDefault.put(GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        texParamsDefault.put(GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
    }
    
    public static final HashMap<Integer, Integer> texParamsNearest = new HashMap<Integer, Integer>();
    static
    {
        texParamsNearest.put(GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        texParamsNearest.put(GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        texParamsNearest.put(GL2.GL_TEXTURE_WRAP_R, GL2.GL_REPEAT);
        texParamsNearest.put(GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        texParamsNearest.put(GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
    }

    public static final HashMap<Integer, Integer> texParamsMipMap = new HashMap<Integer, Integer>();
    static
    {
        texParamsMipMap.put(GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        texParamsMipMap.put(GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
        texParamsMipMap.put(GL2.GL_TEXTURE_WRAP_R, GL2.GL_REPEAT);
        texParamsMipMap.put(GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        texParamsMipMap.put(GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
    }

    public static final HashMap<Integer, Integer> texParamsFBO = new HashMap<Integer, Integer>();
    static
    {
        texParamsFBO.put(GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        texParamsFBO.put(GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        texParamsFBO.put(GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
        texParamsFBO.put(GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        texParamsFBO.put(GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    }
    
    public static final HashMap<Integer, Integer> texParamsSkyMap = new HashMap<Integer, Integer>();
    static
    {
        texParamsSkyMap.put(GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        texParamsSkyMap.put(GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        texParamsSkyMap.put(GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
        texParamsSkyMap.put(GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        texParamsSkyMap.put(GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
    }
    
    public Texture(int type, String name, int target)
    {
        super(type, name);
        
        this.target = target;
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        
        gl.glGenTextures(1, handleBuffer);
        
        handle = handleBuffer.get();
        bind(gl);
    }

    @Override
    public void unload(GL2 gl)
    {
        loaded.set(false);
        
        gl.glDeleteTextures(1, new int[]{handle}, 0);
    }
    
    public void bind(GL2 gl)
    {
        gl.glBindTexture(target, handle);
    }
    
    public void use(GL2 gl, int slot)
    {
        gl.glActiveTexture(slot);
        bind(gl);
    }
    
    public static ByteBuffer imageToBytes(BufferedImage img, int[] w_h)
    {
        int width = img.getWidth();
        int height = img.getHeight();
        
        int bpp = img.getColorModel().getPixelSize()/8;
        ByteBuffer data = Buffers.newDirectByteBuffer(width * bpp * height);
        Raster alpha = img.getAlphaRaster();
        
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int RGB = img.getRGB(x, y);
                data.put((byte)(RGB >> 16));
                data.put((byte)((RGB >> 8) & 0xFF));
                data.put((byte)(RGB & 0xFF));
                if (bpp == 4)
                {
                    data.put((byte)alpha.getPixel(x, y, new int[1])[0]);
                }
            }
        }
        
        data.flip();
        
        w_h[0] = width;
        w_h[1] = height;
        w_h[2] = bpp;
        
        return data;
    }
    
    public void setParameter(GL2 gl, int parameter, int value)
    {
        gl.glTexParameteri(
                target,
                parameter,
                value);
    }
    
    public void setParameters(GL2 gl, Map<Integer,Integer> params)
    {
        for (Map.Entry<Integer,Integer> param :params.entrySet())
        {
            setParameter(gl, param.getKey(), param.getValue());
        }
    }

    public int getHandle()
    {
        return handle;
    }

    public int getTarget()
    {
        return target;
    }
}
