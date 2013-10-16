package afk.ge.tokyo.test;

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author daniel
 */
public class RayBox extends JPanel
{

    static char[] axis =
    {
        'x', 'y', 'z'
    };
    static String message = "foo!";
    static float[] org =
    {
        0, 0, 0
    };
    static float[] ray =
    {
        1, 0, 0
    };
    static float[] ext =
    {
        50, 50, 50
    };
    static Color[] axisCol =
    {
        Color.RED.darker(), Color.GREEN.darker(), Color.BLUE.darker()
    };
    static float[] p = null;
    static Collection<float[]> points = new ConcurrentLinkedDeque<float[]>();
    int[] I;

    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(axisCol[I[0]]);
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.setColor(axisCol[I[1]]);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getWidth());

        g.setColor(Color.WHITE);
        g.drawRect(getWidth() / 2 - (int) ext[I[0]], getHeight() / 2 - (int) ext[I[1]],
                (int) ext[0] * 2, (int) ext[I[1]] * 2);

        g.drawString(message, 5, 15);

        g.setColor(p == null ? Color.MAGENTA : Color.CYAN);
        float x = getWidth() / 2 + (int) org[I[0]];
        float y = getHeight() / 2 + (int) org[I[1]];
        if (p == null)
        {
            g.drawLine((int)x, (int)y,  (int) (x +ray[I[0]] * 1000),  (int) (y +ray[I[1]] * 1000));
        } else
        {
            g.drawLine((int)x, (int)y, getWidth() / 2 + (int) p[I[0]], getHeight() / 2 + (int) p[I[1]]);
            g.setColor(Color.MAGENTA);
            g.drawLine(getWidth() / 2 + (int) p[I[0]], getHeight() / 2 + (int) p[I[1]],
                     (int) (x + ray[I[0]] * 1000),  (int) (y + ray[I[1]] * 1000));
        }
        g.setColor(Color.CYAN);
        for (float[] z : points)
        {
            x = getWidth() / 2 + (int) z[I[0]];
            y = getHeight() / 2 + (int) z[I[1]];
            g.fillOval((int)x - 2, (int)y - 2, 4, 4);
        }
    }

    public static float[] findEnterancePoint()
    {
        message = "";
        points.clear();
        
        

        float[] mext = new float[ext.length];
        for (int i = 0; i < ext.length; i++)
        {
            mext[i] = -ext[i];
        }
        for (int i = 0; i < ext.length; i++)
        {
            if (org[i] >= ext[i])
            {
                message += "[" + axis[i] + "+]";
                if (ray[i] >= 0)
                {
                    message += "[away]";
                    return null; // ray points away from box
                }
                float[] w = lineIntersection(i, ext);
                if (w != null)
                {
                    message += "[yes]";
                    return w;
                }
            } else if (org[i] <= mext[i])
            {
                message += "[" + axis[i] + "-]";
                if (ray[i] <= 0)
                {
                    message += "[away-]";
                    return null; // ray points away from box
                }
                float[] w = lineIntersection(i, mext);
                if (w != null)
                {
                    message += "[yes]";
                    return w;
                }
            }
        }
        return null;
    }

    public static float[] lineIntersection(int xi, float[] lext)
    {
        int yi = (xi + 2) % 3;
        int zi = (xi + 1) % 3;
        if (ray[xi] == 0)
        {
            ray[xi] = 0.00000000001f;
        }
        
        float[] r = new float[3];
        
        float t0 = (lext[xi] - org[xi]) / ray[xi];
        float t1 = (lext[yi] - org[yi]) / ray[yi];
        float t2 = (lext[zi] - org[zi]) / ray[zi];
        
        System.out.println("t0: " + t0);
        System.out.println("t1: " + t1);
        System.out.println("t2: " + t2);
        
        r[xi] = lext[xi];
        r[yi] = org[yi] + ray[yi]*t0;
        r[zi] = org[zi] + ray[zi]*t0;
        
        points.add(r);
        if (Math.abs(r[yi]) <= ext[yi] && Math.abs(r[zi]) <= ext[zi])
        {
            return r;
        }
        
        r = new float[3];
        r[xi] = org[xi] + ray[xi]*t1;
        r[yi] = lext[yi];
        r[zi] = org[zi] + ray[zi]*t1;
        
        points.add(r);
        if (Math.abs(r[xi]) <= ext[xi] && Math.abs(r[zi]) <= ext[zi])
        {
            return r;
        }
        
        r = new float[3];
        r[xi] = org[xi] + ray[xi]*t2;
        r[yi] = org[yi] + ray[yi]*t2;
        r[zi] = lext[zi];
        
        points.add(r);
        if (Math.abs(r[xi]) <= ext[xi] && Math.abs(r[yi]) <= ext[yi])
        {
            return r;
        }
        
        return null;
    }

    public RayBox(int a, int b)
    {
        this.I = new int[]
        {
            a, b
        };
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                org[I[0]] = (float)e.getX() - getWidth() / 2;
                org[I[1]] = (float)e.getY() - getHeight() / 2;
                p = findEnterancePoint();
                getParent().repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                ray[I[0]] = ((float)e.getX() - getWidth() / 2.0f) - org[I[0]];
                ray[I[1]] = ((float)e.getY() - getHeight() / 2.0f) - org[I[1]];
                float length = (float) Math.sqrt(ray[I[0]] * ray[I[0]] + ray[I[1]] * ray[I[1]]);
                ray[I[0]] /= length;
                ray[I[1]] /= length;
                p = findEnterancePoint();
                getParent().repaint();
            }
        });

        p = findEnterancePoint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("RayBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2));

        final GraphicsEngine gfxEngine = new Athens(false);
        gfxEngine.setBackground(Vec3.VEC3_ZERO);

        RayBoxEngine engine = new RayBoxEngine(gfxEngine);
        engine.addSystem(new ISystem()
        {
            float s = 0.1f;

            @Override
            public boolean init(Engine engine)
            {
                return true;
            }

            @Override
            public void update(float t, float dt)
            {
                BBox bbox = new BBox(Mat4.MAT4_IDENTITY, new Vec3(ext[0], ext[1], ext[2]).scale(s));

                GfxEntity gfx = gfxEngine.getDebugEntity(bbox);
                gfx.colour = new Vec3(1, 1, 1);
                gfx.scale = new Vec3(1);

                Vec3 origin = new Vec3(org[0], org[1], org[2]).scale(s);
                Vec3 pvec = p == null ? null : new Vec3(p[0], p[1], p[2]).scale(s);
                gfx = gfxEngine.getDebugEntity(new Vec3[]
                {
                    origin,
                    p == null ? new Vec3(ray[0], ray[1], ray[2]).scale(10000).add(origin) : pvec
                });
                gfx.colour = p == null ? new Vec3(1, 0, 1) : new Vec3(0, 1, 1);
                if (p != null)
                {
                    gfx = gfxEngine.getDebugEntity(new Vec3[]
                    {
                        pvec,
                        new Vec3(ray[0], ray[1], ray[2]).scale(10000).add(pvec)
                    });
                    gfx.colour = new Vec3(1, 0, 1);
                }
                for (float[] z : points)
                {
                    bbox = new BBox(Mat4.MAT4_IDENTITY, new Vec3(0.05f));

                    gfx = gfxEngine.getDebugEntity(bbox);
                    gfx.colour = new Vec3(0, 1, 1);
                    gfx.scale = new Vec3(1);
                    gfx.position = new Vec3(z[0], z[1], z[2]).scale(s);
                }
                gfxEngine.getDebugEntity(new Vec3[]{new Vec3(-1000,0,0),new Vec3(1000,0,0)}).colour = new Vec3(0.7f,0,0);
                gfxEngine.getDebugEntity(new Vec3[]{new Vec3(0,-1000,0),new Vec3(0,1000,0)}).colour = new Vec3(0,0.7f,0);
                gfxEngine.getDebugEntity(new Vec3[]{new Vec3(0,0,-1000),new Vec3(0,0,1000)}).colour = new Vec3(0,0,0.7f);
            }

            @Override
            public void destroy()
            {
            }
        });
        engine.start();

        panel.add(gfxEngine.getAWTComponent());
        panel.add(new RayBox(0, 1));
        panel.add(new RayBox(2, 0));
        panel.add(new RayBox(2, 1));

        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
