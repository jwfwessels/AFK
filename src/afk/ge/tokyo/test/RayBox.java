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
 package afk.ge.tokyo.test;

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import static afk.ge.tokyo.test.RayBox.axisCol;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
    static float[] off =
    {
        0, 0, 0
    };
    static Color[] axisCol =
    {
        Color.RED.darker(), Color.GREEN.darker(), Color.BLUE.darker()
    };
    static float[] p = null;

    static class LPoint
    {

        float[] p;
        String label;

        public LPoint(float[] p, String label)
        {
            this.p = p;
            this.label = label;
        }
    }
    static List<Vec3> points = new ArrayList<Vec3>();
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
        g.drawRect(getWidth() / 2 - (int) (ext[I[0]] - off[I[0]]), getHeight() / 2 - (int) (ext[I[1]] - off[I[1]]),
                (int) ext[I[0]] * 2, (int) ext[I[1]] * 2);

        g.drawString(message, 5, 15);

        g.setColor(p == null ? Color.MAGENTA : Color.CYAN);
        float x = getWidth() / 2 + (int) org[I[0]];
        float y = getHeight() / 2 + (int) org[I[1]];
        if (p == null)
        {
            g.drawLine((int) x, (int) y, (int) (x + ray[I[0]] * 1000), (int) (y + ray[I[1]] * 1000));
        } else
        {
            g.drawLine((int) x, (int) y, getWidth() / 2 + (int) p[I[0]], getHeight() / 2 + (int) p[I[1]]);
            g.setColor(Color.MAGENTA);
            g.drawLine(getWidth() / 2 + (int) p[I[0]], getHeight() / 2 + (int) p[I[1]],
                    (int) (x + ray[I[0]] * 1000), (int) (y + ray[I[1]] * 1000));
        }
        g.setColor(Color.CYAN);
        for (Vec3 z : points)
        {
            x = getWidth() / 2 + (z.get(I[0])*10.0f);
            y = getHeight() / 2 + (z.get(I[1])*10.0f);
            g.fillOval((int) x - 2, (int) y - 2, 4, 4);
            //g.drawString(z.label, (int) x, (int) y);
        }
    }

    public static float[] findEnterancePoint()
    {
        message = "";
        points.clear();

        System.out.println("=============");

        float[] mext = new float[ext.length];
        for (int i = 0; i < ext.length; i++)
        {
            mext[i] = -ext[i];
        }
        ArrayList<float[]> ps = new ArrayList<float[]>();
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
                ps.addAll(lineIntersection(i, ext));
            } else if (org[i] <= mext[i])
            {
                message += "[" + axis[i] + "-]";
                if (ray[i] <= 0)
                {
                    message += "[away-]";
                    return null; // ray points away from box
                }
                ps.addAll(lineIntersection(i, mext));
            }
        }
        if (ps.isEmpty())
        {
            return null;
        }
        float[] choice = ps.get(0);
        float cdist = getDist(choice, org);
        for (int i = 1; i < ps.size(); i++)
        {
            float[] z = ps.get(i);
            float dist = getDist(z, org);
            if (dist < cdist)
            {
                cdist = dist;
                choice = z;
            }
        }
        return choice;
    }

    static float getDist(float[] a, float[] b)
    {
        float dist = 0;
        for (int i = 0; i < a.length; i++)
        {
            float c = (a[i] - b[i]);
            dist += c * c;
        }
        return dist;
    }

    public static ArrayList<float[]> lineIntersection(int xi, float[] lext)
    {
        ArrayList<float[]> ps = new ArrayList<float[]>();

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

        System.out.println("-----");
        System.out.println("t" + xi + ".0: " + t0);
        System.out.println("t" + xi + ".1: " + t1);
        System.out.println("t" + xi + ".2: " + t2);

        r[xi] = lext[xi];
        r[yi] = org[yi] + ray[yi] * t0;
        r[zi] = org[zi] + ray[zi] * t0;

        //points.add(new RayBox.LPoint(r, "t" + xi + "0"));
        if (t0 > 0
                && Math.abs(r[yi]) <= ext[yi]
                && Math.abs(r[zi]) <= ext[zi])
        {
            ps.add(r);
        }

        r = new float[3];
        r[xi] = org[xi] + ray[xi] * t1;
        r[yi] = lext[yi];
        r[zi] = org[zi] + ray[zi] * t1;

        //points.add(new RayBox.LPoint(r, "t" + xi + "0"));
        if (t1 > 0
                && Math.abs(r[xi]) <= ext[xi]
                && Math.abs(r[zi]) <= ext[zi])
        {
            ps.add(r);
        }

        r = new float[3];
        r[xi] = org[xi] + ray[xi] * t2;
        r[yi] = org[yi] + ray[yi] * t2;
        r[zi] = lext[zi];

        //points.add(new RayBox.LPoint(r, "t" + xi + "0"));
        if (t2 > 0
                && Math.abs(r[xi]) <= ext[xi]
                && Math.abs(r[yi]) <= ext[yi])
        {
            ps.add(r);
        }

        return ps;
    }
    private int button = -1;

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
                button = e.getButton();
                if (button == MouseEvent.BUTTON1)
                {
                    org[I[0]] = (float) e.getX() - getWidth() / 2;
                    org[I[1]] = (float) e.getY() - getHeight() / 2;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (button == MouseEvent.BUTTON1)
                {
                    ray[I[0]] = ((float) e.getX() - getWidth() / 2.0f) - org[I[0]];
                    ray[I[1]] = ((float) e.getY() - getHeight() / 2.0f) - org[I[1]];
                } else if (button == MouseEvent.BUTTON2)
                {
                    ext[I[0]] = (float) Math.abs(e.getX() - getWidth() / 2);
                    ext[I[1]] = (float) Math.abs(e.getY() - getHeight() / 2);
                } else if (button == MouseEvent.BUTTON3)
                {
                    off[I[0]] = (float) e.getX() - getWidth() / 2;
                    off[I[1]] = (float) e.getY() - getHeight() / 2;
                }
            }
        });

        //p = findEnterancePoint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final JFrame frame = new JFrame("RayBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel(new GridLayout(2, 2));

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
                Vec3 pos = new Vec3(off[0], off[1], off[2]).scale(s);
                Vec3 origin = new Vec3(org[0], org[1], org[2]).scale(s);
                Vec3 rayVec = new Vec3(ray[0], ray[1], ray[2]).getUnitVector();
                Vec3 extents = new Vec3(ext[0], ext[1], ext[2]).scale(s);
                
                Mat4 m = Matrices.translate(Mat4.MAT4_IDENTITY, pos);
                BBox bbox = new BBox(m, extents);
                points = bbox.getIntersectionPoints(origin, rayVec);
                
                float dist = Float.POSITIVE_INFINITY;
                if (!points.isEmpty())
                {
                    float cdist = Float.POSITIVE_INFINITY;
                    for (int i = 0; i < points.size(); i++)
                    {
                        float d = points.get(i).subtract(origin).getLengthSquared();
                        if (d < cdist)
                        {
                            cdist = d;
                        }
                    }
                    dist = FastMath.sqrtFast(cdist);
                }

                Vec3 pvec = null;
                
                if (!Float.isInfinite(dist))
                {
                    message = "dist: "+dist;
                    pvec = origin.add(rayVec.scale(dist));
                    p = new float[]
                    {
                        pvec.getX()/s, pvec.getY()/s, pvec.getZ()/s
                    };
                } else
                {
                    message = "infinity";
                    p = null;
                }
                panel.repaint();

                GfxEntity gfx = gfxEngine.getDebugEntity(bbox);
                gfx.position = pos;
                gfx.colour = new Vec3(1, 1, 1);
                gfx.scale = new Vec3(1);

                gfx = gfxEngine.getDebugEntity(new Vec3[]
                {
                    origin,
                    p == null ? rayVec.scale(10000).add(origin) : pvec
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
                for (Vec3 z : points)
                {
                    bbox = new BBox(Mat4.MAT4_IDENTITY, new Vec3(0.05f));

                    gfx = gfxEngine.getDebugEntity(bbox);
                    gfx.colour = new Vec3(0, 1, 1);
                    gfx.scale = new Vec3(1);
                    gfx.position = z;
                }
                gfxEngine.getDebugEntity(new Vec3[]
                {
                    new Vec3(-1000, 0, 0), new Vec3(1000, 0, 0)
                }).colour = new Vec3(0.7f, 0, 0);
                gfxEngine.getDebugEntity(new Vec3[]
                {
                    new Vec3(0, -1000, 0), new Vec3(0, 1000, 0)
                }).colour = new Vec3(0, 0.7f, 0);
                gfxEngine.getDebugEntity(new Vec3[]
                {
                    new Vec3(0, 0, -1000), new Vec3(0, 0, 1000)
                }).colour = new Vec3(0, 0, 0.7f);
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
