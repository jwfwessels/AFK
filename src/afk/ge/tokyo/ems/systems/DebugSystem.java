package afk.ge.tokyo.ems.systems;

import afk.bot.RobotEngine;
import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.Tokyo;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.Entity;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.SnapToTerrain;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Targetable;
import afk.ge.tokyo.ems.nodes.RenderNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.hackoeur.jglm.support.FastMath;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    public static final DefaultMutableTreeNode NOTHING_SELECTED_NODE = new DefaultMutableTreeNode("----- Nothing selected -----");
    private Engine engine;
    private JFrame frame;
    private RenderCanvas canvas;
    private Rectangle2D quad = new Rectangle2D.Float(-0.5f, -0.5f, 1, 1);
    private AtomicReference<Point> mouse = new AtomicReference<Point>(null);
    private AtomicReference<Point> hover = new AtomicReference<Point>(null);
    private AtomicInteger wheel = new AtomicInteger(0);
    private JTree tree = null;
    private JMenuBar menubar;
    private JMenu addMenu;
    private JMenuItem addRobot, addWall, addExplosion, moveItem, removeItem;
    private JFileChooser fileChooser;
    private List<DefaultMutableTreeNode> leaves;
    private DefaultTreeModel model;
    private Entity selected = null;
    private Entity hovered = null;
    private RobotEngine botEngine;
    private EntityManager manager; // TODO: this should be replaced with factories!!!!!!!
    private Entity placeEntity = null;

    private Vec3 mouse2world(Point myMouse)
    {
        float mx = (((float) myMouse.x / (float) canvas.getWidth()) - 0.5f) * Tokyo.BOARD_SIZE;
        float my = (((float) myMouse.y / (float) canvas.getHeight()) - 0.5f) * Tokyo.BOARD_SIZE;

        // TODO: read heightmap and use that as Y value
        return new Vec3(mx, 0, my);
    }

    private Entity mouseThing(Point myMouse, List<RenderNode> nodes)
    {
        float closestSq = Float.POSITIVE_INFINITY;
        Entity newSelected = null;
        if (myMouse != null)
        {

            for (RenderNode node : nodes)
            {
                if (node.entity.has(Parent.class))
                {
                    continue;
                }
                float distSq = node.state.pos.subtract(mouse2world(myMouse)).getLengthSquared();
                if (distSq < closestSq)
                {
                    newSelected = node.entity;
                    closestSq = distSq;
                }
            }
        }
        return newSelected;
    }

    private void select(Entity newSelected)
    {
        if (newSelected != selected)
        {
            selected = newSelected;
        }

        if (selected != null)
        {
            tree.setModel(model = createModel(selected));
        } else
        {
            tree.setModel(new DefaultTreeModel(NOTHING_SELECTED_NODE));
        }

        removeItem.setEnabled(selected != null);
        moveItem.setEnabled(selected != null);
    }

    private void startPlaceItem(Entity entity)
    {
        placeEntity = entity;
        if (placeEntity.has(Renderable.class))
        {
            engine.addEntity(placeEntity);
        }
    }

    private void placeItem(Point myMouse)
    {
        State state = placeEntity.get(State.class);
        if (state != null)
        {
            state.prevPos = state.pos;
            state.pos = mouse2world(myMouse);
        }

        if (!placeEntity.has(Renderable.class))
        {
            engine.addEntity(placeEntity);
        }
        select(placeEntity);
        placeEntity = null;
    }

    public DebugSystem(RobotEngine botEngine, EntityManager manager)
    {
        this.botEngine = botEngine;
        this.manager = manager;
    }

    private void drawRenderable(Graphics2D g, RenderNode node)
    {
        if (node.entity.has(Parent.class))
        {
            return;
        }
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

        if (node.entity == hovered)
        {
            g.setColor(new Color(1, 1, 1, 0.5f));
            g.fill(quad);
            g.setColor(new Color(0, 1, 0, 0.5f));
            g.setStroke(new BasicStroke(0.1f));
            g.draw(quad);
        }

        g.setTransform(oldT);
    }

    class RenderCanvas extends Canvas
    {

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

        fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Load Bot");
        fileChooser.setFileFilter(new FileNameExtensionFilter(".class, .jar", "class", "jar"));

        menubar = new JMenuBar();

        addMenu = new JMenu("Add", true);

        addRobot = new JMenuItem("Target");
        addRobot.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Entity entity = new Entity();
                entity.add(new Renderable("wall", EntityManager.MAGENTA));
                entity.add(new SnapToTerrain());
                entity.add(new Targetable());
                Vec3 scale = new Vec3(0.3f);
                entity.add(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO, scale));
                entity.add(new BBoxComponent(scale.scale(0.5f)));
                startPlaceItem(entity);
            }
        });
        addMenu.add(addRobot);
        addWall = new JMenuItem("Wall");
        addWall.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                float scale = (float) (3.0 + 3.0 * Math.random());
                startPlaceItem(manager.createGraphicWall(Vec3.VEC3_ZERO, new Vec3(scale)));
            }
        });
        addMenu.add(addWall);
        addExplosion = new JMenuItem("Explosion");
        addExplosion.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startPlaceItem(manager.makeExplosion(Vec3.VEC3_ZERO, null, 0));
            }
        });
        addMenu.add(addExplosion);

        menubar.add(addMenu);

        moveItem = new JMenuItem("Move");
        moveItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (selected != null)
                {
                    placeEntity = selected;
                }
            }
        });
        menubar.add(moveItem);

        removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (selected != null)
                {
                    DebugSystem.this.engine.removeEntity(selected);
                    select(null);
                }
            }
        });
        menubar.add(removeItem);

        frame.setJMenuBar(menubar);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        tree = new JTree(NOTHING_SELECTED_NODE);
        tree.addTreeExpansionListener(new TreeExpansionListener()
        {
            @Override
            public void treeExpanded(TreeExpansionEvent event)
            {
                frame.pack();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event)
            {
                frame.pack();
            }
        });
        JScrollPane treePanel = new JScrollPane(tree);
        panel.add(treePanel, BorderLayout.WEST);
        panel.add(canvas, BorderLayout.CENTER);
        canvas.setSize(WIDTH, HEIGHT);
        frame.pack();
        frame.setVisible(true);
        canvas.init();
        canvas.requestFocus();

        canvas.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                mouse.set(e.getPoint());
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                hover.set(null);
            }
        });
        canvas.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                hover.set(e.getPoint());
            }
        });
        canvas.addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                wheel.set(e.getWheelRotation());
            }
        });

        select(null);

        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        Point myMouse = mouse.getAndSet(null);
        Point myHover = hover.get();
        int myWheel = wheel.getAndSet(0);

        Graphics2D g = (Graphics2D) canvas.strategy.getDrawGraphics();

        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        AffineTransform origT = g.getTransform();
        g.transform(AffineTransform.getScaleInstance(canvas.getWidth() / Tokyo.BOARD_SIZE,
                canvas.getHeight() / Tokyo.BOARD_SIZE));
        g.transform(AffineTransform.getTranslateInstance(Tokyo.BOARD_SIZE / 2, Tokyo.BOARD_SIZE / 2));

        List<RenderNode> nodes = engine.getNodeList(RenderNode.class);

        for (RenderNode node : nodes)
        {
            drawRenderable(g, node);
        }

        if (placeEntity != null)
        {
            if (myMouse != null)
            {
                placeItem(myMouse);
            } else if (myHover != null)
            {
                hovered = placeEntity;
                State state = placeEntity.get(State.class);
                state.prevPos = state.pos;
                state.pos = mouse2world(myHover);
                if (myWheel != 0)
                {
                    state.prevRot = state.rot;
                    state.rot = state.rot.add(new Vec4(0,myWheel,0,0));
                }
                if (!placeEntity.has(Renderable.class))
                {
                    RenderNode node = new RenderNode();
                    node.entity = placeEntity;
                    node.renderable = new Renderable("cube", EntityManager.MAGENTA);
                    node.state = state;
                    drawRenderable(g, node);
                }
            }
        } else
        {
            Entity newSelected = mouseThing(myMouse, nodes);
            hovered = mouseThing(myHover, nodes);

            if (newSelected != null)
            {
                select(newSelected);
            }
        }

        if (leaves != null)
        {
            for (DefaultMutableTreeNode node : leaves)
            {
                model.nodeChanged(node);
            }
            tree.repaint();
        }

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

            DefaultMutableTreeNode objNode = new DefaultMutableTreeNode(objClass.getSimpleName());
            root.add(objNode);

            Field[] fields = objClass.getFields();

            leaves = new ArrayList<DefaultMutableTreeNode>();

            for (Field field : fields)
            {
                DefaultMutableTreeNode fieldNode = new DefaultMutableTreeNode(field.getName());
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
