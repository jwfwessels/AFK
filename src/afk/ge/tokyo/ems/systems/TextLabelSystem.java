package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.TextLabel;
import afk.ge.tokyo.ems.nodes.TextLabelNode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TextLabelSystem implements ISystem
{

    Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<TextLabelNode> nodes = engine.getNodeList(TextLabelNode.class);
        for (TextLabelNode node : nodes)
        {
            
            if (node.label.isUpdated())
            {
                System.out.println("I'm drawin a label!");
                node.image.setImage(createTextLabel(node.label));
                node.label.setUpdated(false);
            }
        }
    }
    
    private BufferedImage createTextLabel(TextLabel label)
    {
        BufferedImage image = new BufferedImage(100, 20, BufferedImage.TRANSLUCENT);
        Graphics2D g = image.createGraphics();
        
        String str = label.getText();

//        g.setBackground(Color.BLACK);
//        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        int width = g.getFontMetrics().stringWidth(str);

        g.setColor(new Color(0, 0, 0, 0.3f));
        g.fillRoundRect(0, 0, width+10, 20, 5, 5);
        g.setColor(Color.YELLOW);
        g.drawString(str, 5, 15);

        g.dispose();

        return image;
    }

    @Override
    public void destroy()
    {
    }
}
