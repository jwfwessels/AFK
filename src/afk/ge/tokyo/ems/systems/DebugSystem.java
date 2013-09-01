package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.Tokyo;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.Entity;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.RenderNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Daniel
 */
public class DebugSystem implements ISystem
{

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final String WINDOW_TITLE_VIEW = "DebugView";
    public static final String WINDOW_TITLE_INFO = "DebugInfo";
    private Engine engine;
    private JFrame frame;
    private RenderCanvas canvas;
    private Rectangle2D quad = new Rectangle2D.Float(-0.5f, -0.5f, 1, 1);
    private Point mouse = null;
    private JFrame infoFrame;
    private JTree tree = null;
    private List<DefaultMutableTreeNode> leaves;
    private DefaultTreeModel model;
    private Entity selected = null;

    class RenderCanvas extends Canvas
    {

        public BufferedImage buffer = null;
        public BufferStrategy strategy;

        public void init()
        {
            createBufferStrategy(2);
            strategy = getBufferStrategy();
        }
    }

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;

        canvas = new RenderCanvas();

        frame = new JFrame(WINDOW_TITLE_VIEW);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setResizable(false);
        canvas.init();
        canvas.requestFocus();

        canvas.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                mouse = e.getPoint();
            }
        });

        infoFrame = new JFrame(WINDOW_TITLE_INFO);
        infoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        infoFrame.pack();
        infoFrame.setVisible(true);

        return true;
    }

    @Override
    public void update(float t, float dt)
    {

        Graphics2D g = (Graphics2D) canvas.strategy.getDrawGraphics();

        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        AffineTransform origT = g.getTransform();
        g.transform(AffineTransform.getScaleInstance(canvas.getWidth() / Tokyo.BOARD_SIZE,
                canvas.getHeight() / Tokyo.BOARD_SIZE));
        g.transform(AffineTransform.getTranslateInstance(Tokyo.BOARD_SIZE / 2, Tokyo.BOARD_SIZE / 2));

        List<RenderNode> nodes = engine.getNodeList(RenderNode.class);

        float mx = mouse == null ? 0 : (((float) mouse.x / (float) canvas.getWidth()) - 0.5f) * Tokyo.BOARD_SIZE;
        float my = mouse == null ? 0 : (((float) mouse.y / (float) canvas.getHeight()) - 0.5f) * Tokyo.BOARD_SIZE;

        float closestSq = Float.POSITIVE_INFINITY;
        if (mouse != null)
        {
            Entity newSelected = null;
            for (RenderNode node : nodes)
            {
                float distSq = node.state.pos.subtract(new Vec3(mx, 0, my)).getLengthSquared();
                if (distSq < closestSq)
                {
                    newSelected = node.entity;
                    closestSq = distSq;
                }
            }
            if (newSelected != selected)
                selected = newSelected;
            
            if (tree != null)
                infoFrame.remove(tree);
            tree = new JTree(model = createModel(selected));
            infoFrame.add(tree);
            infoFrame.validate();
            infoFrame.pack();
        }
        
        if (leaves != null)
        {
            for (DefaultMutableTreeNode node : leaves)
            {
                model.nodeChanged(node);
            }
            tree.repaint();
        }

        for (RenderNode node : nodes)
        {

            AffineTransform oldT = g.getTransform();

            g.transform(AffineTransform.getTranslateInstance(
                    node.state.pos.getX(), node.state.pos.getZ()));
            g.transform(AffineTransform.getRotateInstance(FastMath.toRadians(-node.state.rot.getY())));
            g.transform(AffineTransform.getScaleInstance(node.state.scale.getX(),
                    node.state.scale.getZ()));

            if (node.entity == selected)
            {
                g.setColor(Color.WHITE);
            } else
            {
                g.setColor(new Color(node.renderable.colour.getX(),
                        node.renderable.colour.getY(),
                        node.renderable.colour.getZ()));
            }
            g.fill(quad);
            if (node.entity == selected)
            {
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(0.1f));
                g.draw(quad);
            }

            g.setTransform(oldT);
        }

        mouse = null;

        g.setTransform(origT);

        g.dispose();

        canvas.strategy.show();

        Toolkit.getDefaultToolkit().sync();
    }
    
    public DefaultTreeModel createModel(Entity entity)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Entity");
        
        for (Object obj : entity.getAll())
        {
            Class objClass = obj.getClass();
            
            DefaultMutableTreeNode objNode
                    = new DefaultMutableTreeNode(objClass.getSimpleName());
            root.add(objNode);
            
            Field[] fields = objClass.getFields();
            
            leaves = new ArrayList<DefaultMutableTreeNode>();
            
            for (Field field : fields)
            {
                DefaultMutableTreeNode fieldNode
                        = new DefaultMutableTreeNode(field.getName());
                objNode.add(fieldNode);
                DefaultMutableTreeNode valueNode;
                valueNode = new FieldValueNode(obj, field);
                leaves.add(valueNode);
                fieldNode.add(valueNode);
            }
        }
        
        return new DefaultTreeModel(root);
    }
    
    private class FieldValueNode extends DefaultMutableTreeNode
    {
        Field field;

        public FieldValueNode(Object obj, Field field)
        {
            super(obj);
            this.field = field;
        }

        @Override
        public String toString()
        {
            try
            {
                return field.get(userObject).toString();
            } catch (ReflectiveOperationException ex)
            {
                return "ERROR";
            }
        }
        
    }

    @Override
    public void destroy()
    {
    }
}
